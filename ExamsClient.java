import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;



public class ExamsClient {
		
		// begin main method
		 public static void main(String[] args) throws Exception{
			 
//	THIS IS USING ImportTxtFile class to import

// 		read in .txt files (rooms.txt, courses.txt, buildings.txt)
//			 ImportTxtFile importRooms = new ImportTxtFile();
//			 importRooms.readFile("rooms.txt");
			 
//			 ImportTxtFile importCourses = new ImportTxtFile();
//			 importCourses.readFile("courses.txt");
//			 System.out.println(importCourses.allColumns.size());
			 
			 ImportTxtFile importBuildings = new ImportTxtFile(); // 26 rows and 2 columns for buildings.txt
			 importBuildings.readFile("buildings.txt"); // insert .txt to read and parse
			 //System.out.println(importBuildings.allColumns.size());
			 DbConnect.post(importBuildings.allColumns); // insert columns into table fields in database
			 


			 // Test for proper data being retrieved in arraylist
//			 for( int i = 0; i <= (importBuildings.allColumns.size() - 1); i++) {
//				   String[] us = importBuildings.allColumns.get( i );
//				  
//				   for(int j=0; j < us.length; j++)
//					{
//						System.out.println("This is row"+j+" of array #"+i+": "+us[j]);
//					}				   
//			 }
			 
			 //importBuildings.insertColumn(); 		 
			 
			 
			 // parse .txt files into tables using arrays
	
			 
//	 	THIS IS USING ReadFile class to import
//		String file_name = "buildings.txt";
//			 
//		 try{
//			 ReadFile file = new ReadFile(file_name);
//			 String[] aryLines = file.OpenFile();
//			 
//			 int i;
//			 for(i=0; i< aryLines.length; i++){
//				 System.out.println(aryLines[i]);
//			 }
//		 }
//		catch (IOException e){
//			System.out.println(e.getMessage());		
//		}
//			
		 
//		 ReadFile file = new ReadFile("buildings.txt");
//		 file.OpenFile();
//		 file.readLines();
			 
			 
	
		 
			 
	} // - end main class
			

} // - end ExamsClient class
