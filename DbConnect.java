// connect to mysql database
import java.sql.*;

public class DbConnect {

	private Connection con; // to connect to database
	private Statement st;  // to create statements to query db
	private ResultSet rs; // holds results from queries
	
	

// constructor for class
// connects to database per "con" variable via JDBC driver
//	public DbConnect(){
//		
//		try{
//	
//			Class.forName("com.mysql.jdbc.Driver");
//			
////			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exams","root","");
////			//con = DriverManager.getConnection("jdbc:mysql://70.39.248.227:3306/deltau5_du_database","deltau5_team4","deltau!");
////			st = con.createStatement();
//			
//			
//		}catch(Exception ex){
//			System.out.println("Error: " + ex);
//		}
//	} // - end constructor
	
	
	public static Connection getConnection() throws Exception{
		
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/exams";
			String username = "root";
			String pwd = "";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, username, pwd);
			System.out.println("Connected");			
			return conn;
			
		} catch(Exception e) {
			System.out.println(e);			
		}
		return null;
	}

	
	
// select records from DB and print them out
	public void selectData(){
		
		try{
			String query= "select * from buildings";
			rs = st.executeQuery(query);
			System.out.println("Records from DB");
			
			while(rs.next()){
				String name = rs.getString("name");
				String availability = rs.getString("availability");
				System.out.println("Name: " + name + "\t" + 
				"Availability on Weekends: " + availability);
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	
	public static void post() throws Exception{
//		final String var1 = "john";
//		final String var2 = "smith";
//		final String[] array1 = {"john","smith","bill","stacie","summer","pol","seth"};
//		final String[] array2 = {"jeep","lambo","ferrari","hummer","bugatti", "ford","chevy"};

ImportTxtFile.allColumns
		
		
		try{
			Connection con = getConnection();
			// normal INSERT with known values
			//PreparedStatement posted = con.prepareStatement("INSERT into buildings (name,availability) VALUES ('"+var1+"','"+var2+"')");
			// INSERT with array values
			PreparedStatement posted = con.prepareStatement("INSERT into buildings (name,availability) VALUES (?,?)");
			
			
			// w. diffrnt lngth arrays, will only insert amnt of values equal to smalst array length
			for(int i = 0; i < array1.length; i++) {
	    	      posted.setString (1, array1[i]); // this inserts array into values(?) of query
	    	      posted.setString (2, array2[i]); // this inserts array into values(?) of query
	    	      posted.executeUpdate();
	    	  }
	    	  
	    	  //posted.setString(2, var2);
	    	  
		} catch(Exception e){
			System.out.println(e);
		} finally {
			System.out.println("Insert Completed:");
		}
		
	}
	
	
	
// insert records into DB
	public void insertData(String insertStatement){
				
		String statement = insertStatement;
			
		try{
			String insert = statement;
			st.executeUpdate(insert);
			System.out.println("Insert values into DB");
									
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

	
	
// update records in DB
	public void updateData(String updateStatement){
		
		String statement = updateStatement;
					
		try{
			String update = statement;
			st.executeUpdate(update);
			System.out.println("Updated values in DB");
									
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

	

// delete records from DB
	public void deleteData(String deleteStatement){
		
		String statement = deleteStatement;
					
		try{
			String delete = statement;
			st.execute(delete);
			System.out.println("Removed values in DB");
									
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	
	
// load data into DB table from a txt file	
	public void loadData(String loadStatement)
    {
		String statement = loadStatement;
		
		// file to be loaded must be located in wamp server directory for 'exams' database
		// C:\wamp\bin\mysql\mysql5.1.53\data\exams <- for localhost location on Derek's cpu
 
        try
        {
        	String load = ""+statement+"";
			st.executeUpdate(load);
			System.out.println("Loaded values in DB");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            st = null;
        }
    }

	
	
// main method to test code for DbConnect classes
	public static void main(String[] args) throws Exception{
		//DbConnect connect = new DbConnect();
		DbConnect.getConnection();
		DbConnect.post();
		
		//connect.selectData();
		//connect.insertData("insert into buildings(name,availability) values ('DDD','Y')");
		// connect.insertData("insert into buildings(name) values ("+ImportTxtFile.nameCol+")");
		//connect.updateData("update buildings set availability = 'N' where name = 'XYZ'");
		//connect.deleteData("delete from buildings where name = 'DDD'");
		//connect.loadData("LOAD DATA INFILE 'buildings.txt' INTO TABLE buildings FIELDS TERMINATED BY '|' (name, availability)");
		//connect.loadData("LOAD DATA INFILE 'buildings.txt' INTO TABLE buildings FIELDS TERMINATED BY '|' (name, availability)");
		
		
//		  /* String to split. */
//		  String[] temp;
//		  		  		  
//		  String str = "one|two|three";
//		  String delimiter = "\\|";
//		  temp = str.split(delimiter);
//		  for(int i =0; i < temp.length ; i++)
//		    System.out.println(temp[i]);
//		  
//		  System.out.println("column 1	column 2	column 3\n" +temp[0]+ "	" + temp[1]+ "	"+temp[2]);
//		  
//		  
		  
	
	}
	
	
} // - end class DbConnect


