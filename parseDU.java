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
        roomData = "sunnaData//rooms.txt";
        buildingData = "sunnaData//buildings.txt";
                
        ArrayList<Section> courseList = new ArrayList();
        ArrayList<Room> roomList = new ArrayList();
        ArrayList<Professor> professorList = new ArrayList();
        
        //ROOM DATA
        BufferedReader input = new BufferedReader(new FileReader(new File(courseData)));
        String line = input.readLine();
	
        while(line!=null){
            //System.out.println(line);
            /*Expecting these columns:
                Room| [0]
                Building| [1]
                Capacity| [2]
                maintenance_days| [3]
                Start_time| [4]
                Duration| [5]
                Computer_based_exams_enabled [6]
                -blank - [7] */
             
            String cols[] = line.split("\\|");
            /*
            public Room(String buildingCodeIn, String roomNumberIn, boolean computerizedIn, int capacityIn, String maintenanceDayIn, int maintenanceStartTimeIn, int maintenanceEndTimeIn) {
            */
            //System.out.println(cols.length);
            roomList.add(new Room(cols[1], cols[0], cols[6]=="Y", parseInt(cols[2]), cols[3], parseInt(cols[4]), parseInt(cols[4])+2*parseInt(cols[5])));
	    line = input.readLine();
	}
        
        
        //PROFESSOR DATA
        input = new BufferedReader(new FileReader(new File(courseData)));
        line = input.readLine();
        
        Set<String> profNames = new HashSet<String>();
	
        while(line!=null){
            //System.out.println(line);
            /*Expecting these columns:
                Course Number| [0]
                Department| [1]
                Call Number| [2]
                Days| [3]
                Time| [4]
                Instructor| [5]
                Room| [6]
                Building| [7]
                Number enrolled| [8]
                Availability during finals week| [9]
                Computer_based_exam [10] 
                -blank- [11] */

             
            String cols[] = line.split("\\|");
            /*public Professor(String fullNameIn, String departmentIn, String availabilityIn) {*/
            //System.out.println(cols.length);
            
            //sets do not allow duplicates, so only create a new professor object if the name doesn't already exist in the set profNames
                if(profNames.add(cols[5])){
                    professorList.add( new Professor(cols[5], cols[1], cols[9]) );
                }
	    line = input.readLine();
	}
        
        
        //COURSE SECTION DATA
        
        /* this one will be more complicated than buildings or professors, since the Section object needs 
            IDs for professor and classroom and things, so we need to build lookups into this routine to get them to match.
            Now I'm also considering building three more classes for courseList, professorList and roomList -- that would 
            allow us to write easy "look up this id in the list" methods. That might be the way to go.
        */
        
/*
        input = new BufferedReader(new FileReader(new File(courseData)));
        line = input.readLine();
	
        while(line!=null){
            //System.out.println(line);
            //Expecting these columns:
             //   Course Number| [0]
             //   Department| [1]
             //   Call Number| [2]
             //   Days| [3]
             //   Time| [4]
             //   Instructor| [5]
             //   Room| [6]
             //   Building| [7]
             //   Number enrolled| [8]
             //   Availability during finals week| [9]
             //   Computer_based_exam [10] 
             //   -blank- [11]
             
            String cols[] = line.split("\\|");
            //public Section(String callNumberIn, int professorIdIn, String offeredDaysIn, 
            //int startTimeIn, int endTimeIn, int roomIdIn, 
            //int enrolledIn, boolean computerFinalIn)
            //System.out.println(cols.length);
            
	    line = input.readLine();
	}
*/
    
    }
}