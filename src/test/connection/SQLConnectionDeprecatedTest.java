package test.connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.connection.QueryResult;
import main.connection.SQLConnection;
public class SQLConnectionDeprecatedTest {
    public static void main(String[] args) {
        SQLConnection dbConn = null;
        try {
            dbConn = new SQLConnection();

            // 1. Create a table
            System.out.println("\n1. Creating the AttendeeAccount table:");
            String sql = """
                    CREATE TABLE AttendeeAccount (
                        Email VARCHAR2(100) PRIMARY KEY,
                        FirstName VARCHAR2(50),
                        LastName VARCHAR2(50),
                        MobileNo VARCHAR2(20),
                        Password VARCHAR2(50),
                        Location VARCHAR2(100),
                        Address VARCHAR2(200),
                        Type VARCHAR2(20),
                        Organization VARCHAR2(50)
                    )
                    """;

            try {
                dbConn.executeUpdate(sql);
                System.out.println("Table created successfully.");
            } catch (SQLException e) {
                System.err.println("Error occurred while creating the table.");
                e.printStackTrace();
            }

            // 2. Insert data using executeUpdate(String sql) (without parameters)
            System.out.println("\n2. Inserting data (without parameters):");
            sql = """
                    INSERT INTO AttendeeAccount (Email, FirstName, LastName, MobileNo, Password, Location, Address, Type, Organization)
                    VALUES ('example@example.com', 'Ethan', 'Lee', '12345678', '12345678', 'Hong Kong', '15 Fat Kwong Street', 'Student', 'PolyU')
                    """;

            int rowsInserted = dbConn.executeUpdate(sql);
            // For INSERT, UPDATE, DELETE statements, the return int is the number of rows affected
            System.out.println("Inserted " + rowsInserted + " record(s).");

            // 3. Insert data using executePreparedUpdate(String sql, Object[] params) (with parameters)
            System.out.println("\n3. Inserting data (with parameters):");
            String insertPreparedSql = """
                    INSERT INTO AttendeeAccount (Email, FirstName, LastName, MobileNo, Password, Location, Address, Type, Organization)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            Object[] insertParams = {
                    "example2@example.com",
                    "Leo",
                    "Sun",
                    "88888888",
                    "88888888",
                    "Hong Kong",
                    "1 Hung Lai Road",
                    "Student",
                    "PolyU"
            };
            rowsInserted = dbConn.executePreparedUpdate(insertPreparedSql, insertParams);
            System.out.println("Inserted " + rowsInserted + " record(s).");

            // 4. Select data using executeQuery(String sql) (without parameters)
            System.out.println("\n4. Selecting data (without parameters):");
            String selectSql = "SELECT Email, FirstName, LastName FROM AttendeeAccount";
            try (QueryResult queryResult = dbConn.executeQuery(selectSql)) {
                ResultSet rs = queryResult.getResultSet();
                while (rs != null && rs.next()) {
                    String email = rs.getString("Email");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    System.out.println("Email: " + email + ", Name: " + firstName + " " + lastName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 5. Select data using executePreparedQuery(String sql, Object[] params) (with parameters)
            System.out.println("\n5. Selecting data (with parameters):");
            String selectPreparedSql = "SELECT Email, FirstName, LastName FROM AttendeeAccount WHERE Email = ?";
            Object[] selectParams = { "example2@example.com" };
            try (QueryResult queryResult = dbConn.executePreparedQuery(selectPreparedSql, selectParams)) {
                ResultSet rsPrepared = queryResult.getResultSet();
                if (rsPrepared != null && rsPrepared.next()) {
                    String email = rsPrepared.getString("Email");
                    String firstName = rsPrepared.getString("FirstName");
                    String lastName = rsPrepared.getString("LastName");
                    System.out.println("Email: " + email + ", Name: " + firstName + " " + lastName);
                } else {
                    System.out.println("No records found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 6. Update data using executeUpdate(String sql) (without parameters)
            System.out.println("\n6. Updating data (without parameters):");
            String updateSql = "UPDATE AttendeeAccount SET MobileNo = '88888888' WHERE Email = 'example@example.com'";
            int rowsUpdated = dbConn.executeUpdate(updateSql);
            System.out.println("Updated " + rowsUpdated + " record(s).");

            // 7. Update data using executePreparedUpdate(String sql, Object[] params) (with parameters)
            System.out.println("\n7. Updating data (with parameters):");
            String updatePreparedSql = "UPDATE AttendeeAccount SET Password = ? WHERE Email = ?";
            Object[] updateParams = { "12345678", "example2@example.com" };
            rowsUpdated = dbConn.executePreparedUpdate(updatePreparedSql, updateParams);
            System.out.println("Updated " + rowsUpdated + " record(s).");

            // 8. Delete data using executeUpdate(String sql) (without parameters)
            System.out.println("\n8. Deleting data (without parameters):");
            String deleteSql = "DELETE FROM AttendeeAccount WHERE Email = 'example@example.com'";
            int rowsDeleted = dbConn.executeUpdate(deleteSql);
            System.out.println("Deleted " + rowsDeleted + " record(s).");

            // 9. Delete data using executePreparedUpdate(String sql, Object[] params) (with parameters)
            System.out.println("\n9. Deleting data (with parameters):");
            String deletePreparedSql = "DELETE FROM AttendeeAccount WHERE Email = ?";
            Object[] deleteParams = { "example2@example.com" };
            rowsDeleted = dbConn.executePreparedUpdate(deletePreparedSql, deleteParams);
            System.out.println("Deleted " + rowsDeleted + " record(s).");

            // 10. Drop the table
            System.out.println("\n10. Dropping the AttendeeAccount table:");
            String dropTableSql = "DROP TABLE AttendeeAccount";
            try {
                dbConn.executeUpdate(dropTableSql);
                System.out.println("Table 'AttendeeAccount' dropped successfully.");
            } catch (SQLException e) {
                System.err.println("Error occurred while dropping the table.");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("An SQL exception occurred.");
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (dbConn != null) {
                dbConn.closeConnection();
            }
        }
    }
}