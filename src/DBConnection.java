import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	public ResultSet getResultSet(String sqlStatement) {
		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=signature_store;integratedSecurity=true;";

		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
					
		try {
		 	// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			          
			// Create and execute an SQL statement that returns some data.
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			          
		}
			      
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

//		finally {
//			if (rs != null) try { rs.close(); } catch(Exception e) {}
//			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
//			if (con != null) try { con.close(); } catch(Exception e) {}
//		}
		return rs;

	}
	public int dml(String sqlStatement) {
		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=signature_store;integratedSecurity=true;";
		int result;
		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
					
		try {
		 	// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			          
			// Create and execute an SQL statement that returns some data.
			stmt = con.createStatement();
			stmt.executeUpdate(sqlStatement);
			result = 1;          
		}
			      
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}

		finally {
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (con != null) try { con.close(); } catch(Exception e) {}
		}
		return result;
	}
}
