package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HGUCoursePatternAnalyzer extends Exception{
	
	private static final HashMap<String, Student> NULL = null;

	String courseName;
	
	String input;
	String output;
	String analysis;
	String coursecode;
	String startyear;
	String endyear;
	boolean help;

	private HashMap<String,Student> students;
	
	Options options = createOptions();
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 * @throws IOException 
	 */
	public void run(String[] args) throws IOException {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				System.exit(0);
			}
		}
		
		Reader reader = Files.newBufferedReader(Paths.get(input));
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		
		String dataPath = input; // csv file to be analyzed
		String resultPath = output; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		//students = new HashMap<String,Student>();
		students = loadStudentCourseRecords(csvParser);
		
		//System.out.println(students.get("2").getStudentId());
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
		totalStudents = getTotalStudents(sortedStudents);
		
		HashMap<String, Integer> CourseTakenStudents = new HashMap<String, Integer>();
		CourseTakenStudents = getCourseTakenStudents(sortedStudents);
		
		//ArrayList<String> Rate = new ArrayList<String>();
		ArrayList<String> Rate = RateOfCourse(students);
		
		//System.out.println(sortedStudents.get("2").getStudentId());
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = new ArrayList<String>();
		
		if (analysis.equals("1")) {
			linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		}
		
		if (analysis.equals("2")) {
			linesToBeSaved = RateOfCourse(sortedStudents);
		}
		//System.out.println(coursecode + "," + courseName);
		
		/*if (analysis.equals("1")) {
			Utils.writeAFile(linesToBeSaved, resultPath);
		}
		
		if (analysis.equals("2")) {
			Utils.writeAFile(RateOfCourse, resultPath);
		}*/
		
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
	private HashMap<String,Student> loadStudentCourseRecords(CSVParser csvParser) {
	      HashMap<String, Student> HashMapOut = new HashMap<String, Student>();
	      
	      for(CSVRecord line:csvParser) {
	         Course data = new Course(line);
	         Student info = new Student(data.getStudentId());
	         
	         if(HashMapOut.containsKey(info.getStudentId())) {
	        	 HashMapOut.get(info.getStudentId()).addCourse(data,startyear,endyear);
	         }else {
	            info.addCourse(data,startyear,endyear);
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
	
	private HashMap<String, Integer> getCourseTakenStudents(Map<String, Student> students) {
		HashMap<String, Integer> HashMapOut = new HashMap<String, Integer>();
		
	    	/*for(Course data:students.coursesTaken){
	    	String key = (data.getYearTaken() + "-" + data.getSemesterCourseTaken());

		    if(semestersByYearAndSemester.containsKey(key)) {
		    }else {
		    	semestersByYearAndSemester.put(key, count++);
		    }
		}*/
	    /*
		for(CSVRecord line:csvParser) {
			Course data = new Course(line);
			String courseCode = 
			Integer num = new data.getStudentId();
			
			if(HashMapOut.containsKey(info.getStudentId())) {
				HashMapOut.get(info.getStudentId()).addCourse(data);
				}
			else {
				info.addCourse(data);
				HashMapOut.put(info.getStudentId(),info);
				}
			}*/
		
		return HashMapOut;
	}

	private HashMap<String, Integer> getTotalStudents(Map<String, Student> students) {
		HashMap<String, Integer> HashMapOut = new HashMap<String, Integer>();
		int TotalStudents = 0;
		/*
		while(students != NULL) {
			Course data = new Course(null);
			String key = (data.getYearTaken() + "-" + data.getSemesterCourseTaken());
			Integer count = 0;
			
			String[] splitYearSemester = (key.split("-"));
			int year = Integer.parseInt(splitYearSemester[0].trim());
			int semesterOfYear = Integer.parseInt(splitYearSemester[1].trim());
			
		    if(HashMapOut.containsKey(key) && year == Integer.parseInt(startyear) && year <= Integer.parseInt(endyear)) {
		    	TotalStudents++;
		    }else {
		    	HashMapOut.put(key, TotalStudents=0);
		    }
		}*/
		
		return HashMapOut;
	}
	
	private ArrayList<String> RateOfCourse(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		
		for(String key: sortedStudents.keySet()) {
			sortedStudents.get(key).getSemestersByYearAndSemester();
		}
		result.add("Year, Semester, CourseCode, CourseName, TotalStudents, StudentsTaken, Rate");
		
		String line = null;
		/*
		for(String key:sortedStudents.keySet()) {
			//System.out.println(sortedStudents.get(key).getStudentId());
			for(String list:sortedStudents.get(key).getSortedSemesterByYearAndSemester().keySet()) {
				String Year = sortedStudents.get(key).getStudentId();
				String Semester = String.valueOf(sortedStudents.get(key).getTotalNumberOfSemesterRegistered());
				String CourseCode = coursecode;
				String CourseName = courseName;
				int TotalStudents = getTotalStudents(students).get(key);
				int StudentsTaken = getCourseTakenStudents(students).get(key);
				int Rate = StudentsTaken/TotalStudents;
				
				line = Year + "," + Semester + "," + CourseCode + "," + CourseName + "," + TotalStudents + "," + StudentsTaken + "," + Rate;
				
				result.add(line);
			}
		}*/
		
		return result;
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			
			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i"); 
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			coursecode = cmd.getOptionValue("c"); 
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
			}
		
		return true;
		}
	
	private Options createOptions() {
		Options options = new Options();
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.build());
		
		return options;
		}
	   
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Pattern Analyzer";
		String footer ="\n Please report issues at https://github.com/sam2244/HGUCourseCounter";
		formatter.printHelp("HGU Course Counter", header, options, footer, true);
		}
}
