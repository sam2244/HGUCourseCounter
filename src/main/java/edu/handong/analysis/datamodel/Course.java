package edu.handong.analysis.datamodel;

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
	
	public Course(String line) {
		studentId=line.split(",")[0];
		yearMonthGraduated=line.split(",")[1];
		firstMajor=line.split(",")[2];
		secondMajor=line.split(",")[3];
		courseCode=line.split(",")[4];
		courseName=line.split(",")[5];
		courseCredit=line.split(",")[6];
		yearTaken=Integer.parseInt(line.split(",")[7]);
		semesterCourseTaken=Integer.parseInt(line.split(",")[8]);
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
