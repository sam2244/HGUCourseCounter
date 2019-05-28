package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer extends Exception{

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		//System.out.println(students.get("2").getStudentId());
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		//System.out.println(sortedStudents.get("2").getStudentId());
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		//System.out.println(linesToBeSaved.get(15));
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
	      HashMap<String, Student> HashMapOut = new HashMap<String, Student>();
	      
	      for(String line:lines) {
	         Course data = new Course(line);
	         Student info = new Student(data.getStudentId());
	         
	         if(HashMapOut.containsKey(info.getStudentId())) {
	        	 HashMapOut.get(info.getStudentId()).addCourse(data);
	         }else {
	            info.addCourse(data);
	            HashMapOut.put(info.getStudentId(),info);
	         }
	      }
	      
	      return HashMapOut;
	   }

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		
		for(String key: sortedStudents.keySet()) {
			sortedStudents.get(key).getSemestersByYearAndSemester();
		}
		result.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		String line = null;
		for(String key:sortedStudents.keySet()) {
			//System.out.println(sortedStudents.get(key).getStudentId());
			for(String list:sortedStudents.get(key).getSortedSemesterByYearAndSemester().keySet()) {
				String ID = sortedStudents.get(key).getStudentId();
				String TotalSem = String.valueOf(sortedStudents.get(key).getTotalNumberOfSemesterRegistered());
				int Sem = sortedStudents.get(key).getSemestersByYearAndSemester().get(list);
				int Num = sortedStudents.get(key).getNumCourseInNthSementer(Sem);
				
				line = ID + "," + TotalSem + "," + Sem + "," + Num;
				
				result.add(line);
			}
		}

		return result;
	}
}
