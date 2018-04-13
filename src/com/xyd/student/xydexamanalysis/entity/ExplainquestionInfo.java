package com.xyd.student.xydexamanalysis.entity;

public class ExplainquestionInfo {

	private String subject;
	private String title;
	private String title_date;
	private int qustionid;
	private String question_type;
	private int max_score;
	private int aquire_score;
	private int difficulty_leave;
	private String vedio_url;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_date() {
		return title_date;
	}

	public void setTitle_date(String title_date) {
		this.title_date = title_date;
	}

	public int getQustionid() {
		return qustionid;
	}

	public void setQustionid(int qustionid) {
		this.qustionid = qustionid;
	}

	public String getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}

	public int getMax_score() {
		return max_score;
	}

	public void setMax_score(int max_score) {
		this.max_score = max_score;
	}

	public int getAquire_score() {
		return aquire_score;
	}

	public void setAquire_score(int aquire_score) {
		this.aquire_score = aquire_score;
	}

	public int getDifficulty_leave() {
		return difficulty_leave;
	}

	public void setDifficulty_leave(int difficulty_leave) {
		this.difficulty_leave = difficulty_leave;
	}

	public String getVedio_url() {
		return vedio_url;
	}

	public void setVedio_url(String vedio_url) {
		this.vedio_url = vedio_url;
	}

}
