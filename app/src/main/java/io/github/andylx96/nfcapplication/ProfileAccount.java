package io.github.andylx96.nfcapplication;

import java.util.ArrayList;

public class ProfileAccount {
    UserInfomation myProfile;
    ArrayList<UserInfomation> otherProfile;

    public ProfileAccount(UserInfomation myProfile, ArrayList<UserInfomation> otherProfile) {
        this.myProfile = myProfile;
        this.otherProfile = otherProfile;

    }

    public ProfileAccount(UserInfomation myProfile) {
        this.myProfile = myProfile;
        this.otherProfile = new ArrayList<UserInfomation>();

    }

    public ProfileAccount() {
        this.myProfile = new UserInfomation();
        this.otherProfile = new ArrayList<UserInfomation>();

    }


    public ProfileAccount(String test) {
        this.myProfile = new UserInfomation();
        this.otherProfile = new ArrayList<UserInfomation>();
        this.otherProfile.add(new UserInfomation("Timmy", "zoe"));


    }


    public ArrayList<UserInfomation> getOtherProfile() {
        return otherProfile;
    }

    public void setOtherProfile(ArrayList<UserInfomation> otherProfile) {
        this.otherProfile = otherProfile;
    }

    public UserInfomation getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(UserInfomation myProfile) {
        this.myProfile = myProfile;
    }
}
