package com.xyd.student.xydexamanalysis.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.DrawHelper;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.xclcharts.renderer.XEnum;

import com.xyd.student.xydexamanalysis.R;

public class MyBarView extends DemoView {
	private BarChart chart = new BarChart();
	// 轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private ArrayList<Integer> offsetList;
	private ArrayList<Double> offset_double;
	private int avg;
	private ArrayList<String> kemuList;
	private double value, value_max, value_min;
	private int type;
	private int step;

	public MyBarView(Context context, ArrayList<String> kemuList,
			ArrayList<Double> offset_double, ArrayList<Integer> offsetList,
			int avg, int type) {
		super(context);
		// TODO Auto-generated constructor stub
		this.kemuList = kemuList;
		this.offset_double = offset_double;
		this.offsetList = offsetList;
		this.avg = avg;
		this.type = type;
		initView();
	}

	public MyBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		chartLabels();// 横轴显示
		chartDataSet(); // 柱状图
		chartRender();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	private void chartRender() {
		try {
			DisplayMetrics dm = getResources().getDisplayMetrics();
			int scrWidth = (int) (dm.widthPixels);
			float rate = (float) scrWidth / 320;
			chart.disableScale();// 禁止缩放
			// 拓展宽度
			// chart.getPlotArea().extWidth(100);
			// 设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
			int[] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			// 隐藏边框
			chart.hideBorder();
			// chart.getDataAxis().hideAxisLabels();
			// 隐藏数据轴刻度
			chart.getDataAxis().hideTickMarks();
			// 数据源
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);
			float margin = DensityUtil.dip2px(getContext(), 30);
			chart.setXTickMarksOffsetMargin(margin);
			chart.setYTickMarksOffsetMargin(margin);
			// 数据轴
			if (max >= 0) {
				if (max > 40) {
					value_max = (((max - max % 10) / 40) + 1) * 40;
				} else {
					value_max = 40;
				}
			} else {
				value_max = 40;
			}
			if (min < 0) {
				if (min < -40) {
					value_min = (((Math.abs(min) - Math.abs(min) % 10) / 40) + 1) * 40;// 绝对值
				} else {
					value_min = 40;
				}
			} else {
				value_min = 40;
			}
			chart.getDataAxis().setAxisMax(value_max);
			chart.getDataAxis().setAxisMin(-value_min);

			// 指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(10);
			chart.getDataAxis().enabledAxisStd();
			chart.getDataAxis().setAxisStd(0);// 设置标准值
			chart.getCategoryAxis().setAxisBuildStd(false);
			// 背景网格
			chart.getPlotGrid().showHorizontalLines();
			// 定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub
					Double tmp = Double.parseDouble(value);
					DecimalFormat df = new DecimalFormat("#0");
					String label = df.format(tmp).toString();
					return (label);
				}
			});
			// 标签旋转45度
			// chart.getCategoryAxis().setTickLabelRotateAngle(45f);
			chart.getCategoryAxis().getTickLabelPaint()
					.setTextSize(((int) (11 * rate)));
			chart.getDataAxis().getTickLabelPaint()
					.setTextSize(((int) (10 * rate)));
			// 在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			chart.getBar().getItemLabelPaint().setTextSize(((int) (7 * rate)));
			// 设定格式
			if (type == 1) {
				chart.getDataAxis().setAxisSteps(2);
				chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
					public String doubleFormatter(Double value) {
						// TODO Auto-generated method stub
						DecimalFormat df = new DecimalFormat("#0");
						String label = df.format(value).toString();
						return label;
					}
				});
			} else {
				chart.getDataAxis().setAxisSteps(4);
				chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
					public String doubleFormatter(Double value) {
						// TODO Auto-generated method stub
						DecimalFormat df = new DecimalFormat("#0.00");
						String label = df.format(value).toString();
						return label;
					}
				});
			}

			// 隐藏Key
			chart.getPlotLegend().hide();

			// 让柱子间没空白
			chart.getBar().setBarInnerMargin(0.5f); // 可尝试0.1或0.5各有啥效果噢

			// 禁用平移模式
			chart.disablePanMode();

			chart.disableHighPrecision();

			// 限制只能左右滑动
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
			chart.setBarCenterStyle(XEnum.BarCenterStyle.SPACE);

			// chart.showRoundBorder();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void chartDataSet() {
		max = 0;
		min = 0;
		// 柱形图值
		// 标签对应的柱形数据集
		List<Double> dataSeriesA = new LinkedList<Double>();
		// 依数据值确定对应的柱形颜色.
		List<Integer> dataColorA = new LinkedList<Integer>();
		if (type == 1) {
			for (int i = 0; i < offsetList.size(); i++) {
				double a = offsetList.get(i);
				dataSeriesA.add(a);
				if (a >= 0) {
					dataColorA.add(Color.rgb(255, 165, 52));
				} else {
					dataColorA.add(Color.rgb(106, 193, 76));
				}
				if (a > max) {
					max = a;
				}
				if (a < min) {
					min = a;
				}
			}
		} else {
			for (int i = 0; i < offset_double.size(); i++) {

				double a = offset_double.get(i);
				dataSeriesA.add(a);
				if (a >= 0) {
					dataColorA.add(Color.rgb(255, 165, 52));
				} else {
					dataColorA.add(Color.rgb(106, 193, 76));
				}
				if (a > max) {
					max = a;
				}
				if (a < min) {
					min = a;
				}
			}

		}

		// if (type==1) {
		// // if (Math.abs(min)>max) {
		// // value=Math.abs(min)-Math.abs(min)%5+15;
		// // }else {
		// // double rate=avg;
		// // double c=max-rate;
		// // double d=rate
		// // int a=(int) (c%4);
		// // value_max=4*(a+1);
		// //
		// // }
		//
		// }else {
		// if (Math.abs(min)>max) {
		// value=(((Math.abs(min)-Math.abs(min)%10)/40)+1)*40;
		// }else {
		// value=(((max-max%10)/40)+1)*40;
		// }
		// }

		// 此地的颜色为Key值颜色及柱形的默认颜色
		BarData BarDataA = new BarData("", dataSeriesA, dataColorA, Color.rgb(
				53, 169, 239));

		chartData.add(BarDataA);
		dataSeriesA = null;
	}

	private void chartLabels() {

		chartLabels = kemuList;
	}

	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private double max;
	private double min;

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
		}
	}

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);
		return lst;
	}

}
