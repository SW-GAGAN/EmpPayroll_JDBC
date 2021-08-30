package com.bridgelabz;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollService {
    public enum IOService {CONSOLE_IO, DB_IO, REST_IO, FILE_IO}

    public List<EmployeePayrollData> employeePayrollList;
    public EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }


    /*constructor which takes List of EmployeePayrollData and initialises
     */

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this.employeePayrollList = employeePayrollList;
    }

    /* This method used to get data from DB
    @param takes param as IOservice
    @throws SQLException if DBactivity failed
    @return the list of EmployeepayrollData
     */

    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws SQLException {
        if (ioService.equals(IOService.DB_IO)) {
            this.employeePayrollList = new EmployeePayrollDBService().readData();
        }
        return employeePayrollList;
    }

    /*This method used to update the salary of employees
    @param takes parameters name and salary passed to method updateEmployessDataUsingStatement
    @return boolean value true if updated successfully else false
    */

    public boolean updateSalary(String name, double salary) {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        boolean result = employeePayrollDBService.updateEmployeeDataUsingStatement(name, salary);
        return result;
    }

    /* This method used to get data from DB
    @param takes name of employee
    @throws SQLException if DBactivity failed
    @return the list of EmployeepayrollData
     */
    public List<EmployeePayrollData> getData(String name) throws SQLException {
        List<EmployeePayrollData> employeePayrollDataList;
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDataList = employeePayrollDBService.getEmployeeDetails(name);
    }

    /* This method used to get data from DB
    @param takes name of employee
    @throws SQLException if DBactivity failed
    @return the boolean value if found successfully
     */

    public boolean checkEmployeePayrollInSyncWithDB(String name) throws SQLException {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        EmployeeDb employeeDbTest = new EmployeeDb();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeeDetails(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollList.stream().filter(employeePayrollDataItem ->
                employeePayrollDataItem.name.equals(name)).findFirst().orElse(null);
    }

    /*This method used to get The data of particular employeeName
     @param takes STARTDATE AND ENDDATE AS input
     @return list of employes data
     */

    public List<EmployeePayrollData> getData(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<EmployeePayrollData> employeePayrollDataList;
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDataList = employeePayrollDBService.readDatadate(startDate, endDate);
    }

    /*This method used to get The AggregateData employeeTable
     @return list of employes Agrreagate data
     */

    public List<EmployeePayrollData> readEmployeePayrollAggregate() throws SQLException {
        List<EmployeePayrollData> employeePayrollDataList;
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        employeePayrollDataList = employeePayrollDBService.employeePayrollAggregate();
        return employeePayrollDataList;
    }


}