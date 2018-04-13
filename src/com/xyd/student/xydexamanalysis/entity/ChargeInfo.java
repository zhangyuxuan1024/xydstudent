package com.xyd.student.xydexamanalysis.entity;

import java.io.Serializable;

public class ChargeInfo implements Serializable {
	private String chargeName;// 支付名称
	private int meId;// 多科标识
	private int seId;// 单科标识
	private double amount;// 金额
	private int payType;
	private String freeTime;

	public ChargeInfo(String chargeName, int meId, int seId, Double amount,
			int payType, String freeTime) {
		super();
		this.chargeName = chargeName;
		this.meId = meId;
		this.seId = seId;
		this.amount = amount;
		this.payType = payType;
		this.freeTime = freeTime;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public int getMeId() {
		return meId;
	}

	public void setMeId(int meId) {
		this.meId = meId;
	}

	public int getSeId() {
		return seId;
	}

	public void setSeId(int seId) {
		this.seId = seId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
