package com.bridgelabz;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollService {
    public EmployeePayrollService() {

    }
    public enum IOService{CONSOLE_IO,DB_IO,REST_IO,FILE_IO}
    public List<EmployeePayrollData> employeePayrollList;

    /*constructor which takes List of EmployeePayrollData and initialises
     */

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList ){
        this.employeePayrollList=employeePayrollList;
    }

    /* This method used to get data from DB
    @param takes param as IOservice
    @throws SQLException if DBactivity failed
    @return the list of EmployeepayrollData
     */

    public List<EmployeePayrollData>  readEmployeePayrollData(IOService ioService) throws SQLException {
        if(ioService.equals(IOService.DB_IO)){
            this.employeePayrollList= new EmployeePayrollDBService().readData();
        }
        return employeePayrollList;
    }
}
