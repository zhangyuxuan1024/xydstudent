package com.xyd.student.xydexamanalysis.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.Fragment.NoticeFragment;
import com.xyd.student.xydexamanalysis.adapter.MyknowledgeAdapter;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.CarelessInfo;
import com.xyd.student.xydexamanalysis.entity.Single_pageList;
import com.xyd.student.xydexamanalysis.entity.Single_scoreList;
import com.xyd.student.xydexamanalysis.entity.Single_subject_scores;
import com.xyd.student.xydexamanalysis.utils.DoubleUtils;
import com.xyd.student.xydexamanalysis.utils.GsonUtil;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.StringUtils;
import com.xyd.student.xydexamanalysis.utils.SubjectIconUtil;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.view.DetailTitle;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;
import com.xyd.student.xydexamanalysis.view.MyScrollListView;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import com.xyd.student.xydexamanalysis.view.MyImageView;
import android.view.View;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class SingReportActivity extends Activity implements OnClickListener,
		LoadingListener, TitleOnClickListener {
	private TitleBar titleBar;
	private DetailTitle titleDetail;
	private ImageView knowledge_lei_icon,knowledge_wu_zhishidian,explain_wu_jiangjie,my_exam_wu_shijuan;
	private TextView typeName, typeInfo, exam_name, paper_explain_name,
			knowledge_name, knowledge_info, careless_name, careless_use_name,
			careless_scoring_degree, careless_info, anwser_name,
			single_report_title2;
	private LinearLayout knowledge_sheet;
	private HorizontalScrollView my_exam_paper;
	private LoadingHelper loadingHelper;
	private Single_subject_scores display;
	private ArrayList<MyImageView> myexampaperlist;
	private DisplayMetrics metrics;
	private MyScrollListView Myknowledgelist;
	private BarChart barChart;
	private Bundle bunde;
	private Intent intent;
	private LoginUtils login;
	private JSONObject param;
	private Context context;
	private ArrayList<String> tagNameList;
	private String studentName;
	private double[] scoreRates;
	private double[] classRates;
	private double[] gradeRates;
	private double[] examRates;
	private String Signal_notice_url;

	String fileurl = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = this;
		this.intent = this.getIntent();
		this.bunde = intent.getExtras();
		login = new LoginUtils();

		setContentView(R.layout.activity_single_report);
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);
		titleBar = (TitleBar) findViewById(R.id.single_report_titlebar);
		// titleBar.setLeftIcon(R.drawable.x,
		// this.getString(R.string.s_singlereport));
		titleBar.setTitle(getResources().getString(R.string.s_singlereport));
		titleBar.setTitle(bunde.getString("subject"));
		titleBar.setLeftIcon(R.drawable.back_icon);
		titleBar.setTitleClickListener(this);
		SharedPreferences msharedPreferences = this.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		Signal_notice_url = msharedPreferences.getString("ReportServUrl", "");
		if (Signal_notice_url.equals("")) {
			ToastUtils.show(context, "获取服务器地址错误，请重新登录");
			this.finish();
			return;
		} else {
			Signal_notice_url = Signal_notice_url + "sac/querysingleana";
		}

		DisplayMetrics dm = getResources().getDisplayMetrics();
		int scrWidth = (int) (dm.widthPixels);
		rate = (float) scrWidth / 1440;
		readData();
	}

	private void readData() {
		param = new JSONObject();
		try {
			param.put("type", "QuerySingleAnalyzingReqInfo");
			// param.put("studentName", bunde.getString("studentName"));
			param.put("studentId", bunde.getString("studentId"));
			param.put("classId", bunde.getString("classId"));
			param.put("schoolId", bunde.getInt("schoolId"));
			param.put("seId", bunde.getInt("seId"));
			param.put("meId", bunde.getInt("meId"));
			// param.put("userId", login.getLoginUserId());
			if (bunde.getInt("msgId") > 0) {
				param.put("msgId", bunde.getInt("msgId"));
			}
			param.put("isReaded", bunde.getInt("isReaded"));
			// Log.i("Lichg", "param=" + param);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("参数=" + param);
		// System.out.println("地址=" + Signal_notice_url);
		Signal_notice_url = "http://a.iclassmate.cn:9000/sac/querysingleana";
		Log.i("info", "单科参数=" + param);
		Log.i("info", "单科地址=" + Signal_notice_url);
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(context);

		httpUtil.request(Signal_notice_url, param,
				new MyHttpUtil.HttpCallback() {
					@Override
					public void success(String result) {
						/*
						 * String fileName = "singletest.txt";
						 * writeFileData(fileName, result);
						 */
						getInfoSuccess(result);
						noticeListUpdate();
					}

					@Override
					public void error(int state, String errorMsg) {
						loadingHelper.ShowError(errorMsg);
					}
				});
	}

	private void getInfoSuccess(String backString) {
		if (GsonUtil.checkJson(backString)) {
			Log.i("info", "----------" + backString);
			display = JsonUtils.jsontosingle(backString);
			Log.i("info", "==========" + display.toString());
			if (display == null) {
				loadingHelper.ShowEmptyData();
				return;
			}
			initView();
		} else {
			loadingHelper.ShowError(this.getString(R.string.s_loading_no_data));
		}

	}

	private void initView() {
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		single_report_title2 = (TextView) findViewById(R.id.single_report_title2);
		// titleDetail = (DetailTitle) findViewById(R.id.single_report_title);
		String time = bunde.getString("time");
		// titleDetail.setTitle(SubjectIconUtil.getIcon(bunde.getString("subject")),bunde.getString("examname"),
		// time.substring(0, 10));
		single_report_title2.setText(bunde.getString("examname"));
		ScrollView sc_main = (ScrollView) findViewById(R.id.sc_main);
		sc_main.smoothScrollTo(0, 0);

		// //总体情况
		typeName = (TextView) findViewById(R.id.type_name);
		typeInfo = (TextView) findViewById(R.id.type_info);
		typeName.setText("总体情况");
		String S_typeinfo = "\t\t\t\t"
				+ StringUtils.ToDBC(display.getPersonAnalysisInfo());
		SpannableStringBuilder msp = StringUtils
				.getSpannableStringBuilder(S_typeinfo);
		typeInfo.setText(msp);
		// 总体情况柱状图
		initBarData();

		// //试卷
		my_exam_paper = (HorizontalScrollView) findViewById(R.id.my_exam_paper);
		my_exam_wu_shijuan = (ImageView) findViewById(R.id.my_exam_wu_shijuan);
		exam_name = (TextView) findViewById(R.id.my_exam_paper_name);
		exam_name.setText("我的试卷");
		setMypaper();

		// //试题讲解
		explain_wu_jiangjie = (ImageView) findViewById(R.id.explain_wu_jiangjie);
		paper_explain_name = (TextView) findViewById(R.id.paper_explain_name);
		paper_explain_name.setText("试题讲解");
		setExplain();

		// //知识点
		knowledge_wu_zhishidian = (ImageView) findViewById(R.id.knowledge_wu_zhishidian);
		knowledge_lei_icon = (ImageView) findViewById(R.id.knowledge_lei_icon);
		knowledge_lei_icon.setOnClickListener(this);
		knowledge_name = (TextView) findViewById(R.id.knowledge_name);
		knowledge_info = (TextView) findViewById(R.id.knowledge_info);
		knowledge_name.setText("知识点");
		if (display.getQstTagAnalysisInfo().length() != 1) {
			knowledge_info.setText("\t\t\t\t"+ StringUtils.ToDBC(display.getQstTagAnalysisInfo()));			
			setknowledge();
		}else{
			knowledge_sheet = (LinearLayout) findViewById(R.id.knowledge_sheet);
			knowledge_sheet.setVisibility(View.GONE);
			knowledge_wu_zhishidian.setVisibility(View.VISIBLE);
		}

		// //粗心度
		careless_name = (TextView) findViewById(R.id.careless_name);
		careless_info = (TextView) findViewById(R.id.careless_info);
		careless_name.setText("粗心度");
		String S_careless_info = "\t\t\t\t"
				+ StringUtils.ToDBC(display.getCarelessInfo_markInfo());
		SpannableStringBuilder mspinfo = StringUtils
				.getSpannableStringBuilder(S_careless_info);
		careless_info.setText(mspinfo);

		setcarelesssheet();
		// //答题情况
		anwser_name = (TextView) findViewById(R.id.anwser_name);
		anwser_name.setText("答题情况");
		setshiti();
		loadingHelper.HideLoading(View.INVISIBLE);

	}

	private void setshiti() {
		int index = 0;
		if (display.getScoreList() == null)
			return;
		int anwser_totl = display.getScoreList().size();

		LinearLayout anwser_ti = (LinearLayout) findViewById(R.id.anwser_ti);
		LinearLayout layout_L_button = new LinearLayout(this);
		LinearLayout.LayoutParams L_ti = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) (rate * 120));
		L_ti.topMargin = (int) (rate * 25);
		L_ti.bottomMargin = (int) (rate * 25);

		layout_L_button.setLayoutParams(L_ti);
		layout_L_button.setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout.LayoutParams L_Bt = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		L_Bt.leftMargin = (int) (rate * 20);
		L_Bt.rightMargin = (int) (rate * 20);

		for (int i = 0; i < anwser_totl; i++) {
			if (index == 4) {
				index = 0;
				layout_L_button = new LinearLayout(this);
				layout_L_button.setLayoutParams(L_ti);
				layout_L_button.setOrientation(LinearLayout.HORIZONTAL);
			}
			// Button button = new Button(this);
			// button.setLayoutParams(L_Bt);
			// button.setText(display.getScoreList().get(i).getDisplayName());
			TextView textView = new TextView(this);
			textView.setLayoutParams(L_Bt);
			textView.setText(display.getScoreList().get(i).getDisplayName());
			textView.setGravity(Gravity.CENTER);
			textView.setPadding((int) (rate * 20), 0, (int) (rate * 90), 0);
			textView.setSingleLine(true);
			if (display.getScoreList().get(i).getScoreFlag() == 1) {
				textView.setBackgroundResource(R.drawable.right_selector);
				textView.setTextColor(getResources().getColor(R.color.white));
			} else if (display.getScoreList().get(i).getScoreFlag() == 2) {
				textView.setBackgroundResource(R.drawable.rw_selector);
				textView.setTextColor(getResources().getColor(R.color.white));
			} else {
				textView.setBackgroundResource(R.drawable.wrong_selector);
				textView.setTextColor(getResources().getColor(R.color.white));
			}

			textView.setId(R.id.TAG_SHITI);
			textView.setTag(R.id.TAG_SHITI, i);
			textView.setOnClickListener(this);
			layout_L_button.addView(textView);
			index++;
			if (index == 4)
				anwser_ti.addView(layout_L_button);
			if (i == anwser_totl - 1 && index != 4) {
				int emptybutton = 4 - anwser_totl % 4;
				for (int j = 0; j < emptybutton; j++) {
					Button buttonempty = new Button(this);
					buttonempty.setLayoutParams(L_Bt);
					buttonempty.setVisibility(View.INVISIBLE);
					layout_L_button.addView(buttonempty);
				}
				anwser_ti.addView(layout_L_button);
			}
		}
	}

	private void setcarelesssheet() {
		careless_use_name = (TextView) findViewById(R.id.careless_use_name);
		careless_scoring_degree = (TextView) findViewById(R.id.careless_scoring_degree);
		careless_use_name.setText(login.getStudentname() + "同学");
		careless_scoring_degree
				.setText("得分率："
						+ display.getCarelessInfo_scoreRate()
						+ "%\t\t\t粗心度："
						+ DoubleUtils.doubletwo(display
								.getCarelessInfo_carelessIndex()));
		FrameLayout setcalesssheet = (FrameLayout) findViewById(R.id.setcalesssheet);
		LinearLayout.LayoutParams L_WW_L = (LinearLayout.LayoutParams) setcalesssheet
				.getLayoutParams();
		L_WW_L.height = metrics.widthPixels * 4 / 7;
		setcalesssheet.setLayoutParams(L_WW_L);
		TextView t, s, textwidth;
		if (display.getCarelessInfo_scoreRate() >= 75
				&& display.getCarelessInfo_carelessIndex() <= 0.5) {
			t = (TextView) findViewById(R.id.c_t_1);
			s = (TextView) findViewById(R.id.c_s_1);
		} else if (display.getCarelessInfo_scoreRate() >= 75
				&& display.getCarelessInfo_carelessIndex() > 0.5) {
			t = (TextView) findViewById(R.id.c_t_2);
			s = (TextView) findViewById(R.id.c_s_2);
		} else if (display.getCarelessInfo_scoreRate() >= 50
				&& display.getCarelessInfo_scoreRate() < 75
				&& display.getCarelessInfo_carelessIndex() <= 0.5) {
			t = (TextView) findViewById(R.id.c_t_3);
			s = (TextView) findViewById(R.id.c_s_3);
		} else if (display.getCarelessInfo_scoreRate() >= 50
				&& display.getCarelessInfo_scoreRate() < 75
				&& display.getCarelessInfo_carelessIndex() > 0.5) {
			t = (TextView) findViewById(R.id.c_t_4);
			s = (TextView) findViewById(R.id.c_s_4);
		} else if (display.getCarelessInfo_scoreRate() < 50
				&& display.getCarelessInfo_carelessIndex() <= 0.5) {
			t = (TextView) findViewById(R.id.c_t_5);
			s = (TextView) findViewById(R.id.c_s_5);
		} else {
			t = (TextView) findViewById(R.id.c_t_6);
			s = (TextView) findViewById(R.id.c_s_6);
		}
		t.setBackgroundColor(getResources().getColor(R.color.single_green));
		t.setTextColor(getResources().getColor(R.color.white));
		s.setBackgroundColor(getResources().getColor(R.color.single_green));
		s.setTextColor(getResources().getColor(R.color.white));

		textwidth = (TextView) findViewById(R.id.textviewwidth);
		double width = metrics.widthPixels - textwidth.getWidth() - 13;
		double height = metrics.widthPixels * 4 / 7;

		ArrayList<CarelessInfo> carelesslist = new ArrayList<CarelessInfo>();
		carelesslist = display.getCarelessList();
		if (carelesslist != null) {
			for (int i = 0; i < carelesslist.size(); i++) {
				int x1 = (int) ((width - 34 - 19) * carelesslist.get(i)
						.getCarelessIndex());
				int y1 = (int) ((height - 34 - 6) * (1 - carelesslist.get(i)
						.getScoreRate() / 100)) + 4;
				FrameLayout.LayoutParams lParam = new FrameLayout.LayoutParams(
						(int) (rate * 25), (int) (rate * 25));
				lParam.gravity = Gravity.LEFT;
				lParam.leftMargin = x1;
				lParam.topMargin = y1;
				ImageView careless_icon2 = new ImageView(this);
				careless_icon2.setLayoutParams(lParam);
				careless_icon2.setBackgroundResource(R.drawable.pointcarele);
				setcalesssheet.addView(careless_icon2, 1);
			}
		}
		int x = (int) ((width - 34 - 19) * display
				.getCarelessInfo_carelessIndex());
		int y = (int) ((height - 34 - 6) * (1 - display
				.getCarelessInfo_scoreRate() / 100)) + 4;
		FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(
				(int) (rate * 40), (int) (rate * 40));
		lParams.gravity = Gravity.LEFT;
		lParams.leftMargin = x;
		lParams.topMargin = y;
		ImageView careless_icon = (ImageView) findViewById(R.id.caless_icon);
		careless_icon.setLayoutParams(lParams);
		careless_icon.setBackgroundResource(R.drawable.ssss);

	}

	private void initBarData() {

		ImageView color1 = (ImageView) findViewById(R.id.color1);
		ImageView color2 = (ImageView) findViewById(R.id.color2);
		ImageView color3 = (ImageView) findViewById(R.id.color3);
		color1.setBackgroundColor(getResources().getColor(R.color.barcolor1));
		color2.setBackgroundColor(getResources().getColor(R.color.barcolor2));
		color3.setBackgroundColor(getResources().getColor(R.color.barcolor3));

		barChart = (BarChart) findViewById(R.id.chart1);
		barChart.setDescription("");
		barChart.setMaxVisibleValueCount(50);
		// scaling can now only be done on x- and y-axis separately
		barChart.setPinchZoom(false);
		barChart.setExtraBottomOffset(6f);// 设置距离底边的距离
		barChart.setDrawBarShadow(false);
		barChart.setDrawGridBackground(false);

		XAxis xAxis = barChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setTextSize(6f);
		xAxis.setSpaceBetweenLabels(0);
		xAxis.setDrawGridLines(false);

		barChart.getAxisLeft().setDrawGridLines(false);
		// add a nice and smooth animation
		barChart.animateY(2500);
		barChart.getLegend().setEnabled(false);

		// TODO Auto-generated method stub
		// 设置Y轴的值
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		if (display.getClassScoreInfoList() != null)
			for (int i = 0; i < 10; i++) {
				int val1 = display.getClassScoreInfoList().get(i).getCount();

				yVals1.add(new BarEntry(val1, i));

			}
		System.out.println("yVals1" + yVals1);
		// 设置X轴标签
		ArrayList<String> xVals = new ArrayList<String>();
		if (display.getClassScoreInfoList() != null)
			for (int i = 0; i < 10; i++) {
				xVals.add(display.getClassScoreInfoList().get(i).getLabel());
			}
		// 柱形颜色
		int[] colors = new int[] { getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor1),
				getResources().getColor(R.color.barcolor2),
				getResources().getColor(R.color.barcolor2),
				getResources().getColor(R.color.barcolor3),
				getResources().getColor(R.color.barcolor3) };
		BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
		set1.setColors(colors);
		set1.setDrawValues(true);

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);

		BarData data = new BarData(xVals, dataSets);
		data.setValueFormatter(getValueFormatter());

		YAxis leftAxis = barChart.getAxisLeft();
		YAxis rightAxis = barChart.getAxisRight();
		leftAxis.setValueFormatter(getValueFormatter());
		rightAxis.setValueFormatter(getValueFormatter());

		barChart.setData(data);
		barChart.setScaleYEnabled(false);
		barChart.invalidate();
	}

	public ValueFormatter getValueFormatter() {
		ValueFormatter vf = new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				DecimalFormat decimalFormat = new DecimalFormat("");
				String s = decimalFormat.format(value);
				return s;
			}
		};
		return vf;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bar, menu);
		return true;
	}

	private void setknowledge() {
		if (display.getTagInfoList() == null)
			return;
		if (display.getTagInfoList().size() == 0)
			return;
		Myknowledgelist = (MyScrollListView) findViewById(R.id.My_knowledge_list);
		ViewGroup.LayoutParams params = Myknowledgelist.getLayoutParams();
		params.height = metrics.widthPixels / 2;
		Myknowledgelist.setLayoutParams(params);
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

		int listleng = display.getTagInfoList().size();
		for (int i = 0; i < listleng; i++) {
			Map<String, Object> item = new HashMap<String, Object>();

			double value = display.getTagInfoList().get(i).getScore();
			if (value % 1.0 == 0) {
				item.put("value", DoubleUtils.doubletoint(value));
			} else {
				item.put("value", value);
			}
			item.put("name", display.getTagInfoList().get(i).getTagName());
			item.put("deep", getKnowledgeDeep(display.getTagInfoList().get(i)
					.getScoreRate()));
			mData.add(item);
		}
		MyknowledgeAdapter knowledgeAdapter = new MyknowledgeAdapter(this,
				mData, R.layout.single_knowlege_item, new String[] { "name",
						"value", "deep" }, new int[] { R.id.knowledge_dian,
						R.id.knowledge_fen, R.id.knowledge_chengdu });
		Myknowledgelist.setAdapter(knowledgeAdapter);
		AbsListView.LayoutParams line = new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 1);
		ImageView linel = new ImageView(this);
		linel.setLayoutParams(line);
		Myknowledgelist.addFooterView(linel);
		Myknowledgelist.setClickable(false);

		tagNameList = new ArrayList<String>();
		studentName = login.getStudentname();
		scoreRates = new double[listleng];
		classRates = new double[listleng];
		gradeRates = new double[listleng];
		examRates = new double[listleng];

		for (int i = 0; i < listleng; i++) {
			tagNameList.add(display.getTagInfoList().get(i).getTagName());
			scoreRates[i] = display.getTagInfoList().get(i).getScoreRate();
			classRates[i] = display.getTagInfoList().get(i).getClassScoreRate();
			gradeRates[i] = display.getTagInfoList().get(i).getGradeScoreRate();
			examRates[i] = display.getTagInfoList().get(i).getExamScoreRate();
		}

	}

	private int getKnowledgeDeep(double de) {

		double d = de * 100;
		System.out.println("getKnowledgeDeep" + d);
		if (d >= 0 && d < 5)
			return R.drawable.star0;
		else if (d >= 5 && d < 15)
			return R.drawable.star1;
		else if (d >= 15 && d < 25)
			return R.drawable.star2;
		else if (d >= 25 && d < 35)
			return R.drawable.star3;
		else if (d >= 35 && d < 45)
			return R.drawable.star4;
		else if (d >= 45 && d < 55)
			return R.drawable.star5;
		else if (d >= 55 && d < 65)
			return R.drawable.star6;
		else if (d >= 65 && d < 75)
			return R.drawable.star7;
		else if (d >= 75 && d < 85)
			return R.drawable.star8;
		else if (d >= 85 && d < 95)
			return R.drawable.star9;
		else if (d >= 95 && d <= 100)
			return R.drawable.star10;
		else
			return R.drawable.star0;

	}

	private void setMypaper() {

		LinearLayout myexampaperview = (LinearLayout) findViewById(R.id.my_exam_paper_view);

		LinearLayout newsinglepage;
		ArrayList<Single_pageList> single_pageList = display.getPageList();
		ArrayList<Single_scoreList> scoreList = display.getScoreList();

		Log.i("info", "single_pageList=" + single_pageList.toString());
		Log.i("info", "scoreList=" + scoreList.toString());

		if (single_pageList == null || scoreList == null) {
			ImageView emptyimage = new ImageView(context);
			emptyimage.setImageResource(R.drawable.sjempty);
			myexampaperview.addView(emptyimage);
//			my_exam_paper.setVisibility(View.GONE);
//			my_exam_wu_shijuan.setVisibility(View.VISIBLE);
			return;
		}

		int getMexampage = single_pageList.size();
		if (getMexampage > 0) {
			myexampaperlist = new ArrayList<MyImageView>();
			for (int i = 0; i < getMexampage; i++) {
				ArrayList<Single_scoreList> list = new ArrayList<Single_scoreList>();
				for (int j = 0; j < scoreList.size(); j++) {
					Single_scoreList single_scoreList = scoreList.get(j);
					int pageIndex = single_scoreList.getPageIndex();
					if (pageIndex == (i + 1)) {
						list.add(single_scoreList);
					}
				}
				newsinglepage = generateSingleLayout(i, list);
				newsinglepage.setClickable(true);
				myexampaperview.addView(newsinglepage);
			}
		} else {
			ImageView emptyimage = new ImageView(context);
			emptyimage.setImageResource(R.drawable.sjempty);
			myexampaperview.addView(emptyimage);
			myexampaperview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Toast.makeText(SingReportActivity.this, "暂无试卷",Toast.LENGTH_SHORT).show();
				}
			});
			
		}
	}

	private void setExplain() {
		LinearLayout myexplain = (LinearLayout) findViewById(R.id.explain_lin);
		LinearLayout newsinglepage;
		if (display.getVideoList() == null) {
			myexplain.addView(emptySingleexplain());
			return;
		}
		int getExplainnamelist = display.getVideoList().size();
		if (getExplainnamelist > 0) {
			for (int i = 0; i < (int) (getExplainnamelist / 2); i++) {
				newsinglepage = generateSingleexplain(i, false);
				myexplain.addView(newsinglepage);
				LinearLayout.LayoutParams L_WW_L = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
				ImageView imageviewline = new ImageView(this);
				imageviewline.getResources();
				imageviewline.setLayoutParams(L_WW_L);
				imageviewline.setBackgroundColor(Color.argb(0xff, 0x00, 0x00, 0x00));
				myexplain.addView(imageviewline);
			}
			if (getExplainnamelist % 2 == 1) {
				newsinglepage = generateSingleexplain((int) (getExplainnamelist / 2), true);
				myexplain.addView(newsinglepage);
				LinearLayout.LayoutParams L_WW_L = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
				ImageView imageviewline = new ImageView(this);
				imageviewline.getResources();
				imageviewline.setLayoutParams(L_WW_L);
				imageviewline.setBackgroundColor(Color.argb(0xff, 0x00, 0x00, 0x00));
				myexplain.addView(imageviewline);
			}
		} else {
//			myexplain.addView(emptySingleexplain());
			myexplain.setVisibility(View.INVISIBLE);
			explain_wu_jiangjie.setVisibility(View.VISIBLE);
		}
	}

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	private LinearLayout generateSingleexplain(int index, Boolean left) {
		LinearLayout layout_sub_Lin = new LinearLayout(this);
		LinearLayout.LayoutParams LL_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				/* LinearLayout.LayoutParams.WRAP_CONTENT */metrics.widthPixels / 6);
		LL_WW.setMargins(0, 19, 0, 19);
		layout_sub_Lin.setLayoutParams(LL_WW);
		layout_sub_Lin.setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout.LayoutParams RL_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
		// RL_WW.setMargins(9, 0, 9, 0);
		OnClickListener wkimageclick = new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intentwk = new Intent(SingReportActivity.this,ViewVideoActivity.class);
				intentwk.putExtra("wk_reourceid",(String) view.getTag(R.id.TAG_WEIKE));
				intentwk.putExtra("wk_url", login.getWeiKeServUrl()+ "WeiKe/resource/");
				Log.e("weiki", "weikeurl:" + login.getWeiKeServUrl());
				startActivity(intentwk);
			}
		};

		ImageView imageviewone = new ImageView(this);
		imageviewone.getResources();
		imageviewone.setLayoutParams(RL_WW);

		imageviewone.setImageResource(R.drawable.play);
		layout_sub_Lin.addView(imageviewone);
		imageviewone.setClickable(true);
		imageviewone.setOnClickListener(wkimageclick);
		imageviewone.setTag(R.id.TAG_WEIKE,
				display.getVideoList().get(index * 2).getWkId());

		TextView textviewleft = new TextView(this);
		textviewleft.setGravity(Gravity.CENTER_HORIZONTAL);
		// textviewleft.setTextColor(Color.argb(0xff, 0x00, 0x00, 0x00));
		textviewleft.setText(display.getVideoList().get(index * 2).getWkName());
		textviewleft.setClickable(true);
		textviewleft.setOnClickListener(wkimageclick);
		textviewleft.setTag(R.id.TAG_WEIKE,
				display.getVideoList().get(index * 2).getWkId());
		textviewleft.setGravity(Gravity.CENTER_VERTICAL);
		textviewleft.setTextSize(14);
		textviewleft.setMaxLines(3);
		textviewleft.setLayoutParams(RL_WW);
		textviewleft.setTextColor(R.color.black);
		layout_sub_Lin.addView(textviewleft);

		LinearLayout.LayoutParams L_WW_L = new LinearLayout.LayoutParams(1,
				LinearLayout.LayoutParams.MATCH_PARENT);
		ImageView imageviewline = new ImageView(this);
		imageviewline.getResources();
		imageviewline.setLayoutParams(L_WW_L);
		imageviewline.setBackgroundColor(Color.argb(0xff, 0x00, 0x00, 0x00));
		layout_sub_Lin.addView(imageviewline);

		ImageView imageviewtwo = new ImageView(this);
		imageviewtwo.getResources();
		imageviewtwo.setLayoutParams(RL_WW);
		imageviewtwo.setImageResource(R.drawable.play);
		layout_sub_Lin.addView(imageviewtwo);
		imageviewtwo.setClickable(true);
		if (left) {
			imageviewtwo.setVisibility(View.INVISIBLE);
		} else {
			imageviewtwo.setOnClickListener(wkimageclick);
			imageviewtwo.setTag(R.id.TAG_WEIKE,
					display.getVideoList().get(index * 2 + 1).getWkId());
		}

		TextView textviewt = new TextView(this);
		textviewt.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		// textviewt.setTextColor(Color.argb(0xff, 0x00, 0x00, 0x00));
		if (left) {
			textviewt.setVisibility(View.INVISIBLE);
		} else {
			textviewt.setText(display.getVideoList().get(index * 2 + 1)
					.getWkName());
			textviewt.setOnClickListener(wkimageclick);
			textviewt.setTag(R.id.TAG_WEIKE,
					display.getVideoList().get(index * 2 + 1).getWkId());
		}
		textviewt.setGravity(Gravity.CENTER_VERTICAL);
		textviewt.setTextSize(14);
		textviewt.setMaxLines(3);
		textviewt.setLayoutParams(RL_WW);
		textviewt.setTextColor(R.color.black);
		layout_sub_Lin.addView(textviewt);

		return layout_sub_Lin;
	}

	private LinearLayout emptySingleexplain() {
		LinearLayout layout_sub_Lin = new LinearLayout(this);
		LinearLayout.LayoutParams LL_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, metrics.widthPixels / 3);
		layout_sub_Lin.setLayoutParams(LL_WW);
		layout_sub_Lin.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams RL_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		RL_WW.setMargins(19, 0, 19, 0);
		ImageView imageempty = new ImageView(this);
		imageempty.getResources();
		imageempty.setLayoutParams(RL_WW);
		imageempty.setScaleType(ScaleType.CENTER);
		imageempty.setImageResource(R.drawable.sjempty_lin);
		imageempty.setBackgroundColor(getResources().getColor(
				R.color.explainquestion_background));
		layout_sub_Lin.addView(imageempty);

		TextView textviewempty = new TextView(this);
		textviewempty.setBackgroundColor(getResources().getColor(
				R.color.explainquestion_background));
		textviewempty.setText("无试题讲解");
		textviewempty.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
		textviewempty.setTextSize(20);
		textviewempty.setLayoutParams(RL_WW);
		layout_sub_Lin.addView(textviewempty);
		return layout_sub_Lin;
	}

	private long lastClicktime = 0;
	private float rate;

	private LinearLayout generateSingleLayout(int indext,
			ArrayList<Single_scoreList> list) {
		LinearLayout layout_sub_Lin = new LinearLayout(this);
		LinearLayout.LayoutParams LL_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout_sub_Lin.setLayoutParams(LL_WW);
		layout_sub_Lin.setOrientation(LinearLayout.VERTICAL);

		MyImageView imageView = new MyImageView(context);
		imageView.getResources();
		LinearLayout.LayoutParams RL_WW = new LinearLayout.LayoutParams(
				metrics.widthPixels / 2, metrics.widthPixels / 3);
		RL_WW.setMargins(9, 19, 9, 9);
		imageView.setLayoutParams(RL_WW);

		// 判断prefixPageUrl是否存在
		// String fileurl = null;
		if (display.getPageList().get(indext).getPrefixPageUrl() != null
				&& display.getPageList().get(indext).getPrefixPageUrl()
						.startsWith("http://")) {
			fileurl = display.getPageList().get(indext).getPrefixPageUrl();
		} else {
			fileurl = login.getFileServUrl();
		}

		Log.i("info", "试卷fileurl=" + fileurl
				+ display.getPageList().get(indext).getPageUrl().substring(1));
		imageView.setTag(fileurl
				+ display.getPageList().get(indext).getPageUrl().substring(1));

		imageView.setImageResource(R.drawable.sjloading);
		// ImageDownLoader loader = new ImageDownLoader(context);
		final int[] flags = new int[list.size()];
		final int[] x = new int[list.size()];
		final int[] y = new int[list.size()];
		final int[] h = new int[list.size()];
		final int[] w = new int[list.size()];
		final double[] personScore = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Single_scoreList single_scoreList = list.get(i);
			flags[i] = single_scoreList.getScoreFlag();
			personScore[i] = single_scoreList.getPersonScore();
			String answerXy = single_scoreList.getAnswerXy();
			String[] xy = answerXy.split(",");
			x[i] = Integer.parseInt(xy[0]);
			y[i] = Integer.parseInt(xy[1]);
			w[i] = Integer.parseInt(xy[2]);
			h[i] = Integer.parseInt(xy[3]);
		}
		final int page = indext + 1;
		final OnClickListener onclick = new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (System.currentTimeMillis() - lastClicktime <= 2000) {
					return;
				}
				lastClicktime = System.currentTimeMillis();

				Intent intent = new Intent(SingReportActivity.this,ViewImageActivity.class);
				intent.putExtra("bitmap", (String) view.getTag());
				intent.putExtra("flags", flags);
				intent.putExtra("x", x);
				intent.putExtra("y", y);
				intent.putExtra("h", h);
				intent.putExtra("w", w);
				intent.putExtra("personScore", personScore);
				intent.putExtra("page", page);
				intent.putExtra("score", display.getScore());
				Log.i("Lichg",
						"(String) view.getTag()" + (String) view.getTag());
				startActivity(intent);
				// overridePendingTransition(R.anim.in_anim, R.anim.out_anim2);
			}
		};
		final OnClickListener failclick = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(SingReportActivity.this, "暂无试卷",
						Toast.LENGTH_SHORT).show();
			}
		};
		String url = fileurl
				+ display.getPageList().get(indext).getPageUrl().substring(1);
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		DisplayImageOptions imageOptions = initDisplayImageOptions();
		Log.i("SingReportActivity", "imageurl:" + imageView.getTag());
		Imageloadlistener simpleloadlistener = new Imageloadlistener(onclick,
				failclick);
		ImageLoader.getInstance().displayImage(url, imageView, imageOptions,
				simpleloadlistener);

		layout_sub_Lin.addView(imageView);
		myexampaperlist.add(imageView);

		TextView tv = new TextView(this);
		LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		if (indext == 0) {
			tv.setText("（第一页）");
		} else if (indext == 1) {
			tv.setText("（第二页）");
		} else if (indext == 2) {
			tv.setText("（第三页）");
		} else if (indext == 3) {
			tv.setText("（第四页）");
		} else {
			tv.setText("（第" + (indext + 1) + "页）");
		}

		// tv.setTextColor(Color.argb(0xff, 0x00, 0x00, 0x00));
		LP_WW.setMargins(9, 5, 9, 19);
		tv.setTextSize(15);
		tv.setLayoutParams(LP_WW);
		layout_sub_Lin.addView(tv);

		return layout_sub_Lin;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.knowledge_lei_icon:
			if (display.getTagInfoList() == null)
				return;
			if (display.getTagInfoList().size() == 0)
				return;
			Intent intent = new Intent(SingReportActivity.this,RadarActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("studentName", studentName);
			bundle.putStringArrayList("tagNameList", tagNameList);
			bundle.putDoubleArray("scoreRates", scoreRates);
			bundle.putDoubleArray("classRates", classRates);
			bundle.putDoubleArray("gradeRates", gradeRates);
			bundle.putDoubleArray("examRates", examRates);
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.in_anim, R.anim.out_anim2);
			break;
		case R.id.TAG_SHITI:
			int tag = (Integer) v.getTag(R.id.TAG_SHITI);
			Single_scoreList single_score = display.getScoreList().get(tag);

			if (single_score != null) {

				Intent intentshiti = new Intent(SingReportActivity.this,
						SingleQuestionActivity.class);
				Bundle v_ti = new Bundle();
				v_ti.putString("subject", bunde.getString("subject"));
				v_ti.putString("time", bunde.getString("time"));
				v_ti.putString("examname", bunde.getString("examname"));
				v_ti.putSerializable("list", display.getScoreList());
				v_ti.putSerializable("pagelist", display.getPageList());
				v_ti.putString("fileurl", fileurl);

				v_ti.putInt("scroindex", tag);
				v_ti.putDouble("score", display.getScore());
				// v_ti.putSerializable("info", single_score);
				intentshiti.putExtras(v_ti);
				startActivity(intentshiti);
				overridePendingTransition(R.anim.in_anim, R.anim.out_anim2);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void OnRetryClick() {
		// TODO Auto-generated method stub
		loadingHelper.ShowLoading();
		readData();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionToggleValues: {

			for (DataSet<?> set : barChart.getData().getDataSets())
				set.setDrawValues(!set.isDrawValuesEnabled());

			barChart.invalidate();
			break;
		}
		case R.id.actionToggleHighlight: {
			if (barChart.isHighlightEnabled())
				barChart.setHighlightEnabled(false);
			else
				barChart.setHighlightEnabled(true);
			barChart.invalidate();
			break;
		}
		case R.id.actionTogglePinch: {
			if (barChart.isPinchZoomEnabled())
				barChart.setPinchZoom(false);
			else
				barChart.setPinchZoom(true);

			barChart.invalidate();
			break;
		}
		case R.id.actionToggleAutoScaleMinMax: {
			barChart.setAutoScaleMinMaxEnabled(!barChart
					.isAutoScaleMinMaxEnabled());
			barChart.notifyDataSetChanged();
			break;
		}
		case R.id.actionToggleHighlightArrow: {
			if (barChart.isDrawHighlightArrowEnabled())
				barChart.setDrawHighlightArrow(false);
			else
				barChart.setDrawHighlightArrow(true);
			barChart.invalidate();
			break;
		}
		case R.id.actionToggleStartzero: {

			barChart.getAxisLeft().setStartAtZero(
					!barChart.getAxisLeft().isStartAtZeroEnabled());
			barChart.getAxisRight().setStartAtZero(
					!barChart.getAxisRight().isStartAtZeroEnabled());
			barChart.invalidate();
			break;
		}
		case R.id.animateX: {
			barChart.animateX(3000);
			break;
		}
		case R.id.animateY: {
			barChart.animateY(3000);
			break;
		}
		case R.id.animateXY: {

			barChart.animateXY(3000, 3000);
			break;
		}
		case R.id.actionToggleFilter: {

			Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER,
					25);

			if (!barChart.isFilteringEnabled()) {
				barChart.enableFiltering(a);
			} else {
				barChart.disableFiltering();
			}
			barChart.invalidate();
			break;
		}
		case R.id.actionSave: {
			if (barChart
					.saveToGallery("title" + System.currentTimeMillis(), 50)) {
				Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getApplicationContext(), "Saving FAILED!",
						Toast.LENGTH_SHORT).show();
			break;
		}
		}
		return true;
	}

	private void noticeListUpdate() {
		if (bunde.getInt("currentindex") == -1)
			return;
		Intent it = new Intent(context, MainActivity.class);
		Bundle v_it = new Bundle();
		if (bunde.getInt("currentindex") >= 0) {
			v_it.putInt("currentindex", bunde.getInt("currentindex"));
			v_it.putInt("isReaded", bunde.getInt("isReaded"));
			it.putExtras(v_it);
			Log.i("setResult", bunde.getInt("currentindex") + "/////////////"
					+ bunde.getInt("isReaded"));
			setResult(NoticeFragment.NOTICE_CODE_SHIFENTI_ACTIVITY_RESULT, it);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.in_anim2, R.anim.out_anim);
	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		// Intent intent = new Intent(this, MainActivity.class);
		// startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.in_anim2, R.anim.out_anim);
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void titleClick() {
		// TODO Auto-generated method stub

	}

	private DisplayImageOptions initDisplayImageOptions() {
		DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
		options.showImageForEmptyUri(R.drawable.sjloading);
		options.showImageOnFail(R.drawable.wu_shijuan);
		options.resetViewBeforeLoading(true);
		options.cacheInMemory(true);
		options.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2);
		options.cacheOnDisk(true);
		options.imageScaleType(ImageScaleType.NONE);
		options.bitmapConfig(Bitmap.Config.RGB_565);
		options.considerExifParams(true);
		options.displayer(new FadeInBitmapDisplayer(300));
		return options.build();
	}

	public void writeFileData(String fileName, String message) {

		try {

			// //FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE);

			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), fileName);
			FileOutputStream fout = new FileOutputStream(file);
			System.out.println(message);
			byte[] bytes = message.getBytes();
			System.out.println(bytes);
			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}
	}

	private class Imageloadlistener extends SimpleImageLoadingListener {

		private View.OnClickListener myonclick;
		private View.OnClickListener myfailclick;

		public Imageloadlistener(View.OnClickListener onclick,
				View.OnClickListener failclick) {
			this.myonclick = onclick;
			this.myfailclick = failclick;
		}

		public void onLoadingStarted(String imageUri, View view) {
			// spinner.setVisibility(View.VISIBLE);
		};

		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			// CreatDialog.creatUserToast(mcontext, null, "同步图片失败").show();
			// spinner.setVisibility(View.GONE);
			view.setTag(imageUri);
			view.setOnClickListener(myfailclick);
		};

		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			// spinner.setVisibility(View.GONE);
			view.setTag(imageUri);
			view.setOnClickListener(myonclick);
		};
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SingReportActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SingReportActivity");
		MobclickAgent.onPause(this);
	}
}