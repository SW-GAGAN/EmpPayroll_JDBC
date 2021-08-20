package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    /*This method used to connect MysqlDatabase which uses Connection class of sql library
    @param takes Database_name, userName and password of Database as input
    @throws might throw sqlexception when connection fails due to wrong password or database non existence
    @returns instance of connection
     */

    private Connection getConnection(String host,String DBname,String userName,String password) throws SQLException {
        String jdbcURL = "jdbc:mysql://"+host+":3306/"+DBname+"?use_SSL=false";
        String driver = "com.mysql.jdbc.Driver";
        Connection connection = null;
        connection= DriverManager.getConnection(jdbcURL,userName,password);
        return  connection;
    }

    /*This Method used to read the data from Database using query
    which uses Connection method to create a connection to Database after retrieving the data from
    creates instance of EmployeePayrollData for every data retrieved and stores in  employeePayrollDataList
    @returns list of employeepayroll data
     */

    public List<EmployeePayrollData> readData() throws SQLException {
        String sql="Select * from employee_payroll";
        List<EmployeePayrollData> employeePayrollDataList=new ArrayList<>();
        try(Connection connection = this.getConnection("localhost","payroll_service",
                "root","Gagan@2107");) {
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql);
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                double salary=resultSet.getDouble("salary");
                LocalDate startDate=resultSet.getDate("start_date").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id,name,salary,startDate));
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
        try (Connection connection = this.getConnection("localhost","payroll_service",
                "root","Gagan@2107")) {
            Statement statement = connection.createStatement();
            int result= statement.executeUpdate(sql);
            if (result==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}