package com.xyd.student.xydexamanalysis.utils;

import com.xyd.student.xydexamanalysis.R;

import android.R.integer;
import android.widget.ImageView;

public class ExamUtils {
	public static String getTime(String time) {
		String ret = time;
		if (ret.contains(" ")) {
			int index = ret.indexOf(" ");
			ret = time.substring(0, index);
		}
		return ret;
	}

	public static void setCourserImage(ImageView img, int courseid) {
		switch (courseid) {
		case -1:
		case 0:
			img.setImageResource(R.drawable.t_zong);
			break;
		case 201:
			img.setImageResource(R.drawable.t_yu);
			break;
		case 202:
			img.setImageResource(R.drawable.t_shu);
			break;
		case 203:
			img.setImageResource(R.drawable.t_ying);
			break;
		case 204:
			img.setImageResource(R.drawable.t_wu);
			break;
		case 205:
			img.setImageResource(R.drawable.t_hua);
			break;
		case 206:
			img.setImageResource(R.drawable.t_sheng);
			break;
		case 207:
			img.setImageResource(R.drawable.t_zheng);
			break;
		case 208:
			img.setImageResource(R.drawable.t_li);
			break;
		case 209:
			img.setImageResource(R.drawable.t_di);
			break;
		case 210:
			img.setImageResource(R.drawable.t_wen);
			break;
		case 211:
			img.setImageResource(R.drawable.t_lizong);
			break;
		case 212:
			img.setImageResource(R.drawable.t_yin);
			break;
		case 213:
			img.setImageResource(R.drawable.t_xin);
			break;
		case 214:
			img.setImageResource(R.drawable.t_mei);
			break;
		case 216:
			img.setImageResource(R.drawable.t_ti);
			break;
		case 221:
			img.setImageResource(R.drawable.t_si);
			break;
		case 222:
			img.setImageResource(R.drawable.t_kexue);
			break;
		case 302:
			img.setImageResource(R.drawable.laodongjishu);
			break;
		case 303:
			img.setImageResource(R.drawable.tongyongjishu);
			break;
		default:
			img.setImageResource(R.drawable.t_qt);
			break;
		}
	}

	public static String getCourseName(int courseid) {
		String name = "其他";
		switch (courseid) {
		case -1:
		case 0:
			name = "总";
			break;
		case 201:
			name = "语文";
			break;
		case 202:
			name = "数学";
			break;
		case 203:
			name = "英语";
			break;
		case 204:
			name = "物理";
			break;
		case 205:
			name = "化学";
			break;
		case 206:
			name = "生物";
			break;
		case 207:
			name = "政治";
			break;
		case 208:
			name = "历史";
			break;
		case 209:
			name = "地理";
			break;
		case 210:
			name = "文综";
			break;
		case 211:
			name = "理综";
			break;
		case 212:
			name = "音乐";
			break;
		case 222:
			name = "科学";
			break;
		case 213:
			name = "信息";
			break;
		case 214:
			name = "美术";
			break;
		case 216:
			name = "体育";
			break;
		case 221:
			name = "思想品德";
			break;
		case 302:
			name = "劳动技术";
			break;
		case 303:
			name = "通用技术";
			break;
		default:
			name = "其他";
			break;
		}
		return name;
	}
}
