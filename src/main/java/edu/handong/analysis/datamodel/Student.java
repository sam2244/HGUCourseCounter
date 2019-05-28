package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Student {
	private Integer count=1; //counting for total semester
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
		/*Iterator studentIter = semestersByYearAndSemester.keySet().iterator();*/
		//int count = 0;
	    for(Course data:coursesTaken){
	    	String key = (data.getYearTaken() + "-" + data.getSemesterCourseTaken());

		    if(semestersByYearAndSemester.containsKey(key)) {
		    }else {
		    	semestersByYearAndSemester.put(key, count++);
		    }
		}
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		int count=0;
		
		for(String key:semestersByYearAndSemester.keySet()) {
			if(semester==semestersByYearAndSemester.get(key)) {
				String[] splitYearSemester = (key.split("-"));
				int year = Integer.parseInt(splitYearSemester[0].trim());
				int semesterOfYear = Integer.parseInt(splitYearSemester[1].trim());
				
				for(Course course:coursesTaken) {
					if(course.getYearTaken() == year && course.getSemesterCourseTaken() == semesterOfYear) {
						count++;
						}
					}
				}
			}
		return count;
	}
	
	public Map<String, Integer> getSortedSemesterByYearAndSemester(){
		Map<String, Integer> sortedSemesterByYearAndSemester = new TreeMap<String, Integer>(semestersByYearAndSemester); 
	return sortedSemesterByYearAndSemester;
	}
	
	public Integer getTotalNumberOfSemesterRegistered() {
		//String num = Integer.parseInt(count);
		return count-1;
	}

	public String getStudentId() {
		return studentId;
	}

	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}
}