package com.project2411.bms.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.project2411.bms.connection.SQLConnection;
import com.project2411.bms.model.AttendeeAccount;
import com.project2411.bms.model.Reserves;

public class AttendeeAccountDao {
    private SQLConnection sqlConnection;

    public AttendeeAccountDao(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public AttendeeAccount getAttendeeByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM AttendeeAccount WHERE Email = ?";
        Object[] params = new Object[] { email };

        List<Map<String, Object>> results = sqlConnection.executePreparedQuery(sql, params);

        if (!results.isEmpty()) {
            Map<String, Object> row = results.get(0);
            return new AttendeeAccount(row);
        } else {
            return null; // Attendee not found
        }
    }

    public boolean updateAttendeeRegistrationData(String email, Reserves registrationData) throws SQLException {
        String sql = "UPDATE Reserves SET SeatNo=?, RegTime=?, DrinkChoice=?, MealChoice=?, Remarks=? WHERE AttendeeEmail=? AND BanquetBIN=?";
        int rowsAffected = sqlConnection.executePreparedUpdate(sql, registrationData.getParams());
        return rowsAffected > 0;
    }
}