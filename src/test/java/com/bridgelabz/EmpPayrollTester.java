package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpPayrollTester {
    EmployeePayrollService employeePayrollService;
    List<EmployeePayrollData> employeePayrollList;

    @Test
    public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() throws SQLException {
        employeePayrollService = new EmployeePayrollService();
        employeePayrollList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertEquals(3, employeePayrollList.size());
    }

    @Test
    public void givenEmployeePayrollUpdateDetails_whenUpdated_shouldMatch() throws SQLException {
        employeePayrollService = new EmployeePayrollService();
        employeePayrollList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        boolean result = employeePayrollService.updateSalary("marlin", 32.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenEmployeeName_WhenRetrieved_shouldReturnParticularData() throws SQLException {
        employeePayrollService = new EmployeePayrollService();
        employeePayrollList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("marlin");
        Assertions.assertEquals(result, true);
    }


}