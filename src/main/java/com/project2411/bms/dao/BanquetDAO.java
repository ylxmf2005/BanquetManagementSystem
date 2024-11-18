package com.project2411.bms.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.project2411.bms.connection.SQLConnection;
import com.project2411.bms.model.Banquet;
import com.project2411.bms.model.Meal;

public class BanquetDAO {
    private SQLConnection sqlConnection;

    public BanquetDAO(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    private int getNextBIN() throws SQLException {
        String getMaxBinSql = "SELECT MAX(BIN) AS MaxBIN FROM Banquet";
        List<Map<String, Object>> result = sqlConnection.executeQuery(getMaxBinSql);
        int newBIN = 1;
        if (!result.isEmpty() && result.get(0).get("MaxBIN") != null) {
            newBIN = ((Number) result.get(0).get("MaxBIN")).intValue() + 1;
        }
        return newBIN;
    }

    private int insertBanquet(Banquet banquet) throws SQLException {
        String sql = "INSERT INTO Banquet (BIN, Name, Date, Time, Address, Location, FirstName, LastName, Available, Quota) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = sqlConnection.executePreparedUpdate(sql, banquet.getParams());
        return rowsAffected;
    }

    public Banquet createBanquet(Banquet banquet) throws SQLException {
        int newBIN = getNextBIN();
        banquet.setBIN(newBIN);
        int rowsAffected = insertBanquet(banquet);
        if (rowsAffected > 0) {
            return banquet;
        } else {
            throw new SQLException("Failed to create the banquet.");
        }
    }

    public boolean updateBanquet(Banquet banquet) throws SQLException {
        String sql = "UPDATE Banquet SET Name=?, Date=?, Time=?, Address=?, Location=?, FirstName=?, LastName=?, Available=?, Quota=? WHERE BIN=?";
        int rowsAffected = sqlConnection.executePreparedUpdate(sql, banquet.getParams());
        return rowsAffected > 0;
    }

    // Assuming the frontend has already checked to ensure that the meals for the same banquet are different.
    public boolean addMealToBanquet(int banquetBIN, Meal meal) throws SQLException {
        String sql = "INSERT INTO Meal (BanquetBIN, DishName, Type, Price, SpecialCuisine) VALUES (?, ?, ?, ?, ?)";
        Object[] params = new Object[] {
            banquetBIN,
            meal.getDishName(),
            meal.getType(),
            meal.getPrice(),
            meal.getSpecialCuisine()
        };

        int rowsAffected = sqlConnection.executePreparedUpdate(sql, params);
        return rowsAffected > 0;
    }
}