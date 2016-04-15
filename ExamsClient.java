import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class ExamsClient {
		
		// begin main method
		 public static void main(String[] args) throws IOException, SQLException{
			 
//	THIS IS USING ImportTxtFile class to import

		// read in .txt files (rooms.txt, courses.txt, buildings.txt)
//			 ImportTxtFile importRooms = new ImportTxtFile();
//			 importRooms.readFile("rooms.txt");
//			 
//			 ImportTxtFile importCourses = new ImportTxtFile();
//			 importCourses.readFile("courses.txt");
			 
			 ImportTxtFile importBuildings = new ImportTxtFile(); // 26 rows and 2 columns for buildings.txt
			//importBuildings.getNumRows();	
			 importBuildings.readFile("buildings.txt"); // insert .txt to read and parse
			 //importBuildings.createColumn(0); // insert column index value for particular column (based on readFile() txt file)

			 importBuildings.insertColumn(); 		 
			 
			 
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
