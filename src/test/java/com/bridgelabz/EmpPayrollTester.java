package com.bridgelabz;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.List;

public class EmpPayrollTester {
    EmployeePayrollService employeePayrollService;
    List<EmployeePayrollData> employeePayrollList;

    @Test
    public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() throws SQLException {
        employeePayrollService = new EmployeePayrollService();
        employeePayrollList = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assert.assertEquals(2, employeePayrollList.size());
    }
}