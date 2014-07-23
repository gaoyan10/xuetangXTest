package com.xuetangx.test.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;

public class AllCourseAnalyzer implements BaseAnalyzer {
	private Context context;
	private String courseId;
	private boolean detail;
	private boolean courses;
	private boolean enroll;
	public AllCourseAnalyzer(Context context, String courseId, boolean detail, boolean courses, boolean enroll) {
		this.context = context;
		this.courseId = courseId;
		this.detail = detail;
		this.courses = courses;
		this.enroll = enroll;
	}

	@Override
	public Object analyseJson(String json, int code) {
		// TODO Auto-generated method stub
		try {
			
			if (code == 200) {
				String key = "data";
				if(enroll) {
					key = "enrollments";
				}
				long time = System.currentTimeMillis();
				List<ContentValues> courses = new ArrayList<ContentValues>();
				JSONObject obj = new JSONObject(json);
				JSONArray list = null;
				if (!this.courses) {
					list = new JSONArray();
					list.put(0, obj);
				}else {
					list = obj.getJSONArray(key);
				}
				
				if (obj.getString("status").equals("success")) {
					for (int i = 0; i < list.length(); i++) {
						JSONObject item = list.getJSONObject(i);
						// HashMap<String, Object> map = new HashMap<String,
						// Object>();
						ContentValues map = new ContentValues();
						map.put("course_id", item.getString("course_id"));
						map.put("display_name", item.getString("display_name"));
						map.put("display_org", item.getString("display_org"));
						map.put("display_coursenum",
								item.getString("display_coursenum"));
						map.put("start", item.getString("start"));
						map.put("advertised_start",
								item.getString("advertised_start"));
						map.put("course_image_url",
								item.getString("course_image_url"));
						map.put("marketing_video_url",
								item.getString("marketing_video_url"));
						map.put("marketing_caption_url", item.getString("marketing_caption_url"));
						map.put("finish", item.getString("finish"));
						map.put("course_status", item.getString("course_status"));
						map.put("student_num", item.getString("student_num"));
						String tmp = item.getJSONObject("master_teacher").toString();
						map.put("master_teacher", tmp);
						map.put("share_content", item.getString("share_content"));
						if (detail) {
							map.put("short_description", item.getString("short_description"));
							map.put("student_num", item.getString("student_num"));
							map.put("reserve", item.getString("reserve"));
							map.put("marketing_video_url", item.getString("marketing_video_url"));
							map.put("effort", item.getString("effort"));
							map.put("faq", item.getJSONArray("faq").toString());
							map.put("category", item.getJSONArray("categories").toString());
							map.put("performance_evaluation", item.getString("performance_evaluation"));
							map.put("outline", item.getString("outline"));
							map.put("enroll", item.getBoolean("enroll"));
							try {
								map.put("naturalStart", item.getString("naturalStart"));
							}catch(JSONException e) {
								map.put("naturalStart", "");
								e.printStackTrace();
							}
							if (!enroll)
								map.put("selected", item.getBoolean("selected"));
							if (!enroll) {
								map.put("knowledage", item.getString("knowledge_map"));
							}
							map.put("refresh_timestamp", time);
						}else {
							
						}
						courses.add(map);
					}
					return courses;
				}else {
					return this.NET_ERR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NET_ERR;
		}
		return NET_ERR;

	}

	@Override
	public String createJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMessage connect() {
		// TODO Auto-generated method stub
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(ConstantUtils.KEY, Utils.getAPIKey(context));
		if (Utils.getUserName().length() > 0 && !Utils.getAccessToken().equals("Bearer null")) { 
			params.put(ConstantUtils.ACCESS, Utils.getAccessToken());
		}
		NetConnector con = NetConnector.getInstance();
		if (!courses) {
			ResponseMessage msg = con.httpGet(ConstantUtils.U_ROOT
				+ ConstantUtils.U_COURSEWARE_FOR_ONECOURSE  + "?course_id=" + courseId, params);
			return msg;
		}else {
			if (!enroll) {
			ResponseMessage msg = con.httpGet(ConstantUtils.U_ROOT
					+ ConstantUtils.U_COURSES , params);
			return msg;
			}else {
				ResponseMessage msg = con.httpGet(ConstantUtils.U_ROOT
						+ ConstantUtils.U_GET_ENROLL , params);
				return msg;
			}
		}
		
	}

}
