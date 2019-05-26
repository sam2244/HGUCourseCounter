package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private int count;
	private String studentId;
	private ArrayList<Course> coursesTaken; // List of courses student has taken
	private HashMap<String,Integer> semestersByYearAndSemester; //key: Year-Semester
    // e.g., 2003-1
	
	public Student(String studentId) { // Constructor
		this.coursesTaken = new ArrayList<Course>();
		this.studentId = studentId;
		this.semestersByYearAndSemester = new HashMap<String, Integer>();
	   }
	
	public void addCourse(Course newRecord){
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String, Integer> getSemestersByYearAndSemester() {
	      for(String line: getyear + "-"+ getsemester) {
		         String data = new String(line);
		         Integer info = new Integer(data.getStudentId());
		         
		         if(semestersByYearAndSemester.containsKey(info.getStudentId())) {
		        	 semestersByYearAndSemester.get(info.getStudentId()).addCourse(data);
		         }else {
		            info.addCourse(data);
		            semestersByYearAndSemester.put(info.getStudentId(),info);
		         }
		      }
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		semester = 0;
		for()
		
		return semester;
	}

	public String getStudentId() {
		return studentId;
	}

	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}
}