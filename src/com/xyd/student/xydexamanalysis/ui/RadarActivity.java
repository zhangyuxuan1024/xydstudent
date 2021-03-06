package com.xyd.student.xydexamanalysis.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

import org.w3c.dom.Text;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.listener.RadarChartOndrawListener;
import com.github.mikephil.charting.utils.ChosedPoint;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.view.MyMarkerView;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RadarActivity extends FragmentActivity implements
		TitleOnClickListener, OnClickListener {
	private TitleBar titleBar;
	private RadarChart mChart;
	private Typeface tf;
	private ArrayList<String> knowledge_point;
	private ArrayList<Float> userList, classList, gradeList, examList;
	private String studentName;
	private TextView tv1;
	private TextView radapersonla_tx;
	private TextView banji_tx;
	private TextView nianji_tx;
	private TextView kaoshi_tx;

	private DecimalFormat decimalFormat;
	private int currentindex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.radar_layout);
		setContentView(R.layout.activity_radar);
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(getResources().getString(R.string.knowledge_radar));
		titleBar.setLeftIcon(R.drawable.back_icon);
		titleBar.setTitleClickListener(this);

		tv1 = (TextView) findViewById(R.id.tv1);
		radapersonla_tx = (TextView) findViewById(R.id.radapersonla_tx);
		banji_tx = (TextView) findViewById(R.id.banji_tx);
		nianji_tx = (TextView) findViewById(R.id.nianji_tx);
		kaoshi_tx = (TextView) findViewById(R.id.kaoshi_tx);
		Button radalast_btn = (Button) findViewById(R.id.radalast_btn);
		Button radanext_btn = (Button) findViewById(R.id.radanext_btn);
		radalast_btn.setOnClickListener(this);
		radanext_btn.setOnClickListener(this);
		// tv2 = (TextView) findViewById(R.id.tv3);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		mChart = (RadarChart) findViewById(R.id.radarchart);
		mChart.setRadarChartOndrawListener(radarCharonDrawListener);

		RelativeLayout.LayoutParams L_WW_L = (RelativeLayout.LayoutParams) mChart.getLayoutParams();
		L_WW_L.height = metrics.widthPixels - 180;
		mChart.setLayoutParams(L_WW_L);

		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		mChart.setDescription("");
		mChart.setWebLineWidth(1.5f);
		mChart.setWebLineWidthInner(0.75f);
		mChart.setWebAlpha(100);

		initData();
		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		/*
		 * MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view,
		 * tv1, tv2, studentName, knowledge_point, userList, classList,
		 * gradeList, examList);
		 * 
		 * // set the marker to the chart mChart.setMarkerView(mv);
		 */
		setData();

		XAxis xAxis = mChart.getXAxis();
		xAxis.setTypeface(tf);
		xAxis.setTextSize(7f);

		YAxis yAxis = mChart.getYAxis();
		yAxis.setTypeface(tf);
		yAxis.setLabelCount(10, false);
		yAxis.setTextSize(7f);

		yAxis.setStartAtZero(true);
		yAxis.setAxisMaxValue(1.1f);
		yAxis.setAxisMinValue(0);
		yAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				DecimalFormat decimalFormat = new DecimalFormat("0.0");
				String s = decimalFormat.format(value);
				return s;
			}
		});

		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_CENTER);
		l.setTypeface(tf);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);
		l.setEnabled(false);

		decimalFormat = new DecimalFormat("###,###,###,##0" + ".00");
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.radar, menu); return true; }
	 */

	private void initData() {
		// TODO Auto-generated method stub
		knowledge_point = new ArrayList<String>();
		userList = new ArrayList<Float>();
		classList = new ArrayList<Float>();
		gradeList = new ArrayList<Float>();
		examList = new ArrayList<Float>();

		Bundle bundle = getIntent().getExtras();
		studentName = bundle.getString("studentName");
		knowledge_point = bundle.getStringArrayList("tagNameList");
		double[] scoreRates = bundle.getDoubleArray("scoreRates");
		double[] classRates = bundle.getDoubleArray("classRates");
		double[] gradeRates = bundle.getDoubleArray("gradeRates");
		double[] examRates = bundle.getDoubleArray("examRates");
		for (int i = 0; i < knowledge_point.size(); i++) {

			userList.add((float) scoreRates[i]);
			classList.add((float) classRates[i]);
			gradeList.add((float) gradeRates[i]);
			examList.add((float) examRates[i]);
		}
	}

	public void setData() {

		int cnt = knowledge_point.size();

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		ArrayList<Entry> yVals3 = new ArrayList<Entry>();
		ArrayList<Entry> yVals4 = new ArrayList<Entry>();

		// IMPORTANT: In a PieChart, no values (Entry) should have the same
		// xIndex (even if from different DataSets), since no values can be
		// drawn above each other.
		for (int i = 0; i < cnt; i++) {
			yVals1.add(new Entry(userList.get(i), i));
		}
		for (int i = 0; i < cnt; i++) {
			yVals2.add(new Entry(classList.get(i), i));
		}
		for (int i = 0; i < cnt; i++) {
			yVals3.add(new Entry(gradeList.get(i), i));
		}
		for (int i = 0; i < cnt; i++) {
			yVals4.add(new Entry(examList.get(i), i));
		}

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < cnt; i++) {
			String knowledge_name;
			if (knowledge_point.get(i).length() > 5) {
				knowledge_name = knowledge_point.get(i).trim().substring(0, 5)
						+ "...";
			} else {
				knowledge_name = knowledge_point.get(i);
			}
			xVals.add(knowledge_name);
		}
		RadarDataSet set1 = new RadarDataSet(yVals1, studentName);
		set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
		set1.setDrawFilled(true);
		set1.setLineWidth(2f);

		RadarDataSet set2 = new RadarDataSet(yVals2, "班级");
		set2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
		set2.setDrawFilled(true);
		set2.setLineWidth(2f);

		RadarDataSet set3 = new RadarDataSet(yVals3, "年级");
		set3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
		set3.setDrawFilled(true);
		set3.setLineWidth(2f);

		RadarDataSet set4 = new RadarDataSet(yVals4, "考试");
		set4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
		set4.setDrawFilled(true);
		set4.setLineWidth(2f);

		ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
		sets.add(set1);
		sets.add(set2);
		sets.add(set3);
		sets.add(set4);

		RadarData data = new RadarData(xVals, sets);
		data.setValueTypeface(tf);
		data.setValueTextSize(8f);
		data.setDrawValues(false);
		mChart.setData(data);
		mChart.invalidate();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		RadarActivity.this.finish();
		overridePendingTransition(R.anim.in_anim2, R.anim.out_anim);
	}
	
	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		RadarActivity.this.finish();
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

	/**
	 * 友盟统计
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	RadarChartOndrawListener radarCharonDrawListener = new RadarChartOndrawListener() {

		@Override
		public void onEnterOnDrawInRadarchart() {
			// TODO Auto-generated method stub
			currentindex = mChart.getIndexForAngle(270f);
			ChosedPoint chosepoint = new ChosedPoint();
			float textsize = Utils.convertDpToPixel(12f);
			chosepoint.setPotition(currentindex);
			chosepoint.setTextSize(textsize);
			chosepoint.setTextClour(Color.RED);
			mChart.getXAxisRendererRadarChart().setChosePoint(chosepoint);

			tv1.setText(knowledge_point.get(currentindex));
			radapersonla_tx.setText("个人:"
					+ decimalFormat.format(userList.get(currentindex) * 100)
					+ "%");
			if (userList.get(currentindex) < classList.get(currentindex)
					|| userList.get(currentindex) < examList.get(currentindex)
					|| userList.get(currentindex) < gradeList.get(currentindex)) {
				radapersonla_tx.setTextColor(getResources().getColor(
						R.color.redtextclour));
			} else {
				radapersonla_tx.setTextColor(getResources().getColor(
						R.color.classclour));
			}
			banji_tx.setText(" 班级:"
					+ decimalFormat.format(classList.get(currentindex) * 100)
					+ "%");
			kaoshi_tx.setText(" 考试:"
					+ decimalFormat.format(examList.get(currentindex) * 100)
					+ "%");
			nianji_tx.setText(" 年级:"
					+ decimalFormat.format(gradeList.get(currentindex) * 100)
					+ "%");

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.radalast_btn:
			mChart.spin(500, mChart.getRotationAngle(),
					mChart.getRotationAngle() - mChart.getSliceAngle(),
					Easing.EasingOption.EaseInCubic);
			break;
		case R.id.radanext_btn:
			mChart.spin(500, mChart.getRotationAngle(),
					mChart.getRotationAngle() + mChart.getSliceAngle(),
					Easing.EasingOption.EaseInCubic);
			break;

		default:
			break;
		}

	}

}
