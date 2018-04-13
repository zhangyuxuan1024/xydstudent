package com.xyd.student.xydexamanalysis.entity.weike;

import java.io.Serializable;
import java.util.List;

public class Weike implements Serializable {
	private List<List<WeiRecord>> list;

	public List<List<WeiRecord>> getList() {
		return list;
	}

	public void setList(List<List<WeiRecord>> list) {
		this.list = list;
	}

}
