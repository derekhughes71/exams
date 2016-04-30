package cis498;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class parseDU {

    @SuppressWarnings({ "unchecked", "null" })
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
        for(Room classroom: roomList){
            System.out.println(classroom);
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
        line = input.readLine(); //the first line is headings, so start with line 2
	
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

        System.out.println("\nAll courses with assigned buckets:");
        
        // assign each section to a bucket based on start time and days offered (mon or tue)
        for(Section course: courseList){
        	if(course.getStartTime()<= 800 && (course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101")))){
        		course.setBucket("A");
                        bucketA.add(course);
                }else if(course.getStartTime()>= 830 && course.getStartTime()<= 930 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("B");
                        bucketB.add(course);
                }else if(course.getStartTime()>= 945 && course.getStartTime()<= 1045 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("C");
                        bucketC.add(course);
                }else if(course.getStartTime()>= 1100 && course.getStartTime()<= 1145 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("D");
                        bucketD.add(course);
        	}else if(course.getStartTime()>= 1150 && course.getStartTime()<= 1200 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("E");
                        bucketE.add(course);
        	}else if(course.getStartTime()>= 1230 && course.getStartTime()<= 1300 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("F");
                        bucketF.add(course);
        	}else if(course.getStartTime()>= 1330 && course.getStartTime()<= 1400 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("G");
                        bucketG.add(course);
        	}else if(course.getStartTime()>= 1500 && course.getStartTime()<= 1530 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("H");
                        bucketH.add(course);
        	}else if(course.getStartTime()>= 1600 && course.getStartTime()<= 1645 && ((course.getOfferedDays().equals("10100") || (course.getOfferedDays().equals("10101"))))){
        		course.setBucket("I");
                        bucketI.add(course);
        	}else if(course.getStartTime()<= 800 && course.getOfferedDays().equals("01010")){
        		course.setBucket("J");
                        bucketJ.add(course);
        	}else if(course.getStartTime()>= 900 && course.getStartTime()<= 945 && course.getOfferedDays().equals("01010")){
        		course.setBucket("K");
                        bucketK.add(course);
        	}else if(course.getStartTime()>= 1000 && course.getStartTime()<= 1030 && course.getOfferedDays().equals("01010")){
        		course.setBucket("L");
                        bucketL.add(course);
        	}else if(course.getStartTime()>= 1100 && course.getStartTime()<= 1130  && course.getOfferedDays().equals("01010")){
        		course.setBucket("M");
                        bucketM.add(course);
        	}else if(course.getStartTime()== 1200 && course.getOfferedDays().equals("01010")){
        		course.setBucket("N");
                        bucketN.add(course);
        	}else if(course.getStartTime()>= 1230 && course.getStartTime()<= 1300 && course.getOfferedDays().equals("01010")){
        		course.setBucket("O");
                        bucketO.add(course);
        	}else if(course.getStartTime()>= 1315 && course.getStartTime()<= 1430 && course.getOfferedDays().equals("01010")){
        		course.setBucket("P");
                        bucketP.add(course);
        	}else if(course.getStartTime()>= 1500 && course.getStartTime()<= 1600 && course.getOfferedDays().equals("01010")){
        		course.setBucket("Q");
                        bucketQ.add(course);
        	}else if(course.getStartTime()>= 1630 && course.getOfferedDays().equals("01010")){
        		course.setBucket("R");    
                        bucketR.add(course);                        
                }else{
                    System.out.println("Couldn't bucket course: " + course);
                }
        	System.out.println("Bucketed successfully: " + course);
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
        
        // print out courses in each bucket and the bucket size for verification/accuracy
        for(ArrayList bucket: bucketList){
            System.out.println(bucket.toString()); 
            System.out.println("Bucket size: "+bucket.size()+"\n");
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
    

    int examDay = 0;
    int examSlotNumber = 0;
    
    for(ArrayList<Section> currentBucket : bucketList){ 
        //loop through all buckets
        Iterator<Section> it = currentBucket.iterator(); 
        Section currentSection;
        while(it.hasNext()) {
                currentSection = it.next();
                if(currentSection.getClassroom().roomFree(examDay, examSlotNumber)){
                    //the regular classroom is free!
                    if(currentSection.isComputerFinal()){
                        //if the section needs a computerized final
                        if(currentSection.getClassroom().isComputerized() && 
                                currentSection.getClassroom().getCapacity() >= currentSection.getEnrolled()){
                            //classroom is computerized, and it's big enough!
                            // now for a professor
                            
                                            //check normal professor, then find others: - - - - - - - - - - - - - - - - - - - - - - (same as section below) 
                                            if(currentSection.getInstructor().professorFree(examDay, examSlotNumber)){
                                                //professor is free, so schedule!
                                                currentSection.getInstructor().scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);
                                                currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                                                currentSection.setExamProctor(currentSection.getInstructor());
                                                it.remove();
                                                continue;
                                            }else{
                                                //normal professor is not free
                                                for(Professor currentProf : professorList){
                                                    //loop through all professors
                                                    if(currentProf.getDepartment().equals(currentSection.getDepartment())){
                                                        //only check if the department is correct
                                                        if(currentProf.professorFree(examDay, examSlotNumber)){
                                                            //current professor is free for the slot, so schedule!
                                                            currentProf.scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);
                                                            currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                                                            currentSection.setExamProctor(currentProf);
                                                            it.remove();
                                                            break;
                                                        }
                                                    }
                                                }   
                                            }
                                            // ^^^^^ end professor checking/searching  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

                        }else{
                            //not a computerized room, and we need one
                            continue;
                        }
                    }else{
                        // the section does not need a computerized final
                        if(currentSection.getClassroom().getCapacity() >= 2*currentSection.getEnrolled()){
                            //the classroom is big enough, so find a professor
                            
                            
                                            //check normal professor, then find others: - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
                                            if(currentSection.getInstructor().professorFree(examDay, examSlotNumber)){
                                                //professor is free, so schedule!
                                                currentSection.getInstructor().scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);
                                                currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                                                currentSection.setExamProctor(currentSection.getInstructor());
                                                it.remove();
                                            }else{
                                                //normal professor is not free
                                                for(Professor currentProf : professorList){
                                                    //loop through all professors
                                                    if(currentProf.getDepartment().equals(currentSection.getDepartment())){
                                                        //only check if the department is correct
                                                        if(currentProf.professorFree(examDay, examSlotNumber)){
                                                            //current professor is free for the slot, so schedule!
                                                            currentProf.scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);
                                                            currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                                                            currentSection.setExamProctor(currentProf);
                                                            it.remove();
                                                            break;
                                                        }
                                                    }
                                                }   
                                            }
                                            // ^^^^^end professor checking/searching  - - - - - - - - - - - - - - - - - - - - - - - - - - - (same as above)

                        }else{ //classroom is not big enough
                            continue;
                        }
                    }
                }else{
                    //the regular classroom is not free
                    continue;
                }
            }
        
        //next bucket is assigned to the next timeslot in the week's schedule
        examSlotNumber++;
        if(examSlotNumber>=5){
            examDay++;
            examSlotNumber=0;
        }
    }
    


//    if(false){ //CHANGE TO TRUE FOR DEMONSTRATING TO PROF. SUNNA
//        System.out.println();
//        System.out.println("Section call number 13467 will have its exam scheduled in the first timeslot on Tuesday in THUY-304.");
//        
//        Section toSchedule = null;
//        Room examRoom = null;
//        for(Section temp : courseList){
//            //System.out.println(temp.getCallNumber() + " ");
//            if(temp.getCallNumber()==13467){
//                toSchedule = temp;
//                break;
//            }
//        }
//        
//        for(Room temp : roomList){
//            //System.out.println(">" + temp.getBuildingCode() + "<>" + temp.getRoomNumber() + "< ");
//            if(temp.getBuildingCode().equals("THUY") && temp.getRoomNumber().equals("304")){
//                examRoom = temp;
//                break;
//            }
//        }
//        
//        if(examRoom!=null && toSchedule!=null){
//            examRoom.scheduleExam(toSchedule, 0, 1);
//            System.out.println(">>Verifying the professor of this course to prove scheduling complete: " + examRoom.getScheduledSection(0, 1).getInstructor().getFullName());
//        }else{
//            System.out.println(">>Error locating classroom or section.");
//        }
//        }
//    }

//System.out.println("The following courses are still unscheduled:");
//int count = 0;
//        for(ArrayList<Section> currentBucket : bucketList){ 
//            //loop through all buckets
//            Iterator<Section> it = currentBucket.iterator(); 
//            Section currentSection;
//            while(it.hasNext()) {
//                currentSection = it.next();
//                System.out.println(currentSection);
//                count++;
//            }
//            System.out.println("");
//        }
//        System.out.println("Total unscheduled: " + count);

for(Room currentRoom : roomList){ 
    System.out.println(currentRoom.printSchedule());
}

    }
}