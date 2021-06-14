package com.GMS.manager.helperClasses;

public class EmployeeItem
{
    private int imageResource ;
    private String empName ;
    private String empJob ;
    private String empStatus ;

    public EmployeeItem(String empName, String empJob, String empStatus) {
        this.empName = empName;
        this.empJob = empJob;
        this.empStatus = empStatus;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpJob() {
        return empJob;
    }

    public String getEmpStatus() {
        return empStatus;
    }
}
