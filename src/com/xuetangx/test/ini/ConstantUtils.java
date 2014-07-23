package com.xuetangx.test.ini;

public class ConstantUtils {
	public static int SCREEN_HEIGHT = -1;
	public static int SCREEN_WIDTH = -1; 
	/**
	 * constant for database.
	 */
	public static final String DB_COURSE = "course.db";
	public static final String DB_USER = "userdata.db";
	public static final String DB_DOWNLOAD = "download.db";
	public static final String DB_HISTORY = "history.db";
	public static final String DB_MESSAGE = "message.db";
	public static final String T_COURSE = "T_COURSE";
	public static final String T_ENROLLMENT = "T_USER_ENROLLMENT";
	public static final String T_CATEGORY = "T_COURSE_CATEGORY";
	public static final String T_COURSE_DETAIL = "T_COURSE_DETAIL";
	public static final String T_VIDEO_STATUS = "T_VIDEO_STATUS";
	public static final String T_TEACHER = "T_TEACHER";
	public static final String T_ORG = "T_ORG";
	public static final String T_COURSE_VIDEO_STATUS = "T_COURSE_VIDEO_STATUS";
	
	public static final String T_CURRENT_USER = "T_CURRENT_USER";
	public static final String T_USER = "T_USER";
	
	public static final String T_MESSAGE = "T_MESSAGE";
	
	public static final String T_SEARCH_HISTORY = "T_HISTORY";
	
	/**
	 * constant for storage.
	 */
	public static final String ROOT = "xuetangx/mobilev1/";
	public static final String P_LOG = "xuetangx/mobilev1/log_module/";
	public static final String P_VIDEO = "xuetangx/mobilev1/videocache/";
	public static final String P_IMAGE = "xuetangx/mobilev1/image/";
	public static final String P_FILE = "xuetangx/mobilev1/filecache/";
	public static final String P_TMP = "xuetangx/mobilev1/tmp/";
	public static final String F_LOG = "tmp.log";
	
	/**
	 * constant for url.
	 */
	//public static final String U_ROOT = "http://10.10.10.20:3000/";
	//public static final String U_ROOT = "http://192.168.9.203/";
	public static final String U_ROOT = "http://www.xuetangx.com/";
	public static final String U_ROOTS = "https://www.xuetangx.com/";
	public static final String KEY = "X-edx-api-key";
	public static final String ACCESS = "Authorization";
	public static final String U_WELCOME = "api/v1/welcomepage";
	public static final String U_SEARCH = "api/v1/search?query=";
	public static final String U_LOG = "api/v1/log/";
	public static final String U_FEEDBACK = "api/v1/feedback";
	public static final String U_UPDATE = "api/v1/updates/v1/";
	public static final String U_ENROLL = "api/v1/enrollment/";
	public static final String U_CHANGE_MESSAGE = "api/v1/changemessage/";
	public static final String U_NOTIFY = "api/v1/notify/";
	public static final String U_SYNC = "api/v1/synchronize/";
	public static final String U_COURSEWARE = "api/v1/courseware/";
	public static final String U_COURSEWARE_FOR_ONECOURSE = "api/v1/courseware/";
	public static final String U_COURSENAME = "api/v1/coursename/";
	public static final String U_CATEGORY = "api/v1/category/";
	public static final String U_GET_ENROLL = "api/v1/enrollments/";
	public static final String U_SIGNUP = "api/v1/register/";
	public static final String U_LOGIN = "api/oauth2/access_token";
	public static final String U_RESET = "api/v1/passreset/";
	public static final String U_SRT = "http://s.xuetangx.com/files/course/caption/";
	public static final String U_COURSES = "api/v1/courses";
	/**
	 * constant for shared preference.
	 */
	public static final String S_DEFAULT = "preference";
	public static final String S_SETTINGS = "settings";
	public static final String S_TMP = "log_tmp";
	
	/**
	 * constant key for shared preference an others.
	 */
	public static final String K_LAST_BOOT = "last_boot";
	public static final String K_BOOT_IMAGE = "boot_image";
	public static final String K_BOOT_TIME = "boot_time";
	
	/**
	 * broadcast constant.
	 */
	public static final String DOWN_START = "video_start_download";
	public static final String DOWN_CANCEL = "video_cancel_download";
	public static final String DOWN_B = "video_download";
	public static final String DOWN_PROGRESS = "video_progress";
	public static final String DOWN_UPDATE = "video_update";
	public static final String DOWN_DELETE = "video_delete";
	public static final String COURSE_B = "courselist_broadcast";
	public static final String COURSE_UPDATE = "courselist_update";
	public static final String TOAST_THREAD = "toast_thread";
	
	public static int status = 0;
}
