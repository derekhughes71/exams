package cis498;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static java.lang.Integer.parseInt;

public class ParseDU {

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
            Computer_based_exams_enabled    6
            
            public Room(String buildingCodeIn, String roomNumberIn, boolean computerizedIn, int capacityIn, String maintenanceDayIn, 
                int maintenanceStartTimeIn, int maintenanceEndTimeIn, boolean weekendOk) {
            */ 
             
            String cols[] = line.split("\\|");
            
            //sets do not allow duplicates, so only create a new room object if the building+number name doesn't already exist in the set campusClassrooms
            if(campusClassrooms.add(cols[1]+cols[0])){
                roomList.add(new Room(cols[1], cols[0], cols[6].equals("Y"), parseInt(cols[2]), cols[3], parseInt(cols[4]+"00"), parseInt(cols[4]+"00")+parseInt(cols[5]+"00"), weekendBuildings.contains(cols[1]) ));
            }
	    line = input.readLine();
	}
        
        // sort room (roomList) by capacity (largest to smallest)
        Collections.sort(roomList); 
        
        //System.out.println("\nRooms with capacity largest to smallest:");
        
        // print out descending capacity levels per override toString() in Room class
        for(Room classroom: roomList){
            //System.out.println(classroom);
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
        
        System.out.println("Courses inputted: " + courseList.size());
        System.out.println("Professors inputted: " + professorList.size());
        System.out.println("Rooms inputted: " + roomList.size() + "\n");
        
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

        System.out.println("Bucket sizes:");
        
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
        	//System.out.println("Bucketed successfully: " + course);
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
            //System.out.println(bucket.toString()); 
            System.out.println("Bucket size: "+bucket.size());
        }

       
//  ___  _   ___ ___   _ 
// | _ \/_\ / __/ __| / |
// |  _/ _ \\__ \__ \ | |
// |_|/_/ \_\___/___/ |_|
//
// look at normal classroom, and if so, try the normal professor 

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
                        //classroom is computerized as we need, and it's big enough, so schedule the section for now
                        currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                        currentSection.setExamRoom(currentSection.getClassroom());
                        
                        //check normal professor, just for giggles
                        if(currentSection.getInstructor().professorFree(examDay, examSlotNumber)){
                            //professor is free, so just do the rest of scheduling
                            currentSection.getInstructor().scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);        
                            currentSection.setExamProctor(currentSection.getInstructor());
                            //remove the section from bucket and future passes
                            it.remove();
                        }else{
                            //normal professor is not free, so we'll leave the section/room scheduled and move on
                        }                   
                    }else{
                        //normal room is unsuitable - not free, or not big enough
                    }
                }else{ 
                    //section does not need computerized room
                    if(currentSection.getClassroom().getCapacity() >= 2*currentSection.getEnrolled()){
                        //the normal classroom is big enough, so schedule the for now
                        currentSection.getClassroom().scheduleExam(currentSection, examDay, examSlotNumber);
                        currentSection.setExamRoom(currentSection.getClassroom());
                            
                        //check normal professor, just for giggles
                        if(currentSection.getInstructor().professorFree(examDay, examSlotNumber)){
                            //professor is free, so just do the rest of scheduling
                            currentSection.getInstructor().scheduleRoom(currentSection.getClassroom(), examDay, examSlotNumber);        
                            currentSection.setExamProctor(currentSection.getInstructor());
                            //remove the section from bucket and future passes
                            it.remove();
                        }else{
                            //normal professor is not free, so we'll leave the section/room scheduled and move on
                        }
                    }else{
                        // normal classroom is not suitable
                    }
                }
            }
        }
                
//  ___  _   ___ ___   ___ 
// | _ \/_\ / __/ __| |_  )
// |  _/ _ \\__ \__ \  / / 
// |_|/_/ \_\___/___/ /___|
//   
// look at normal professor for availability
        
        it = currentBucket.iterator(); 
        while(it.hasNext()) {
            currentSection = it.next(); 
            if(currentSection.getInstructor().professorFree(examDay, examSlotNumber)){
                //the original professor is available, so schedule him/her
                currentSection.setExamProctor(currentSection.getInstructor());
            }else{
                //the original professor is not available at this time
            }
        }
        
//  ___  _   ___ ___   ____
// | _ \/_\ / __/ __| |__ /
// |  _/ _ \\__ \__ \  |_ \
// |_|/_/ \_\___/___/ |___/
//          
// all possible ideal scheduling has been done (between sections and their original instructors, and their original classrooms)
// now time to fill in the pieces and find alternates where needed

        it = currentBucket.iterator(); 
        while(it.hasNext()) {
            currentSection = it.next(); 
            if(currentSection.getExamRoom()==null){
                //find a classroom
                
                Room currentRoom = null;
                for(Room currentRoomTemp : roomList){ 
                    //count down to the smallest possible classroom that can accomodate this section
                    if(currentRoomTemp.getCapacity()>=currentSection.getEnrolled()){
                        currentRoom = currentRoomTemp;
                    }else{
                        break;
                    }
                } 
                
                int i;
                for(i=roomList.indexOf(currentRoom);i>=0;i--){ 
                    //start with currentRoom and start looking at increasingly larger rooms until one is suitable and free
                    currentRoom = roomList.get(i);
                    
                    if(currentRoom.roomFree(examDay, examSlotNumber)){
                        if(currentSection.isComputerFinal() && currentRoom.isComputerized()){
                            //computerized room located, so schedule it
                            currentRoom.scheduleExam(currentSection, examDay, examSlotNumber);
                            currentSection.setExamRoom(currentRoom);
                            break;
                        }else if(currentRoom.getCapacity() >= 2*currentSection.getEnrolled()){
                            //any room (computerized or not) will do, so long as it's big enough
                            currentSection.getExamProctor().scheduleRoom(currentRoom, examDay, examSlotNumber);
                            currentRoom.scheduleExam(currentSection, examDay, examSlotNumber);
                            currentSection.setExamRoom(currentRoom);
                            break;
                        }
                    }
                currentSection.setExamProctor(currentSection.getInstructor());
                }
            } //END LOOP TO FIND A ROOM
            
            if(currentSection.getExamProctor()==null && currentSection.getExamRoom()!=null){
                //if proctor still unscheduled, but we have a room scheduled, find a proctor (otherwise skip, since it won't schedule anyway without a room)
                for(Professor currentProf : professorList){
                    //loop through all professors
                    if(currentProf.getDepartment().equals(currentSection.getDepartment())){
                        //only check this person if his/her department is correct
                        if(currentProf.professorFree(examDay, examSlotNumber)){
                            //current professor is free for the slot, so schedule!
                            currentProf.scheduleRoom(currentSection.getExamRoom(), examDay, examSlotNumber);
                            currentSection.setExamProctor(currentProf);
                            break;
                        }
                    }
                }   
            }
        }
        
        //System.out.println("Bucket looking at exam day "+examDay+" and timeslot "+examSlotNumber);
        //next bucket is assigned to the next timeslot in the week's schedule
        examSlotNumber++;
        if(examSlotNumber>=5){
            examDay++;
            examSlotNumber=0;
        }
    

//  ___  _   ___ ___    ___ 
// | _ \/_\ / __/ __|  / , |_
// |  _/ _ \\__ \__ \ /__   _|
// |_|/_/ \_\___/___/    |_|
//       
// Clean up pass

    it = currentBucket.iterator(); 
        while(it.hasNext()) {
            currentSection = it.next();
            if(currentSection.getExamRoom()!=null && currentSection.getExamProctor()!=null){
                // course has an exam room and a proctor, so it's scheduled
                it.remove();
            }
            if(currentSection.getExamRoom()==null || currentSection.getExamProctor()==null){
                //couldn't schedule, so break the links
                currentSection.setExamRoom(null);
                currentSection.setExamProctor(null);
            }
        }
    
    }// End the loop through all the buckets

        int count = 0;
        
        System.out.println("\nUnscheduled courses:");
        for(ArrayList<Section> currentBucket : bucketList){ 
            //loop through all buckets
            Iterator<Section> it = currentBucket.iterator(); 
            Section currentSection;
            while(it.hasNext()) {
                currentSection = it.next();
                System.out.println(currentSection);
                count++;
            }
            //System.out.println("");
        }
        System.out.println("\nTotal courses still unscheduled: " + count + "\n");

        for(Room currentRoom : roomList){ 
            System.out.println(currentRoom.printSchedule());
        }

    }
}