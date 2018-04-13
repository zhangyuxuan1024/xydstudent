package com.xyd.student.xydexamanalysis.entity;

public class PersonalInfo {
	private String name;
	private String schoolBadgeVisitePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolBadgeVisitePath() {
		return schoolBadgeVisitePath;
	}

	public void setSchoolBadgeVisitePath(String schoolBadgeVisitePath) {
		this.schoolBadgeVisitePath = schoolBadgeVisitePath;
	}

	@Override
	public String toString() {
		return "PersonalInfo [name=" + name + ", schoolBadgeVisitePath="
				+ schoolBadgeVisitePath + ", getName()=" + getName()
				+ ", getSchoolBadgeVisitePath()=" + getSchoolBadgeVisitePath()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
