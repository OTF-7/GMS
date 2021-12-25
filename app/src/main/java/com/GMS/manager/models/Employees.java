package com.GMS.manager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Employees implements Parcelable {

    public static final Creator<Employees> CREATOR = new Creator<Employees>() {
        @Override
        public Employees createFromParcel(Parcel in) {
            return new Employees(in);
        }

        @Override
        public Employees[] newArray(int size) {
            return new Employees[size];
        }
    };
    private int employeeImage;
    private String employeeFirstName;
    private String employeeMiddleName;
    private String employeeLastName;
    private String employeeNeighborhood;
    private String employeeAge;
    private String employeeEmail;
    private String employeeUserName;
    private String employeePhone;
    private String employeeHiringDate;
    private String employeeJopType;

    public Employees() {
    }

    protected Employees(Parcel in) {
        employeeImage = in.readInt();
        employeeFirstName = in.readString();
        employeeMiddleName = in.readString();
        employeeLastName = in.readString();
        employeeNeighborhood = in.readString();
        employeeAge = in.readString();
        employeeEmail = in.readString();
        employeeUserName = in.readString();
        employeePhone = in.readString();
        employeeHiringDate = in.readString();
        employeeJopType = in.readString();
    }

    public String getEmployeeMiddleName() {
        return employeeMiddleName;
    }

    public void setEmployeeMiddleName(String employeeMiddleName) {
        this.employeeMiddleName = employeeMiddleName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeNeighborhood() {
        return employeeNeighborhood;
    }

    public void setEmployeeNeighborhood(String employeeNeighborhood) {
        this.employeeNeighborhood = employeeNeighborhood;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeHiringDate() {
        return employeeHiringDate;
    }

    public void setEmployeeHiringDate(String employeeHiringDate) {
        this.employeeHiringDate = employeeHiringDate;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public int getEmployeeImage() {
        return employeeImage;
    }

    public void setEmployeeImage(int employeeImage) {
        this.employeeImage = employeeImage;
    }


    public String getEmployeeJopType() {
        return employeeJopType;
    }

    public void setEmployeeJopType(String employeeJopType) {
        this.employeeJopType = employeeJopType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employeeImage);
        dest.writeString(employeeFirstName);
        dest.writeString(employeeMiddleName);
        dest.writeString(employeeLastName);
        dest.writeString(employeeNeighborhood);
        dest.writeString(employeeAge);
        dest.writeString(employeeEmail);
        dest.writeString(employeeUserName);
        dest.writeString(employeePhone);
        dest.writeString(employeeHiringDate);
        dest.writeString(employeeJopType);
    }
}
