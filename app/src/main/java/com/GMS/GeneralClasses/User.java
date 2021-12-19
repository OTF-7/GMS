package com.GMS.GeneralClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String firstName, midName, lastName, email, userName, password, phone;
    private int userType;

    public User() {
    }

    public User(String firstName,
                String midName,
                String lastName,
                String email,
                String userName,
                String password,
                String phone,
                int userType) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.userType = userType;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        midName = in.readString();
        lastName = in.readString();
        email = in.readString();
        userName = in.readString();
        password = in.readString();
        phone = in.readString();
        userType = in.readInt();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(midName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeInt(userType);
    }
}
