package cis498;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ParseDU {

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException{
        String courseData, roomData, buildingData;
        courseData = "sunnaData/courses.txt";
        roomData = "sunnaData/rooms.txt";
        buildingData = "sunnaData/buildings.txt";
                
        ArrayList<Section> courseList = new ArrayList();
        ArrayList<Room> roomList = new ArrayList();
        ArrayList<Professor> professorList = new ArrayList();
        
        //using sets to easily create unique ArrayLists of these objects
        Set<String> weekendBuildings = new HashSet<>(); //contains only those buildings which are available on the weekend
        Set<String> campusClassrooms = new HashSet<>(); //contains only unique classroom names (building + number) on campus
        
        
        //BUILDING DATA - record the buildings which are open on the weekends
        BufferedReader input = new BufferedReader(new FileReader(new File(buildingData)));
        String line = input.readLine();
        line = input.readLine(); //the first line is headings
	
        while(line!=null){
            String cols[] = line.split("\\|");
            if(cols[1].equals("Y")){
                weekendBuildings.add(cols[1]);
            }
            line = input.readLine();
        }
        
        
        //ROOM DATA - create a unique list of all classrooms
        input = new BufferedReader(new FileReader(new File(roomData)));
        line = input.readLine();
        line = input.readLine(); //the first line is headings
	
        while(line!=null && !line.trim().equals("")){
            /*Data columns:
            Room	0
            Building	1
            Capacity	2
            maintenance_days	3
            Start_time	4
            Duration	5
            Computer_based_exams_enabled    5
            
            public Room(String buildingCodeIn, String roomNumberIn, boolean computerizedIn, int capacityIn, String maintenanceDayIn, 
                int maintenanceStartTimeIn, int maintenanceEndTimeIn, boolean weekendOk) {
            */ 
             
            String cols[] = line.split("\\|");
            
            //sets do not allow duplicates, so only create a new room object if the building+number name doesn't already exist in the set campusClassrooms
            if(campusClassrooms.add(cols[1]+cols[0])){
                roomList.add(new Room(cols[1], cols[0], cols[5].equals("Y"), parseInt(cols[2]), cols[3], parseInt(cols[4]+"00"), parseInt(cols[4]+"00")+parseInt(cols[5]+"00"), weekendBuildings.contains(cols[1]) ));
            }
	    line = input.readLine();
	}
        
        // sort room (roomList) by capacity (largest to smallest)
        Collections.sort(roomList); 
        
        System.out.println("\nRooms with capacity largest to smallest:");
        
        // print out descending capacity levels per override toString() in Room class
        for(Room str: roomList){
			System.out.println(str);
	   }
 
        
        //PROFESSOR DATA
        input = new BufferedReader(new FileReader(new File(courseData)));
        line = input.readLine();
        line = input.readLine(); //the first line is headings
        
        Set<String> profNames = new HashSet<>();
	
        while(line!=null && !line.trim().equals("")){
            /*Data columns:
                Course Number [0]
                Department [1]
                Call Number [2]
                Days [3]
                Time [4]
                Instructor [5]
                Room [6]
                Building [7]
                Number enrolled [8]
                Availability during finals week [9]
                Computer_based_exam [10] 
                -blank- [11] 
         
            public Professor(String fullNameIn, String departmentIn, String availabilityIn) {*/

             
            String cols[] = line.split("\\|");
            
            //sets do not allow duplicates, so only create a new professor object if the name doesn't already exist in the set profNames
                if(profNames.add(cols[5])){
                    professorList.add( new Professor(cols[5], cols[1], cols[9]) );
                }
	    line = input.readLine();
	}
        
        
        //COURSE SECTION DATA
        input = new BufferedReader(new FileReader(new File(courseData)));
        line = input.readLine();
        line = input.readLine(); //the first line is headings
	
        while(line!=null && !line.trim().equals("")){
            /*Data columns:
                Course Number [0]
                Department [1]
                Call Number [2]
                Days [3]
                Time [4]
                Instructor [5]
                Room [6]
                Building [7]
                Number enrolled [8]
                Availability during finals week [9]
                Computer_based_exam [10] 
                -blank- [11] 
            
                Section(int callNumber, Professor instructor, Room classroom, String department, int courseNumber, 
                        String offeredDays, int startTime, int endTime, int enrolled, boolean computerFinal) {
                 */
             
            String cols[] = line.split("\\|");
            Professor profToFind = null;
            for(Professor temp : professorList){
                if(temp.getFullName().equals(cols[5])){
                    profToFind = temp;
                }
            }
            
            Room roomToFind = null;
            for(Room temp : roomList){
                if(temp.getBuildingCode().equals(cols[7]) && temp.getRoomNumber().equals(cols[6])){
                    roomToFind = temp;
                }
            }
            
            if(roomToFind==null || profToFind==null){
                System.out.println("Error matching professor or classroom for this row of data:");
                System.out.println(line);
            }else{
                courseList.add(new Section(parseInt(cols[2]), 
                                           profToFind, 
                                           roomToFind, 
                                           cols[1], 
                                           parseInt(cols[0]), 
                                           cols[3], 
                                           parseInt(cols[4].substring(0,cols[4].length()-4)),
                                           parseInt(cols[4].substring(cols[4].length()-4)),
                                           parseInt(cols[8]),
                                           cols[10].equals("Y")
                                           ));
            }
	    line = input.readLine();
	}    
      
        
        // sort courses (courseList) by start time (ascending start times)
        Collections.sort(courseList); 
        
        // print out ascending start times per override toString() in Section class
//        for(Section str: courseList){
//			System.out.println(str);
//	   	}
        
        System.out.println("\nAll courses with assigned buckets:");
        
        // assign each section to a bucket based on start time and days offered (mon or tue)
        for(Section str: courseList){
        	if(str.getStartTime()<= 800)
        		str.setBucket("A");
        	if(str.getStartTime()>= 830 && str.getStartTime()<= 930 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("B");
        	if(str.getStartTime()>= 945 && str.getStartTime()<= 1045 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("C");
        	if(str.getStartTime()>= 1100 && str.getStartTime()<= 1145 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("D");
        	if(str.getStartTime()>= 1150 && str.getStartTime()<= 1200 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("E");
        	if(str.getStartTime()>= 1230 && str.getStartTime()<= 1300 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("F");
        	if(str.getStartTime()>= 1330 && str.getStartTime()<= 1400 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("G");
        	if(str.getStartTime()>= 1500 && str.getStartTime()<= 1530 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("H");
        	if(str.getStartTime()>= 1600 && str.getStartTime()<= 1645 && ((str.getOfferedDays().equals("10100") || (str.getOfferedDays().equals("10101")))))
        		str.setBucket("I");
        	if(str.getStartTime()== 800 && str.getOfferedDays().equals("01010"))
        		str.setBucket("J");
        	if(str.getStartTime()>= 900 && str.getStartTime()<= 945 && str.getOfferedDays().equals("01010"))
        		str.setBucket("K");
        	if(str.getStartTime()>= 1000 && str.getStartTime()<= 1030 && str.getOfferedDays().equals("01010"))
        		str.setBucket("L");
        	if(str.getStartTime()== 1100 && str.getOfferedDays().equals("01010"))
        		str.setBucket("M");
        	if(str.getStartTime()== 1200 && str.getOfferedDays().equals("01010"))
        		str.setBucket("N");
        	if(str.getStartTime()>= 1230 && str.getStartTime()<= 1300 && str.getOfferedDays().equals("01010"))
        		str.setBucket("O");
        	if(str.getStartTime()>= 1315 && str.getStartTime()<= 1430 && str.getOfferedDays().equals("01010"))
        		str.setBucket("P");
        	if(str.getStartTime()>= 1500 && str.getStartTime()<= 1600 && str.getOfferedDays().equals("01010"))
        		str.setBucket("Q");
        	if(str.getStartTime()>= 1630 && str.getOfferedDays().equals("01010"))
        		str.setBucket("R");           	
        	
        	System.out.println(str);
        }
        
        System.out.println("\nBucket A courses:");
        
        // get list of courses in specific bucket
        for(Section str: courseList){
        	if(str.getBucket().equalsIgnoreCase("A"))
        		System.out.println(str);
        }
        
//        
//      // SORTING course/section by start times
//      int[] order = new int[courseList.size()]; // to track order of sorting     
//      int[] startOrder = new int[courseList.size()]; // to hold sorted order of startTime values  
//      
//        for(int i=0; i<courseList.size();i++){
//        	startOrder[i] = courseList.get(i).getStartTime();
//        	//System.out.println(courseList.get(i).getStartTime());
//        }
//        
//        for (int i = 0; i < startOrder.length - 1; i++)
//        {
//            int index = i;
//            for (int j = i + 1; j < startOrder.length; j++)
//                if (startOrder[j] < startOrder[index]) 
//                    index = j;
//      
//            int smallerNumber = startOrder[index];  
//            startOrder[index] = startOrder[i];
//            startOrder[i] = smallerNumber;
//            order[i] = index;
//        }
//        
////        MySelectionSort.doSelectionSort(startOrder);
//        for(int i:order){
//            System.out.println(i);
//        }
//        
//            System.out.println(startOrder.length+ " : "+order.length);
//        
        
//    System.out.println("Courses inputted: " + courseList.size());
//    System.out.println("Professors inputted: " + professorList.size());
//    System.out.println("Rooms inputted: " + roomList.size());
    
    if(false){ //CHANGE TO TRUE FOR DEMONSTRATING TO PROF. SUNNA
        System.out.println();
        System.out.println("Section call number 13467 will have its exam scheduled in the first timeslot on Tuesday in THUY-304.");
        
        Section toSchedule = null;
        Room examRoom = null;
        for(Section temp : courseList){
            //System.out.println(temp.getCallNumber() + " ");
            if(temp.getCallNumber()==13467){
                toSchedule = temp;
                break;
            }
        }
        
        for(Room temp : roomList){
            //System.out.println(">" + temp.getBuildingCode() + "<>" + temp.getRoomNumber() + "< ");
            if(temp.getBuildingCode().equals("THUY") && temp.getRoomNumber().equals("304")){
                examRoom = temp;
                break;
            }
        }
        
        if(examRoom!=null && toSchedule!=null){
            examRoom.scheduleExam(toSchedule, 0, 1);
            System.out.println(">>Verifying the professor of this course to prove scheduling complete: " + examRoom.getScheduledSection(0, 1).getInstructor().getFullName());
        }else{
            System.out.println(">>Error locating classroom or section.");
        }
        }
    }
}
