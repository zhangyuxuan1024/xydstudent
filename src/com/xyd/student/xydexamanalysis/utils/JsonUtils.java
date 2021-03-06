package com.xyd.student.xydexamanalysis.utils;

/**
 * Created by Lichg.
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.R.integer;
import android.R.string;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.Xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyd.student.xydexamanalysis.entity.CarelessInfo;
import com.xyd.student.xydexamanalysis.entity.Casa_detail;
import com.xyd.student.xydexamanalysis.entity.ChargeInfo;
import com.xyd.student.xydexamanalysis.entity.Exam_Con;
import com.xyd.student.xydexamanalysis.entity.ExplainInfo;
import com.xyd.student.xydexamanalysis.entity.Explain_single_info;
import com.xyd.student.xydexamanalysis.entity.Gasa_detal;
import com.xyd.student.xydexamanalysis.entity.Grade_CourseScore;
import com.xyd.student.xydexamanalysis.entity.Grade_Report;
import com.xyd.student.xydexamanalysis.entity.Login;
import com.xyd.student.xydexamanalysis.entity.Login_school;
import com.xyd.student.xydexamanalysis.entity.Login_school_con;
import com.xyd.student.xydexamanalysis.entity.Login_school_config;
import com.xyd.student.xydexamanalysis.entity.Login_userInfo;
import com.xyd.student.xydexamanalysis.entity.Notice_Con;
import com.xyd.student.xydexamanalysis.entity.PayInfo;
import com.xyd.student.xydexamanalysis.entity.PayOrder;
import com.xyd.student.xydexamanalysis.entity.PersonalInfo;
import com.xyd.student.xydexamanalysis.entity.SchoolBadge;
import com.xyd.student.xydexamanalysis.entity.SingleExam;
import com.xyd.student.xydexamanalysis.entity.SingleNotice;
import com.xyd.student.xydexamanalysis.entity.SingleReport;
import com.xyd.student.xydexamanalysis.entity.Single_pageList;
import com.xyd.student.xydexamanalysis.entity.UnbalanceInfo;
import com.xyd.student.xydexamanalysis.entity.Unbalance_detail;
import com.xyd.student.xydexamanalysis.entity.Single_classScoreInfo;
import com.xyd.student.xydexamanalysis.entity.Single_scoreList;
import com.xyd.student.xydexamanalysis.entity.Single_subject_scores;
import com.xyd.student.xydexamanalysis.entity.Single_tagInfoList;
import com.xyd.student.xydexamanalysis.entity.videoList;

/**
 * JSON数据解析工具类
 */
public class JsonUtils {

	public interface GetTotalListener {
		public void getTotal(int totalCount);
	}

	/****
	 * ________________________for Json maybe null
	 * _____________________________________
	 */
	static String ForJson(JSONObject object, String key) {
		String value = "";
		try {
			if (object.optString(key) == null) {
				value = "";
			} else {
				value = object.getString(key);
			}
			return value;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static String getResultMsg(String jsonString) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonString);
			return rootNode.path("resultDesc").asText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getResultCode(String jsonString) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonString);
			return rootNode.path("resultCode").asInt();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static PayOrder jsontoPayOrder(String jsonString) {
		PayOrder payOrder = new PayOrder();
		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);
				String type = object.getString("type");
				String orderId = object.getString("orderId");
				payOrder.setOrderId(orderId);
				payOrder.setType(type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return payOrder;
	}

	public static Notice_Con jsontoNoticeList(String jsonString) {
		Notice_Con ret = new Notice_Con();
		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);
				ArrayList<SingleNotice> noticelist = new ArrayList<SingleNotice>();
				SingleNotice t_singlenotice = null;
				int total = object.getInt("totalCount");
				int unread = object.getInt("unReadedCount");
				Log.i("未读消息条数", "" + unread);
				ret.setTotal(total);
				ret.setUnread(unread);
				JSONArray msgList = new JSONArray(object.getString("msgList"));
				if (msgList != null) {
					for (int i = 0; i < msgList.length(); i++) {
						JSONObject objectlist = msgList.getJSONObject(i);
						int id = objectlist.getInt("id");
						int meId = objectlist.getInt("meId");
						String meName = objectlist.getString("meName");
						int seId = objectlist.getInt("seId");
						int schoolId = objectlist.getInt("schoolId");
						String classId = objectlist.getString("classId");
						String studentId = objectlist.getString("studentId");
						String studentName = objectlist
								.getString("studentName");
						int courseId = objectlist.getInt("courseId");
						String courseName = objectlist.getString("courseName");
						String subjectName = objectlist
								.getString("subjectName");
						String strInfo = objectlist.getString("strInfo");
						String indbtime = objectlist.getString("indbtime");
						String meDate = objectlist.getString("meDate");
						int isReaded = objectlist.getInt("isReaded");
						int msgType = objectlist.getInt("msgType");
						// 支付信息
						PayInfo n_payInfo = null;
						JSONObject object2 = objectlist
								.getJSONObject("payInfo");
						int payStatus = object2.getInt("payStatus");
						String chargeInfo = object2.getString("chargeInfo");
						ChargeInfo n_chargeInfo = null;
						if (chargeInfo != null && chargeInfo.startsWith("{")) {

							JSONObject object3 = object2
									.getJSONObject("chargeInfo");

							String chargeName = object3.getString("chargeName");
							int pay_meId = object3.getInt("meId");
							int pay_seId = object3.getInt("seId");
							Double amount = object3.getDouble("amount");
							int payType = object3.getInt("payType");
							String freeTime = object3.getString("freeTime");
							n_chargeInfo = new ChargeInfo(chargeName, pay_meId,
									pay_seId, amount, payType, freeTime);
						}

						n_payInfo = new PayInfo(payStatus, n_chargeInfo);

						t_singlenotice = new SingleNotice(courseId, id,
								studentId, studentName, courseName,
								subjectName, meName, meId, seId, schoolId,
								classId, strInfo, indbtime, isReaded, meDate,
								n_payInfo);
						noticelist.add(t_singlenotice);
					}
					ret.setNoticelist(noticelist);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Exam_Con jsontoExamList(String jsonString) {
		Exam_Con ret = new Exam_Con();
		ArrayList<SingleExam> singleexamlist = null;
		SingleExam singleexam = null;
		ArrayList<SingleReport> single;
		SingleReport singleline;
		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);
				int total = object.getInt("totalCount");
				ret.setTotal(total);
				JSONArray multiReportList = new JSONArray(
						object.getString("multiReportList"));
				// //Log.i("Lichg", "msgList="+msgList);
				if (multiReportList != null) {
					singleexamlist = new ArrayList<SingleExam>();
					for (int i = 0; i < multiReportList.length(); i++) {
						singleexam = new SingleExam();
						single = new ArrayList<SingleReport>();
						JSONObject objectmarkinfo = new JSONObject(
								multiReportList.getJSONObject(i).getString(
										"markInfo"));
						int meId = objectmarkinfo.getInt("meId");
						String meName = objectmarkinfo.getString("meName");
						String meDate = objectmarkinfo.getString("meDate");
						int fullScore = objectmarkinfo.getInt("fullScore");
						int MaxScore = objectmarkinfo.getInt("MaxScore");
						int studentCount = objectmarkinfo
								.getInt("studentCount");
						String exam_info = "（参考人数：" + studentCount + "人，满分"
								+ fullScore + "分，联考最高分" + MaxScore + "分）";
						singleexam.setExam_name(meName);
						singleexam.setExam_info(exam_info);
						singleexam.setExam_time(meDate);
						singleexam.setMeId(meId);
						JSONArray objectdetailList = new JSONArray(
								multiReportList.getJSONObject(i).getString(
										"detailList"));
						for (int j = 0; j < objectdetailList.length(); j++) {
							JSONObject objectdetailListsingle = objectdetailList
									.getJSONObject(j);
							double s_avgScore = objectdetailListsingle
									.getDouble("avgScore");
							String s_classId = objectdetailListsingle
									.getString("classId");
							int s_classOrder = objectdetailListsingle
									.getInt("classOrder");
							int s_courseId = objectdetailListsingle
									.getInt("courseId");
							String s_courseName = objectdetailListsingle
									.getString("courseName");
							double s_fullScore = objectdetailListsingle
									.getInt("fullScore");
							int s_graderOrder = objectdetailListsingle
									.getInt("graderOrder");
							int hideStudentOrder = objectdetailListsingle
									.getInt("hideStudentOrder");
							String s_markInfoString = objectdetailListsingle
									.getString("markInfoString");
							double s_maxScore = objectdetailListsingle
									.getDouble("maxScore");
							int s_meId = objectdetailListsingle.getInt("meId");
							int s_seId = objectdetailListsingle.getInt("seId");
							double s_score = objectdetailListsingle
									.getDouble("score");
							// int s_score =
							// objectdetailListsingle.getInt("score");
							// float s_score =
							// objectdetailListsingle.getFloat("score");
							// double s_score2 =
							// objectdetailListsingle.getDouble("score");
							// float s_score = (float) s_score2;

							// Log.i("miss",
							// "markInfoString="+s_markInfoString);
							// String course_name = "您的" + s_courseName + " ： ";
							// String course_score = s_score + "分";
							// String singleinfo = "班排前" + s_classOrder +
							// "名，年排前"+ s_graderOrder + "名，年级最高分" + s_maxScore +
							// "分，平均分" + s_avgScore + "分";
							String singleinfo = s_markInfoString;
							// 支付信息
							PayInfo e_payInfo = null;
							JSONObject object2 = objectdetailListsingle
									.optJSONObject("payInfo");
							int payStatus = object2.getInt("payStatus");
							String chargeInfo = object2.getString("chargeInfo");

							ChargeInfo e_chargeInfo = null;
							if (chargeInfo != null
									&& chargeInfo.startsWith("{")) {
								JSONObject object3 = object2
										.getJSONObject("chargeInfo");

								String chargeName = object3
										.getString("chargeName");
								int pay_meId = object3.getInt("meId");
								int pay_seId = object3.getInt("seId");
								Double amount = object3.getDouble("amount");
								int payType = object3.getInt("payType");
								String freeTime = object3.getString("freeTime");

								e_chargeInfo = new ChargeInfo(chargeName,
										pay_meId, pay_seId, amount, payType,
										freeTime);
							}

							e_payInfo = new PayInfo(payStatus, e_chargeInfo);

							SpannableStringBuilder Se_info_s = new SpannableStringBuilder();

							singleline = new SingleReport(s_courseName,
									s_courseId, s_courseName, s_score,
									singleinfo, Se_info_s, s_meId, s_seId,
									s_classId, e_payInfo);
							single.add(singleline);
						}
						singleexam.setReportlist(single);
						singleexamlist.add(singleexam);

					}
					ret.setExamlist(singleexamlist);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Single_subject_scores jsontosingle(String jsonString) {
		Single_subject_scores ret = new Single_subject_scores();

		if (jsonString != null) {
			try {
				int i = 0;
				JSONObject object = new JSONObject(jsonString);
				if (object.getJSONObject("personAnalysisInfo") != null) {
					ret.setPersonAnalysisInfo(object.getJSONObject(
							"personAnalysisInfo").getString("markInfo"));
					ret.setScore(object.getJSONObject("personAnalysisInfo")
							.getDouble("score"));
					JSONArray obj_classScoreInfoList = new JSONArray(object
							.getJSONObject("personAnalysisInfo").getString(
									"classScoreInfoList"));
					if (obj_classScoreInfoList != null) {
						ArrayList<Single_classScoreInfo> classScoreInfoList = new ArrayList<Single_classScoreInfo>();
						for (i = 0; i < obj_classScoreInfoList.length(); i++) {
							String label = obj_classScoreInfoList
									.getJSONObject(i).getString("label");
							double minScore = 0;// obj_classScoreInfoList.getJSONObject(i).getDouble("minScore");
							double maxScore = 0;// obj_classScoreInfoList.getJSONObject(i).getDouble("maxScore");
							int count = obj_classScoreInfoList.getJSONObject(i)
									.getInt("count");
							int isMyFlag = obj_classScoreInfoList
									.getJSONObject(i).getInt("isMyFlag");
							String groupFlag = obj_classScoreInfoList
									.getJSONObject(i).getString("groupFlag");
							classScoreInfoList.add(new Single_classScoreInfo(
									label, minScore, maxScore, count, isMyFlag,
									groupFlag));
						}
						ret.setClassScoreInfoList(classScoreInfoList);
					}
				}
				if (object.getJSONObject("qstTagAnalysisInfo") != null) {
					String markInfo = object
							.getJSONObject("qstTagAnalysisInfo").getString(
									"markInfo");
					if (markInfo != "null")
						ret.setQstTagAnalysisInfo(markInfo);
					else
						ret.setQstTagAnalysisInfo("无");
					if (object.getJSONObject("qstTagAnalysisInfo").getString(
							"tagInfoList") != null
							&& object.getJSONObject("qstTagAnalysisInfo")
									.getString("tagInfoList") != "null") {
						JSONArray obj_tagInfoList = new JSONArray(object
								.getJSONObject("qstTagAnalysisInfo").getString(
										"tagInfoList"));
						if (obj_tagInfoList != null) {
							ArrayList<Single_tagInfoList> tagInfoList = new ArrayList<Single_tagInfoList>();
							for (i = 0; i < obj_tagInfoList.length(); i++) {
								int seId = 0;// obj_tagInfoList.getJSONObject(i).getInt("seId");
								String tagName = obj_tagInfoList.getJSONObject(
										i).getString("tagName");
								int count = 0;// obj_tagInfoList.getJSONObject(i).getInt("count");
								double fullScore = 0;// obj_tagInfoList.getJSONObject(i).getDouble("fullScore");
								double score = obj_tagInfoList.getJSONObject(i)
										.getDouble("score");
								double classScore = 0;// obj_tagInfoList.getJSONObject(i).getDouble("classScore");
								double gradeScore = 0;// obj_tagInfoList.getJSONObject(i).getDouble("gradeScore");
								double examScore = 0;// obj_tagInfoList.getJSONObject(i).getDouble("examScore");
								double scoreRate = obj_tagInfoList
										.getJSONObject(i)
										.getDouble("scoreRate");
								double classScoreRate = obj_tagInfoList
										.getJSONObject(i).getDouble(
												"classScoreRate");
								double gradeScoreRate = obj_tagInfoList
										.getJSONObject(i).getDouble(
												"gradeScoreRate");
								double examScoreRate = obj_tagInfoList
										.getJSONObject(i).getDouble(
												"examScoreRate");
								int flag = obj_tagInfoList.getJSONObject(i)
										.getInt("flag");
								tagInfoList.add(new Single_tagInfoList(seId,
										tagName, count, fullScore, score,
										classScore, gradeScore, examScore,
										scoreRate, classScoreRate,
										gradeScoreRate, examScoreRate, flag));
							}
							ret.setTagInfoList(tagInfoList);
						}
					}
				}
				JSONArray obj_videoList = new JSONArray(
						object.getString("videoList"));
				if (obj_videoList != null) {
					ArrayList<videoList> videoList = new ArrayList<videoList>();
					for (i = 0; i < obj_videoList.length(); i++) {
						String wkId = obj_videoList.getJSONObject(i).getString(
								"wkId");
						String wkName = obj_videoList.getJSONObject(i)
								.getString("wkName");
						videoList.add(new videoList(wkId, wkName));
					}
					ret.setVideoList(videoList);
				}
				if (null != object.optJSONObject("carelessInfo")) {
					ret.setCarelessInfo_courseName(object.getJSONObject(
							"carelessInfo").getString("courseName"));
					ret.setCarelessInfo_markInfo(object.getJSONObject(
							"carelessInfo").getString("markInfo"));
					ret.setCarelessInfo_scoreRate(object.getJSONObject(
							"carelessInfo").getDouble("scoreRate"));
					ret.setCarelessInfo_carelessIndex(object.getJSONObject(
							"carelessInfo").getDouble("carelessIndex"));

					JSONArray careless_List = new JSONArray(object
							.getJSONObject("carelessInfo").getString(
									"classCareLessList"));
					if (careless_List != null) {
						ArrayList<CarelessInfo> carelessList = new ArrayList<CarelessInfo>();
						for (int j = 0; j < careless_List.length(); j++) {
							double scoreRate = careless_List.getJSONObject(j)
									.getDouble("scoreRate");
							double carelessIndex = careless_List.getJSONObject(
									j).getDouble("carelessIndex");
							carelessList.add(new CarelessInfo(scoreRate,
									carelessIndex));
						}
						ret.setCarelessList(carelessList);
					}
				}
				System.out.print("数据=" + object.getString("scoreList"));
				JSONArray obj_scoreList = new JSONArray(
						object.getString("scoreList"));
				System.out.print("数据长度=" + obj_scoreList.length());
				if (obj_scoreList != null) {
					ArrayList<Single_scoreList> scoreList = new ArrayList<Single_scoreList>();
					for (i = 0; i < obj_scoreList.length(); i++) {
						int seId = 0;// obj_scoreList.getJSONObject(i).getInt("seId");
						String tagName = "";// obj_scoreList.getJSONObject(i).getString("tagName");
						int displayIndex = obj_scoreList.getJSONObject(i)
								.getInt("displayIndex");
						String displayName = obj_scoreList.getJSONObject(i)
								.getString("displayName");
						int answerType = obj_scoreList.getJSONObject(i).getInt(
								"answerType");
						double fullScore = obj_scoreList.getJSONObject(i)
								.getDouble("fullScore");
						double personScore = obj_scoreList.getJSONObject(i)
								.getDouble("personScore");
						double classScore = obj_scoreList.getJSONObject(i)
								.getDouble("classScore");
						double gradeScore = obj_scoreList.getJSONObject(i)
								.getDouble("gradeScore");
						double examScore = obj_scoreList.getJSONObject(i)
								.getDouble("examScore");
						int scoreFlag = obj_scoreList.getJSONObject(i).getInt(
								"scoreFlag");
						String wkId = obj_scoreList.getJSONObject(i).getString(
								"wkId");
						String wkName = obj_scoreList.getJSONObject(i)
								.getString("wkName");
						int wkFlag = obj_scoreList.getJSONObject(i).getInt(
								"wkFlag");
						int excellentFlag = obj_scoreList.getJSONObject(i)
								.getInt("excellentFlag");
						double eqDiffculty = obj_scoreList.getJSONObject(i)
								.getDouble("eqDiffculty");
						String answerXy = obj_scoreList.getJSONObject(i)
								.getString("answerXy");
						String contentXy = obj_scoreList.getJSONObject(i)
								.getString("contentXy");
						int pageIndex = obj_scoreList.getJSONObject(i).getInt(
								"pageIndex");
						// System.out.println("=============="+answerXy);
						// System.out.println("=============="+contentXy);
						scoreList.add(new Single_scoreList(seId, tagName,
								displayIndex, displayName, answerType,
								fullScore, personScore, classScore, gradeScore,
								examScore, scoreFlag, wkId, wkName, wkFlag,
								excellentFlag, eqDiffculty, answerXy,
								contentXy, pageIndex));
					}
					ret.setScoreList(scoreList);
				}
				JSONArray obj_pageList = new JSONArray(
						object.getString("pageList"));
				if (obj_pageList != null) {
					ArrayList<Single_pageList> pageList = new ArrayList<Single_pageList>();
					for (i = 0; i < obj_pageList.length(); i++) {
						int seId = obj_pageList.getJSONObject(i).getInt("seId");
						int pageIndex = obj_pageList.getJSONObject(i).getInt(
								"pageIndex");
						String pageUrl = obj_pageList.getJSONObject(i)
								.getString("pageUrl");
						String pageWh = obj_pageList.getJSONObject(i)
								.getString("pageWh");
						int width = obj_pageList.getJSONObject(i).getInt(
								"width");
						int height = obj_pageList.getJSONObject(i).getInt(
								"height");
						String prefixPageUrl = obj_pageList.getJSONObject(i)
								.getString("prefixPageUrl");
						pageList.add(new Single_pageList(seId, pageIndex,
								pageUrl, pageWh, width, height, prefixPageUrl));
					}
					ret.setPageList(pageList);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Grade_Report jsontoGradeList(String jsonString) {
		Grade_Report grade_Report = new Grade_Report();

		ArrayList<Grade_CourseScore> courseScores = null;
		ArrayList<Casa_detail> casaliList = null;
		ArrayList<Gasa_detal> gasa_List = null;
		ArrayList<Unbalance_detail> unbalance_list = null;

		Grade_CourseScore grade_CourseScore = null;
		Casa_detail casa_detail = null;
		Gasa_detal gasa_detal = null;
		Unbalance_detail unbalance_detail = null;
		UnbalanceInfo unbalanceInfo = null;

		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);

				// JSONObject courseObject = (JSONObject) object
				// .get("courseAnaInfo");
				// JSONObject casaObject = (JSONObject) object.get("casaInfo");
				// JSONObject gasaObject = (JSONObject) object.get("gasaInfo");
				// JSONObject unbanlanceObject = (JSONObject) object
				// .get("unbalancedInfo");
				JSONObject courseObject = object.getJSONObject("courseAnaInfo");
				JSONObject casaObject = object.getJSONObject("casaInfo");
				JSONObject gasaObject = object.getJSONObject("gasaInfo");
				JSONObject unbanlanceObject = object
						.getJSONObject("unbalancedInfo");

				// JSONArray coursedetailList = (JSONArray) courseObject
				// .get("courseScoreList");
				// JSONArray casadetailList = (JSONArray) casaObject
				// .get("offsetList");
				// JSONArray gasadetailList = (JSONArray) gasaObject
				// .get("offsetList");
				// JSONArray unbalancedetailList = (JSONArray) unbanlanceObject
				// .get("detailList");
				JSONArray coursedetailList = courseObject
						.getJSONArray("courseScoreList");
				JSONArray casadetailList = casaObject
						.getJSONArray("offsetList");
				JSONArray gasadetailList = gasaObject
						.getJSONArray("offsetList");
				JSONArray unbalancedetailList = unbanlanceObject
						.getJSONArray("detailList");

				courseScores = new ArrayList<Grade_CourseScore>();
				casaliList = new ArrayList<Casa_detail>();
				gasa_List = new ArrayList<Gasa_detal>();
				unbalance_list = new ArrayList<Unbalance_detail>();
				String courseMark = courseObject.getString("markInfo");

				String markInfo = unbanlanceObject.getString("markInfo");
				int avgDegreeRate = unbanlanceObject.getInt("avgDegreeRate");

				for (int i = 0; i < unbalancedetailList.length(); i++) {
					JSONObject unbdetailsingle = unbalancedetailList
							.getJSONObject(i);

					String courseName = unbdetailsingle.getString("courseName");
					int degreeRate = unbdetailsingle.getInt("degreeRate");
					int avgRate = unbdetailsingle.getInt("avgDegreeRate");
					int degreeRateOffset = unbdetailsingle
							.getInt("degreeRateOffset");

					unbalance_detail = new Unbalance_detail(courseName,
							degreeRate, avgRate, degreeRateOffset);
					unbalance_list.add(unbalance_detail);
				}
				unbalanceInfo = new UnbalanceInfo(markInfo, avgDegreeRate,
						unbalance_list);

				for (int i = 0; i < coursedetailList.length(); i++) {
					JSONObject coursedetailListsingle = coursedetailList
							.getJSONObject(i);
					String courseId = coursedetailListsingle
							.getString("courseId");
					String courseName = coursedetailListsingle
							.getString("courseName");
					double score = coursedetailListsingle.getDouble("score");
					double classAvgScore = coursedetailListsingle
							.getDouble("classAvgScore");
					double gradeAvgScore = coursedetailListsingle
							.getDouble("gradeAvgScore");
					int classPercentRate = coursedetailListsingle
							.getInt("classPercentRate");
					int scorePercentRate = coursedetailListsingle
							.optInt("scorePercentRate");

					grade_CourseScore = new Grade_CourseScore(courseId,
							courseName, score, classAvgScore, gradeAvgScore,
							classPercentRate, scorePercentRate);
					courseScores.add(grade_CourseScore);
				}
				for (int i = 0; i < casadetailList.length(); i++) {
					JSONObject casadetailsingle = casadetailList
							.getJSONObject(i);

					String courseName = casadetailsingle
							.getString("courseName");
					double score = casadetailsingle.getDouble("score");
					double classAvgScore = casadetailsingle
							.getDouble("classAvgScore");
					double offset = casadetailsingle.getDouble("offset");

					casa_detail = new Casa_detail(courseName, score,
							classAvgScore, offset);
					casaliList.add(casa_detail);
				}
				for (int i = 0; i < gasadetailList.length(); i++) {
					JSONObject gasadetailsingle = gasadetailList
							.getJSONObject(i);

					String courseName = gasadetailsingle
							.getString("courseName");
					double score = gasadetailsingle.getDouble("score");
					double gradeAvgScore = gasadetailsingle
							.getDouble("gradeAvgScore");
					double offset = gasadetailsingle.getDouble("offset");

					gasa_detal = new Gasa_detal(courseName, score,
							gradeAvgScore, offset);
					gasa_List.add(gasa_detal);
				}

				grade_Report.setCourselist(courseScores);
				grade_Report.setCasaList(casaliList);
				grade_Report.setGasaList(gasa_List);
				grade_Report.setUnbalanceInfo(unbalanceInfo);
				grade_Report.setCourseMark(courseMark);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return grade_Report;
	}

	public static ExplainInfo jsontoExplainList(String jsonString) {
		ExplainInfo explainInfo = new ExplainInfo();
		ArrayList<Explain_single_info> eList = null;
		Explain_single_info single_info = null;

		if (jsonString != null) {

			try {
				JSONObject object = new JSONObject(jsonString);
				JSONObject commonObject = object.getJSONObject("commonInfo");
				String e_meName = commonObject.getString("meName");
				String e_courseName = commonObject.getString("courseName");
				String e_meDate = commonObject.getString("meDate");
				String e_studentCount = commonObject.getString("studentCount");
				String e_fullScore = commonObject.getString("fullScore");
				String e_maxScore = commonObject.getString("maxScore");

				if (object.get("detailInfo").equals(null)) {
				} else {
					JSONArray infoArray = object.getJSONArray("detailInfo");

					eList = new ArrayList<Explain_single_info>();
					for (int i = 0; i < infoArray.length(); i++) {
						JSONObject infoObject = infoArray.getJSONObject(i);

						String type = infoObject.getString("type");
						int displayIndex = infoObject.getInt("displayIndex");
						double difficulty = infoObject.getDouble("difficulty");
						double explain_fullScore = infoObject
								.getDouble("fullScore");
						double score = infoObject.getDouble("score");

						if (type.equals("QuestionExplainMsgDetail")) {
							String wkId = infoObject.getString("wkId");
							String wkName = infoObject.getString("wkName");
							String displayName = infoObject
									.getString("displayName");
							// String pathUrl = infoObject.getString("pathUrl");
							single_info = new Explain_single_info(type,
									displayIndex, difficulty,
									explain_fullScore, score, wkId, wkName,/*
																			 * pathUrl
																			 * ,
																			 */
									displayName, null, null, null, null, null);
						} else if (type.equals("ExcellentQuestionMsgDetail")) {
							String displayName = infoObject
									.getString("displayName");
							String excellentXywh = infoObject
									.getString("excellentXywh");
							String prefixUrl = infoObject
									.getString("prefixUrl");
							String pageUrl = infoObject.getString("pageUrl");
							String answerXywh = infoObject
									.getString("answerXywh");
							single_info = new Explain_single_info(type,
									displayIndex, difficulty,
									explain_fullScore, score, null, null, /* null, */
									displayName, null, excellentXywh,
									prefixUrl, pageUrl, answerXywh);
						} else if (type.equals("ErrorQuestionMsgDetail")) {
							String displayName = infoObject
									.getString("displayName");
							String errorXywh = infoObject
									.getString("errorXywh");
							String prefixUrl = infoObject
									.getString("prefixUrl");
							String pageUrl = infoObject.getString("pageUrl");
							String answerXywh = infoObject
									.getString("answerXywh");
							single_info = new Explain_single_info(type,
									displayIndex, difficulty,
									explain_fullScore, score, null, null, /* null, */
									displayName, errorXywh, null, prefixUrl,
									pageUrl, answerXywh);
						}
						eList.add(single_info);
					}
				}
				explainInfo.setCourseName(e_courseName);
				explainInfo.setExplain_single_infosList(eList);
				explainInfo.setFullScore(e_fullScore);
				explainInfo.setMaxScore(e_maxScore);
				explainInfo.setMeDate(e_meDate);
				explainInfo.setMeName(e_meName);
				explainInfo.setStudentCount(e_studentCount);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return explainInfo;
	}

	public static Login_school_con jsontoschoollist(String jsonString) {
		Login_school_con ret = new Login_school_con();
		ret.setSchoollist(null);
		if (jsonString != null) {
			try {
				JSONObject object = new JSONObject(jsonString);
				Log.i("Lichg", "object" + object);
				JSONArray schoolList = new JSONArray(
						object.getString("schoolList"));
				ret.setResultCode(object.getInt("resultCode"));
				ret.setResultDesc(object.getString("resultDesc"));
				ret.setSchoolNameRegular(object.getString("schoolNameRegular"));
				Login_school loginschool = null;
				Log.i("Lichg", "schoolList" + schoolList);
				if (schoolList != null) {
					ArrayList<Login_school> loginschoollist = new ArrayList<Login_school>();
					for (int i = 0; i < schoolList.length(); i++) {
						JSONObject objectlist = schoolList.getJSONObject(i);
						int schoolId = objectlist.getInt("schoolId");
						int areaId = objectlist.getInt("areaId");
						String name = objectlist.getString("name");
						String areaName = objectlist.getString("areaName");
						int status = objectlist.getInt("status");
						// //String courseName =
						// objectlist.getString("courseName");
						loginschool = new Login_school(schoolId, areaId, name,
								areaName, status);
						loginschoollist.add(loginschool);
					}
					ret.setSchoollist(loginschoollist);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Login_school_config jsontoschoolconfig(String jsonString) {
		Login_school_config loginschoolconfig = null;
		Log.i("Lichg", "jsonString" + jsonString);
		if (jsonString != null) {
			try {
				Log.i("Lichg", "jsonString" + jsonString);
				JSONObject object = new JSONObject(jsonString);
				JSONObject configInfo = object.getJSONObject("configInfo");
				if (configInfo != null) {
					Log.i("Lichg", "configInfo" + configInfo);
					int asId = configInfo.getInt("asId");
					String appId = configInfo.getString("appId");
					String appName = configInfo.getString("appName");
					int serverId = configInfo.getInt("serverId");
					String serverName = configInfo.getString("serverName");
					int areaId = configInfo.getInt("areaId");
					String areaName = configInfo.getString("areaName");
					int schoolId = configInfo.getInt("schoolId");
					String schoolName = configInfo.getString("schoolName");
					String status = configInfo.getString("status");
					String loginServUrl = configInfo.getString("loginServUrl");
					String fileServUrl = configInfo.getString("fileServUrl");
					String reportServUrl = configInfo
							.getString("reportServUrl");
					String weiKeServUrl = configInfo.getString("weiKeServUrl");
					loginschoolconfig = new Login_school_config(asId, appId,
							appName, serverId, serverName, areaId, areaName,
							schoolId, schoolName, status, loginServUrl,
							fileServUrl, reportServUrl, weiKeServUrl);
				}
				return loginschoolconfig;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * json arry 结构，其中appconfig 是个xml字符串
	 */

	public static Login_school_config analys_Login_schconfig(String jsonString) {
		try {
			JSONArray school_configarry = new JSONArray(jsonString);
			if (school_configarry != null && school_configarry.length() > 0) {
				JSONObject jsonresult = school_configarry.getJSONObject(0);
				if (jsonresult != null) {
					Login_school_config loginschoolconfig = new Login_school_config();
					int asId = jsonresult.optInt("asId");
					String appId = jsonresult.optString("appId");
					String appName = jsonresult.optString("appName");
					int serverId = jsonresult.optInt("serverId");
					String serverName = jsonresult.optString("serverName");
					int areaId = jsonresult.optInt("areaId");
					String areaName = jsonresult.optString("areaName");
					int schoolId = jsonresult.optInt("schoolId");
					String schoolName = jsonresult.optString("schoolName");
					String status = jsonresult.optString("status");

					loginschoolconfig.setAsId(asId);
					loginschoolconfig.setAppId(appId);
					loginschoolconfig.setAppName(appName);
					loginschoolconfig.setServerId(serverId);
					loginschoolconfig.setSchoolName(serverName);
					loginschoolconfig.setAreaId(areaId);
					loginschoolconfig.setAreaName(areaName);
					loginschoolconfig.setSchoolId(schoolId);
					loginschoolconfig.setSchoolName(schoolName);
					loginschoolconfig.setStatus(status);

					String appConfig = jsonresult.optString("appConfig");

					String fileServUrl = "";
					// String reportServUrl = "";
					String weiKeServUrl = "";
					String basecenterUrl = "";
					String digital_camplusUrl = "";
					String webSerUrl = "";
					String appcenterUrl = "";

					if (appConfig != null && appConfig.length() > 0) {
						XmlPullParser xmlPullParser = Xml.newPullParser();
						// parser.setInput(appConfig.get, "UTF-8");
						InputStream ins = new ByteArrayInputStream(
								appConfig.getBytes());
						try {
							xmlPullParser.setInput(ins, "UTF-8");
							int eventType = xmlPullParser.getEventType();
							while (eventType != XmlPullParser.END_DOCUMENT) {
								switch (eventType) {
								case XmlPullParser.START_TAG:
									if (xmlPullParser.getName().equals(
											"digital_camplus")) {

										digital_camplusUrl = xmlPullParser
												.getAttributeValue(0);
									}
									/*
									 * if(xmlPullParser.getName().equals("report"
									 * )){ // xmlPullParser.next();
									 * reportServUrl =
									 * xmlPullParser.getAttributeValue(0); }
									 */
									if (xmlPullParser.getName().equals(
											"appcenter")) {
										// xmlPullParser.next();
										appcenterUrl = xmlPullParser
												.getAttributeValue(0);
									}
									if (xmlPullParser.getName().equals(
											"basecenter")) {
										// xmlPullParser.next();

										basecenterUrl = xmlPullParser
												.getAttributeValue(0);
									}
									if (xmlPullParser.getName().equals("web")) {
										// xmlPullParser.next();

										webSerUrl = xmlPullParser
												.getAttributeValue(0);
									}
									if (xmlPullParser.getName().equals("file")) {
										// xmlPullParser.next();

										fileServUrl = xmlPullParser
												.getAttributeValue(0);
									}
									if (xmlPullParser.getName().equals("weike")) {
										// xmlPullParser.next();
										weiKeServUrl = xmlPullParser
												.getAttributeValue(0);
									}
									break;
								case XmlPullParser.END_TAG:
									break;
								default:
									break;
								}
								eventType = xmlPullParser.next();
							}
							//
							String loginServUrl = basecenterUrl
									+ "BaseCenter/rest/service/loginM";
							loginschoolconfig.setLoginServUrl(loginServUrl);
							loginschoolconfig.setBasecenterUrl(basecenterUrl);
							loginschoolconfig.setFileServUrl(fileServUrl);
							loginschoolconfig.setReportServUrl(appcenterUrl);
							loginschoolconfig.setWeiKeServUrl(weiKeServUrl);
							loginschoolconfig.setWebUrl(webSerUrl);
							loginschoolconfig
									.setDigital_camplusUrl(digital_camplusUrl);
							return loginschoolconfig;

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Login jsontologinuserInfo(String jsonString) {
		Login loginuserInfo = null;
		if (jsonString != null) {
			try {

				// JSONObject object = new JSONObject(jsonString);
				/*
				 * loginuserInfo.setResultCode(object.getInt("resultCode"));
				 * loginuserInfo.setResultDesc(object.getString("resultDesc"));
				 */
				// if (object.getInt("resultCode") != 0)
				// return loginuserInfo;

				JSONArray obj_userInfoList = new JSONArray(jsonString);
				if (obj_userInfoList != null) {

					String userId = obj_userInfoList.getJSONObject(0)
							.getString("userId");
					String loginName = obj_userInfoList.getJSONObject(0)
							.getString("loginName");
					String userCode = obj_userInfoList.getJSONObject(0)
							.getString("userCode");
					String sysCode = obj_userInfoList.getJSONObject(0)
							.getString("sysCode");
					int type = obj_userInfoList.getJSONObject(0).getInt("type");
					String email = obj_userInfoList.getJSONObject(0).getString(
							"email");
					String mobile = obj_userInfoList.getJSONObject(0)
							.getString("mobile");
					String name = obj_userInfoList.getJSONObject(0).getString(
							"name");
					String nickName = obj_userInfoList.getJSONObject(0)
							.getString("nickName");
					int status = obj_userInfoList.getJSONObject(0).getInt(
							"status");
					int areaId = obj_userInfoList.getJSONObject(0).getInt(
							"areaId");
					int schoolId = obj_userInfoList.getJSONObject(0).getInt(
							"schoolId");
					int appUsed = obj_userInfoList.getJSONObject(0).getInt(
							"appUsed");
					loginuserInfo = new Login();
					loginuserInfo.setResultCode(0);
					loginuserInfo.setResultDesc("Sucess");
					loginuserInfo.setUserInfoList(new Login_userInfo(userId,
							loginName, userCode, sysCode, type, email, mobile,
							name, nickName, status, areaId, schoolId, appUsed));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return loginuserInfo;
	}

	public static SchoolBadge StartJson(String result) {
		SchoolBadge schoolBadge = null;
		try {
			JSONObject jsonObject1 = new JSONObject(result);
			schoolBadge = new SchoolBadge();
			schoolBadge.setResultDesc(jsonObject1.getString("resultDesc"));
			schoolBadge.setResultCode(jsonObject1.getInt("resultCode"));
			JSONObject jsonObject2 = jsonObject1.getJSONObject("personalInfo");
			PersonalInfo personalInfo = new PersonalInfo();
			personalInfo.setName(jsonObject2.getString("name"));
			personalInfo.setSchoolBadgeVisitePath(jsonObject2
					.getString("schoolBadgeVisitePath"));
			schoolBadge.setPersonalInfo(personalInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schoolBadge;
	}
}
