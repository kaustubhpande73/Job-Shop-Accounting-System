package Indivisual_Project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class DataHandler {
private Connection conn;
// Azure SQL connection credentials
final static String HOSTNAME = "pand0021.database.windows.net";
final static String DBNAME = "cs-dsa-4513-sql-db";
final static String USERNAME = "pand0021";
final static String PASSWORD = "Stepbiggermx12!";
// Database connection string
final static String URL =
String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
HOSTNAME, DBNAME, USERNAME, PASSWORD);

private void getDBConnection() throws SQLException {
	
	if (conn != null) {
		return;
	}
	this.conn = DriverManager.getConnection(URL);
}

public boolean addCustomer(
        String customerName, String customerAddress, int category) throws SQLException {
    getDBConnection(); // Preparing the database connection

    final String CUSTOMER_INSERT_Q = "INSERT INTO Customer (customer_name, customer_address, category) VALUES (?, ?, ?);";

    try (final PreparedStatement stmt = conn.prepareStatement(CUSTOMER_INSERT_Q)) {
        stmt.setString(1, customerName);
        stmt.setString(2, customerAddress);
        stmt.setInt(3, category);

        int rowsAffected = stmt.executeUpdate();
        // If at least one record is updated, then we indicate success
        return rowsAffected > 0;
    } catch (Exception e) {
        // Handle any SQL errors
        e.printStackTrace();
        return false;
    }
}
}