package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDb {
    public PreparedStatement employeePayrollDataStatement;
    private static EmployeeDb employeeDbTest;

    public static EmployeeDb getInstance(EmployeeDb employeeDb) {
        if (employeeDbTest == null)
            employeeDbTest = new EmployeeDb();
        return employeeDb;
    }

    private Connection getConnection(String host, String DBname, String userName, String password) throws SQLException {
        String jdbcURL = "jdbc:mysql://" + host + ":3306/" + DBname + "?use_SSL=false";
        String driver = "com.mysql.jdbc.Driver";
        Connection connection = null;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        return connection;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) throws SQLException {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws SQLException {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
                System.out.println(id + name + salary + startDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection("localhost", "payroll_service",
                    "root", "Gagan@2107");
            String sql = "SELECT * FROM employee_payroll WHERE name =?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EmployeePayrollData> readDatadate(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = String.format("Select * from employee_payroll where start_date between '%s' and '%s'", startDate, endDate);
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection("localhost", "payroll_service",
                "root", "Gagan@2107");) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate start_Date = resultSet.getDate("start_date").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, start_Date));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employeePayrollDataList;
    }
}