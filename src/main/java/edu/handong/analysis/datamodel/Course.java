package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVRecord;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(CSVRecord line) {
		studentId=line.get(0); //.split(",")[0].trim();
		yearMonthGraduated=line.get(1); //.split(",")[1].trim();
		firstMajor=line.get(2); //.split(",")[2].trim();
		secondMajor=line.get(3); //.split(",")[3].trim();
		courseCode=line.get(4); //.split(",")[4].trim();
		courseName=line.get(5); //.split(",")[5].trim();
		courseCredit=line.get(6); //.split(",")[6].trim();
		yearTaken=Integer.parseInt(line.get(7)); //.split(",")[7].trim());
		semesterCourseTaken=Integer.parseInt(line.get(8)); //.split(",")[8].trim());
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setYearMonthGraduated(String yearMonthGraduated) {
		this.yearMonthGraduated = yearMonthGraduated;
	}

	public void setFirstMajor(String firstMajor) {
		this.firstMajor = firstMajor;
	}

	public void setSecondMajor(String secondMajor) {
		this.secondMajor = secondMajor;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseCredit(String courseCredit) {
		this.courseCredit = courseCredit;
	}

	public void setYearTaken(int yearTaken) {
		this.yearTaken = yearTaken;
	}

	public void setSemesterCourseTaken(int semesterCourseTaken) {
		this.semesterCourseTaken = semesterCourseTaken;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}

	public String getFirstMajor() {
		return firstMajor;
	}

	public String getSecondMajor() {
		return secondMajor;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseCredit() {
		return courseCredit;
	}

	public int getYearTaken() {
		return yearTaken;
	}

	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	
}
