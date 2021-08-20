package com.bridgelabz;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    public int id;
    public String name;
    public double Salary;
    public LocalDate date;

    public EmployeePayrollData(Integer id,String name,Double Salary){
        this.id=id;
        this.name=name;
        this.Salary=Salary;
    }
    public EmployeePayrollData(Integer id,String name,Double Salary,LocalDate date){
        this(id,name,Salary);
        this.date=date;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Salary=" + Salary +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.Salary, Salary) == 0 && Objects.equals(name, that.name) && Objects.equals(date, that.date);
    }

}