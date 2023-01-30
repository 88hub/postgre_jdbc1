package org.dmtv.postgre_jdbc;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        insertMethod("myUser123", "311415", "test@test.com");
        selectMethod();

    }

    public static void selectMethod() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mydb",
                    "postgres", "postgrespassword");

            Statement statement = connection.createStatement();
            String query = "SELECT t1.login AS login1, t2.login as login2 " +
                    "FROM table1 t1 JOIN table2 t2 ON t1.login = t2.login";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("login1"));
                System.out.println(resultSet.getString("login2"));
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

    public static void insertMethod(String login, String password, String mail) {
        String sqlLine = "INSERT INTO table1 (login, password, date) VALUES (?, ?, ?)";
        String sqlLine2 = "INSERT INTO table2 (login, email) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mydb",
                "postgres", "postgrespassword");) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlLine);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2,password);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlLine2);
            preparedStatement2.setString(1, login);
            preparedStatement2.setString(2, mail);
            preparedStatement2.executeUpdate();
            preparedStatement2.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
