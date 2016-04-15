import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ImportTxtFile {

	String[][] parseByCols;
	String[] parseByRows;
    String[] nameCol;
    ArrayList<String[]> allColumns;
    
    int linenumber = 0;
    int numberOfCols;
    
    public ArrayList<String[]> getAllColumns(){
    	return allColumns;
    }
	
	public void readFile(String file) throws IOException {
		        
		// The name of the file to open.
        String fileName = file;
        
        // FileReader reads text files in the default encoding.
        FileReader fileReaderLine = new FileReader(fileName);        

        
        // count number lines in file - need to use separate BufferedReader
    	    LineNumberReader lnr = new LineNumberReader(fileReaderLine);    		    
    	        		    
    	        while (lnr.readLine() != null){
    	        	linenumber++;
    	        }
 	 
    	     lnr.close();
    	// end - count number of lines
    	            
    	            
    	    
    	    // FileReader reads text files in the default encoding.
    	    FileReader fileReader = new FileReader(fileName);
            
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            // to write results to a string
            StringBuffer fileContents = new StringBuffer();
            
            // This will reference one line at a time
            String line = bufferedReader.readLine();           
            
            // determine number of columns to create
            String[] parse = line.split("\\|");
		    numberOfCols = parse.length;
        		    
		    // hold each line in a separate dimension of an array
		    parseByCols = new String[linenumber][numberOfCols];
		    parseByRows = new String[linenumber];
		    nameCol = new String[linenumber];	
		    allColumns = new ArrayList<String[]>();
		    
		    // counters to iterate into 2d array
            int i = 0;
            int j = 0;
            
        // separate data into a matrix by [rows][columns]
            while(line != null) {
                
            	// Insert line into an array (parseByRows[])
            	if(i<=(linenumber-1)){
            		parseByRows[i] = line;
            		// System.out.println("Number i: "+i+"\nArray Row: "+parseByRows[i]);            		
            		
            		// parse the line by pipe delimiter
            		String[] items = parseByRows[i].split("\\|");
		        	
            		// place each parsed item into a column of each row/line
		        	for(int k=0; k<=(numberOfCols-1); k++){
			        	parseByCols[i][k] = items[k];
			        	// System.out.println("K = "+k+" Value["+i+"]["+k+"] "+parseByCols[i][k]);
			        	
		        	} // - end for        	
            	} // - end if
            	
            	fileContents.append(line).append("\n");
		        line = bufferedReader.readLine();		      
		       	        
		        // increase i counters
			    i++;
		     } // - end while loop
            
            
            // Always close files.
            bufferedReader.close(); 
            
            
        // put each column from matrix into own column            
            for(int k1=0; k1<=(numberOfCols-1); k1++){
		            
            		for(int i1=1; i1<=(linenumber-1); i1++){
		            	
		            	// puts every row from this column into its own array and prints out row value   
		            	nameCol[i1]= parseByCols[i1][k1]; 
		            	 	 
		            } // - end for 
		         
		         // insert each separated column into arraylist		         
		         allColumns.add(nameCol);
		         
		         // Print out each array from each arraylist field
		         System.out.println(Arrays.toString(allColumns.get(k1)));

            } // - end for
             
 }   // - end readFile()                     
   	
 

      
	      
	      public void insertColumn() throws SQLException{
	    	  
	    	  // to connect to database
	    	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exams","root","");
	    	  // to create statements to query db
	    	  Statement st = con.createStatement();  
	    	  // holds results from queries
	    	  ResultSet rs; 
	    		
	    	  String query = "INSERT into buildings (name)" + "VALUES (?)";
	    	  PreparedStatement preparedStmt = null;
			try {
				preparedStmt = con.prepareStatement(query);
				
				//preparedStmt.setString (1, ID);
		    	  for (String[] nameCols : allColumns) {
		    	      preparedStmt.setString (1, nameCols[0]); // this inserts array into values(?) of query
		    	      preparedStmt.executeUpdate();
		    	  }
		    	  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	      }



	  	/*
	        Java String to ArrayList Example.
	        This Java String to ArrayList example shows how to convert Java String containing values to
	        Java ArrayList.
	 
	 */
//	      public static void main(String args[]){
//	               
//	                //String object having values, separated by ","
//	                String strNumbers = "1,2,3,4,5";
//	               
//	                /*
//	                 * To convert Java String to ArrayList, first split the string and then
//	                 * use asList method of Arrays class to convert it to ArrayList.
//	                 */
//	               
//	                //split the string using separator, in this case it is ","
//	                String[] strValues = strNumbers.split(",");
//	               
//	                /*
//	                 * Use asList method of Arrays class to convert Java String array to ArrayList
//	                 */
//	                ArrayList<String> aListNumbers = new ArrayList<String>(Arrays.asList(strValues));
//	                System.out.println("Java String converted to ArrayList: " + aListNumbers);
//	        } // end mainS
//	

	
} // end class - ImportTxtFile
	
	