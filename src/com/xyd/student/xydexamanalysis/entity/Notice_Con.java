package com.xyd.student.xydexamanalysis.entity;

import java.util.ArrayList;

public class Notice_Con {
	private int total;
	private int unread;
	private ArrayList<SingleNotice> noticelist;

	public Notice_Con() {
		super();
	}

	public Notice_Con(int total, int unread, ArrayList<SingleNotice> noticelist) {
		super();
		this.total = total;
		this.unread = unread;
		this.noticelist = noticelist;

	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	public ArrayList<SingleNotice> getNoticelist() {
		return noticelist;
	}

	public void setNoticelist(ArrayList<SingleNotice> noticelist) {
		this.noticelist = noticelist;
	}

}
