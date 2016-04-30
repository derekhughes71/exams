package cis498;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ParseDU {

    @SuppressWarnings({ "unchecked"})
	public static void main(String[] args) throws FileNotFoundException, IOException{
        String courseData, roomData, buildingData;
        courseData = "sunnaData/courses.txt";
        roomData = "sunnaData/rooms.txt";
        buildingData = "sunnaData/buildings.txt";
                
        ArrayList<Section> courseList = new ArrayList<Section>();
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<Professor> professorList = new ArrayList<Professor>();
        
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
        
        //System.out.println("\nRooms with capacity largest to smallest:");
        
        // print out descending capacity levels per override toString() in Room class
        for(Room str: roomList){
			//System.out.println(str);
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
        
//        for(Professor str: professorList){
//			System.out.println(str.getFullName().toString());
//	   }
//        System.out.println(professorList.get(0).getFullName().toString());
        
        
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
        	if(str.getStartTime()>= 1100 && str.getStartTime()<= 1130  && str.getOfferedDays().equals("01010"))
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
        	
        	//System.out.println(str);
        }
        
        
        // ARRAYLIST of Arraylists of courses in each bucket
        // create arraylist of courses for each bucket, store these bucket arraylists into
        // a master arraylist
        ArrayList<ArrayList> bucketList= new ArrayList<ArrayList>(); 
        ArrayList<Section> bucketA =  new ArrayList<Section>();
        ArrayList<Section> bucketB =  new ArrayList<Section>();
        ArrayList<Section> bucketC =  new ArrayList<Section>();
        ArrayList<Section> bucketD =  new ArrayList<Section>();
        ArrayList<Section> bucketE =  new ArrayList<Section>();
        ArrayList<Section> bucketF =  new ArrayList<Section>();
        ArrayList<Section> bucketG =  new ArrayList<Section>();
        ArrayList<Section> bucketH =  new ArrayList<Section>();
        ArrayList<Section> bucketI =  new ArrayList<Section>();
        ArrayList<Section> bucketJ =  new ArrayList<Section>();
        ArrayList<Section> bucketK =  new ArrayList<Section>();
        ArrayList<Section> bucketL =  new ArrayList<Section>();
        ArrayList<Section> bucketM =  new ArrayList<Section>();
        ArrayList<Section> bucketN =  new ArrayList<Section>();
        ArrayList<Section> bucketO =  new ArrayList<Section>();
        ArrayList<Section> bucketP =  new ArrayList<Section>();
        ArrayList<Section> bucketQ =  new ArrayList<Section>();
        ArrayList<Section> bucketR =  new ArrayList<Section>();        
        
        System.out.println("\nBucket ArrayList of courses in each bucket: \n");
        
        // get list of courses in specific bucket
        for(Section str: courseList){
        	if(str.getBucket().equalsIgnoreCase("A"))
        		bucketA.add(str); // add to A arraylist
        	if(str.getBucket().equalsIgnoreCase("B"))
        		bucketB.add(str); // add to B arraylist
        	if(str.getBucket().equalsIgnoreCase("C"))
        		bucketC.add(str); // add to C arraylist
        	if(str.getBucket().equalsIgnoreCase("D"))
        		bucketD.add(str); // add to D arraylist
        	if(str.getBucket().equalsIgnoreCase("E"))
        		bucketE.add(str); // add to E arraylist
        	if(str.getBucket().equalsIgnoreCase("F"))
        		bucketF.add(str); // add to F arraylist
        	if(str.getBucket().equalsIgnoreCase("G"))
        		bucketG.add(str); // add to G arraylist
        	if(str.getBucket().equalsIgnoreCase("H"))
        		bucketH.add(str); // add to H arraylist
        	if(str.getBucket().equalsIgnoreCase("I"))
        		bucketI.add(str); // add to I arraylist
        	if(str.getBucket().equalsIgnoreCase("J"))
        		bucketJ.add(str); // add to J arraylist
        	if(str.getBucket().equalsIgnoreCase("K"))
        		bucketK.add(str); // add to K arraylist
        	if(str.getBucket().equalsIgnoreCase("L"))
        		bucketL.add(str); // add to L arraylist
        	if(str.getBucket().equalsIgnoreCase("M"))
        		bucketM.add(str); // add to M arraylist
        	if(str.getBucket().equalsIgnoreCase("N"))
        		bucketN.add(str); // add to N arraylist
        	if(str.getBucket().equalsIgnoreCase("O"))
        		bucketO.add(str); // add to O arraylist
        	if(str.getBucket().equalsIgnoreCase("P"))
        		bucketP.add(str); // add to P arraylist
        	if(str.getBucket().equalsIgnoreCase("Q"))
        		bucketQ.add(str); // add to Q arraylist
        	if(str.getBucket().equalsIgnoreCase("R"))
        		bucketR.add(str); // add to R arraylist
        }
        
        // Add courses in each bucket to bucketList ArrayList
        bucketList.add(bucketA);
        bucketList.add(bucketB);
        bucketList.add(bucketC);
        bucketList.add(bucketD);
        bucketList.add(bucketE);
        bucketList.add(bucketF);
        bucketList.add(bucketG);
        bucketList.add(bucketH);
        bucketList.add(bucketI);
        bucketList.add(bucketJ);
        bucketList.add(bucketK);
        bucketList.add(bucketL);
        bucketList.add(bucketM);
        bucketList.add(bucketN);
        bucketList.add(bucketO);
        bucketList.add(bucketP);
        bucketList.add(bucketQ);
        bucketList.add(bucketR);
        
        
//        // Example code to remove specific Section from the bucketList or from
//        // a particular bucket
//        for(int i=0; i<bucketList.size(); i++){ // bucket element of bucketList
//        	ArrayList bucket = bucketList.get(i);
//	        	for(int j=0; j<bucket.size(); j++){ // section of each bucket of bucketList
//	        		Section course = (Section) bucket.get(j);
//		        		if(course.getCallNumber()==12807) // find call number in se
//		        			bucket.remove(j);   // if found, remove that section from that bucket    			
//	        	}
//        } // end for - remove specific Section
       
      
        
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
