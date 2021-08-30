package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    public PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    /*This method used to connect MysqlDatabase which uses Connection class of sql library
    @param takes Database_name, userName and password of Database as input
    @throws might throw sqlexception when connection fails due to wrong password or database non existence
    @returns instance of connection
     */

    private Connection getConnection(String host, String DBname, String userName, String password) throws SQLException {
        String jdbcURL = "jdbc:mysql://" + host + ":3306/" + DBname + "?use_SSL=false";
        String driver = "com.mysql.jdbc.Driver";
        Connection connection = null;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        return connection;
    }

    /*This Method used to read the data from Database using query
    which uses Connection method to create a connection to Database after retrieving the data from
    creates instance of EmployeePayrollData for every data retrieved and stores in  employeePayrollDataList
    @returns list of employeepayroll data
     */

    public List<EmployeePayrollData> readData() throws SQLException {
        String sql = "Select * from employee_payroll";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection("localhost", "payroll_service",
                "root", "Gagan@2107");) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /*This method used to update the salary of employees
    @param takes parameters name and salary
    @return boolean value true if updated successfully else false
     */
    public boolean updateEmployeeDataUsingStatement(String name, Double salary) {
        //alternate way String sql1 = " update employee_payroll set salary ='"+salary+"' where name ='"+name+"'";
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try (Connection connection = this.getConnection("localhost", "payroll_service",
                "root", "Gagan@2107")) {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            if (result == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*This method used to get The data of particular employeeName
    @param takes name as an input
    @return list of employes data
     */

    public List<EmployeePayrollData> getEmployeeDetails(String name) throws SQLException {
        String sql = String.format("Select * from employee_payroll where name='%s'", name);
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection("localhost", "payroll_service",
                "root", "Gagan@2107");) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name1, salary, startDate));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employeePayrollDataList;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
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

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
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

    /*This method used to get The data of particular employeeName
     @param takes STARTDATE AND ENDDATE AS input
     @return list of employes data
     */

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