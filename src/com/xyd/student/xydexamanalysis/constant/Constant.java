package com.xyd.student.xydexamanalysis.constant;

/**
 * Created by Lichg.
 */
public class Constant {

	public static final int ROWS_EXAM_LIMIT = 5;
	public static final int ROWS_NOTICE_LIMIT = 15;
	/* 默认的URL */
	public static String BASE_URL_DM = "http://base.iclassmate.cn:8586/";
	public static String BASELOGINURL = "http://reportuser.iclassmate.cn:8081/BaseCenter/rest/service/loginM";
	public static String BASE_URL = "http://app.iclassmate.cn:10000/";
	public static String BASE_URL2 = "http://app.iclassmate.cn:10010/";
	// public static String BASE_URL = "http://a.iclassmate.cn:10001/";
	// public static String BASE_URL = "http://192.168.1.120:10000/";
	// public static String BASE_URL2 = "http://192.168.1.120:10010/";
	// public static String BASE_URL = "http://a.iclassmate.cn:10010/";
	// public static String BASE_URL = "http://192.168.1.91:10000/";
	// public static String BASE_URL = "http://qdreport1.iclassmate.cn:10000/";
	// public static String BASE_URL = "http://192.168.1.79:10000/";
	// public static String BASE_URL = "http://192.168.1.201:10000/";
	// public static String BASE_URL = "http://139.129.97.230:9000/";
	// public static String BASE_URL2 = "http://192.168.1.120:10010/";
	public static String BASEFILEURL = "http://qdfile1.iclassmate.cn/";
	public static String BSAEWEIKEURL = "http://weike.iclassmate.cn/";

	public static String NOTICE_URL = BASE_URL + "sac/querymessage";

	public static String EXAM_URL = BASE_URL + "sac/queryexams";

	public static String SINGLE_NOTICE_URL = BASE_URL + "sac/querysingleana";
	// public static String LOGIN_SEARCHSCHOOL_URL = BASE_URL +
	// "sac/getschools";

	// 支付订单流水号接口
	public static String PAY_ORDER_URL = BASE_URL + "sac/getpayorderid";

	public static String LOGIN_SEARCHSCHOOL_DM_URL = BASE_URL_DM
			+ "BaseCenter/rest/service/school/searchpy/";
	public static String GET_SCHOOL_SERVERLIST_DM = BASE_URL_DM
			+ "BaseCenter/rest/service/appServer";
	public static String LOGIN_SEARCHSCHOOLCONFIG_URL = BASE_URL
			+ "sac/getschoolconfig";

	// public static String LOGIN_URL = BASE_URL + "sac/login";

	public static final String SHARED_PREFERENCES = "xyd";
	public static String FIRST_LOGIN = "first_login";
	public static String UPDATE_URL = BASE_URL + "sac/getversion";
	/**
	 * 试卷默认前缀
	 */
	public static String BASE_FILEURL = "http://qdfile1.iclassmate.cn";

	// 保存微課
	public static String SAVE_WEIKE = BASE_URL2 + "savewk";
	// 请求微课属性
	public static String GET_WEIKE_INFO = "http://weike.iclassmate.cn/WeiKe/resource?action=DruqwPmdyUmLAHjxDFlPE5XBrQ@@@&resource=%s";
	// 查询微课
	public static String GET_WEIKE = BASE_URL2 + "getwkrecord/%s/%s";
	// 修改微课
	public static String UPDATE_WEIKE = BASE_URL2 + "updatewkinfo";
}
