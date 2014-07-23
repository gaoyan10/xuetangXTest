package com.xuetangx.test.ini;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.util.Log;

/**
 * this class is a entrance for network request.
 * https and http.
 * https methods ignore all.
 * @author gaoyansansheng@gmail.com github.com/gaoyan10
 *
 */
public class NetConnector {
	private static NetConnector instance = new NetConnector();
	private NetConnector() {
		
	}
	public static NetConnector getInstance() {
		return instance;
	}
	public ResponseMessage httpsPost(String url, String json, HashMap<String, String> header) {
		return httpsConnector(url, json, 20000, header, "POST");
	}
	public ResponseMessage httpsGet(String url, HashMap<String, String> header) {
		return httpsConnector(url, "", 20000, header, "GET");
	}
	public ResponseMessage httpsOthers(String url, HashMap<String, String> header, String method) {
		return httpsConnector(url, "", 20000, header, method);
	}
	public ResponseMessage httpPost(String url, String json, HashMap<String, String> header) {
		return httpConnector(url, json, 20000, header, "POST");
	}
	public ResponseMessage httpGet(String url, HashMap<String, String> header) {
		return httpConnector(url, "", 20000, header, "GET");
	}
	public ResponseMessage httpOthers(String url, HashMap<String, String> header, String method) {
		return httpConnector(url, "", 20000, header, method);
	}
	
	private ResponseMessage httpsConnector(String url, String json, int timeOut, HashMap<String, String> header, String method) {
		StringBuilder sb = new StringBuilder();
		int code = 0;
		HttpsURLConnection conn = null; 
		try {
			SSLContext sc = SSLContext.getInstance("TLS");  
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());  
			url = url.replaceAll(" ", "%20");
			URL urls = new URL(url);
			conn = (HttpsURLConnection)urls.openConnection();
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			if(method.equals("POST")) {
				conn.setDoOutput(true);
			}
			Iterator it = header.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>)it.next();
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			if (method.equals("POST")) {
				postData(conn, json);
			}
			code = conn.getResponseCode();
			String responseMessage = conn.getResponseMessage();
			BufferedReader reader;
			if(code >= 400 ){
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}else{
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String content = reader.readLine();
			while(content!= null) {
				sb.append(content);
				content = reader.readLine();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (conn != null) {
				conn.disconnect();
			}
		}
		return new ResponseMessage(sb.toString(), code);
	}
	private void postData(HttpsURLConnection conn, String data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.flush();
		writer.close();
	}
	private ResponseMessage httpConnector(String url, String json, int timeOut, HashMap<String, String> header, String method) {
		StringBuilder sb = new StringBuilder();
		int code = 0;
		HttpURLConnection conn = null; 
		try {
			url = url.replaceAll(" ", "%20");
			URL urls = new URL(url);
			conn = (HttpURLConnection)urls.openConnection();
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			if(method.equals("POST")) {
				conn.setDoOutput(true);
			}
			Iterator it = header.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>)it.next();
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			if (method.equals("POST")) {
				postData(conn, json);
			}
			code = conn.getResponseCode();
			String responseMessage = conn.getResponseMessage();
			BufferedReader reader;
			if(code >= 400 ){
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}else{
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String content = reader.readLine();
			while(content!= null) {
				sb.append(content);
				content = reader.readLine();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//LogUtils.postErrorLog("Network" + e.getMessage());
			e.printStackTrace();
		} catch(IOException e) {
			//LogUtils.postErrorLog("IO" + e.getMessage());
			e.printStackTrace();
		}finally{
			if (conn != null) {
				conn.disconnect();
			}
		}
		return new ResponseMessage(sb.toString(), code);
	}
	private void postData(HttpURLConnection conn, String data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.flush();
		writer.close();
	}
	public boolean httpDownloadCCFile(String url, File file) {
		HttpURLConnection conn = null;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			URL urls = new URL(url);
			conn = (HttpURLConnection)urls.openConnection();
			//conn.setRequestProperty("Accept-Encoding", "identity");
			if(conn.getResponseCode() < 400) {
				
				BufferedInputStream input = new BufferedInputStream(conn.getInputStream());
				ByteArrayBuffer buffer = new ByteArrayBuffer(4096);
				int loc = 0;
				FileOutputStream out = new FileOutputStream(file);
				byte[] b = new byte[1024];
				while ((loc = input.read(b))!= -1) {
					Log.i("download", loc +"");
					out.write(b, 0, loc);
					out.flush();
				}
				out.flush();
				out.close();
				return true;
			}else {
				return false;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if(conn != null) {
				conn.disconnect();
			}
		}
	}
	public boolean downloadImage(String url, File file, String https) {
		HttpURLConnection conn = null;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			URL urls = new URL(url);
			conn = (HttpURLConnection)urls.openConnection();
			if(conn.getResponseCode() < 400) {
				BufferedInputStream input = new BufferedInputStream(conn.getInputStream());
				ByteArrayBuffer buffer = new ByteArrayBuffer(4096);
				int loc = 0;
				String result = "";
				byte[] b = new byte[1024];
				while ((loc = input.read(b))!= -1) {
					buffer.append(b, 0, loc);
					//result += new String(b, "UTF-8");
				}
				FileOutputStream out = new FileOutputStream(file);
				//FileWriter out = new FileWriter(file);
				out.write(buffer.toByteArray());
				//out.write(result);
				out.flush();
				out.close();
				return true;
			}else {
				return false;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if(conn != null) {
				conn.disconnect();
			}
		}

	}
	/**
	 * Verifier.
	 * ignore all unsafe, trust all.
	 * @author gaoyansansheng@gmail.com
	 *
	 */
	 private class MyHostnameVerifier implements HostnameVerifier{  
         @Override  
         public boolean verify(String hostname, SSLSession session) {  
                 // TODO Auto-generated method stub  
                 return true;  
         }  

    }  

    private class MyTrustManager implements X509TrustManager{  
         @Override  
         public void checkClientTrusted(X509Certificate[] chain, String authType)  
                         throws CertificateException {  
                 // TODO Auto-generated method stub    
         }  
         @Override  
         public void checkServerTrusted(X509Certificate[] chain, String authType)  

                         throws CertificateException {  
                 // TODO Auto-generated method stub      
         }  
         @Override  
         public X509Certificate[] getAcceptedIssuers() {  
                 // TODO Auto-generated method stub  
                 return null;  
         }          

   }  
}
