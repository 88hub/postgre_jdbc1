package org.dmtv.postgre_jdbc;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        insertMethod();
        selectMethod();

    }

    public static void selectMethod() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mydb",
                    "postgres", "postgrespassword");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login FROM table1");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("login"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void insertMethod() {
        String sqlLine = "INSERT INTO table1 (login, password, date) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mydb",
                "postgres", "postgrespassword");) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlLine);
            int randNumUser = (int)(Math.random() * 10000);
            preparedStatement.setString(1, "newUser" + randNumUser);
            preparedStatement.setString(2,"newPassword" + randNumUser);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
