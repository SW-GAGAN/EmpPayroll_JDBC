package com.bridgelabz;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class Demo {

    public static void main(String[] args) {
        String jdbcURl = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "Gagan@2107";
        Connection connection;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        listDrivres();
        try {
            System.out.println("Connection to database:" + jdbcURl);
            connection = DriverManager.getConnection(jdbcURl, userName, password);
            System.out.println(" The connection is successful !!!" + connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void listDrivres() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println(" " + driverClass.getClass().getName());
        }
    }
}

