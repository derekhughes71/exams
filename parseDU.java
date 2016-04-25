package cis498;

import java.io.BufferedReader;
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class parseDU {

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
        
    System.out.println("Courses inputted: " + courseList.size());
    System.out.println("Professors inputted: " + professorList.size());
    System.out.println("Rooms inputted: " + roomList.size());
    
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