package io.github.andylx96.nfcapplication;

import java.util.ArrayList;

public class UserInfomation {

    public String name;
    public String info;
    public ArrayList<OtherInfomation> otherInfomationArrayList;

    public UserInfomation(){
        this.name = "NA Name";
        this.info = "NA Info";
        this.otherInfomationArrayList = new ArrayList<>();

    }

    public UserInfomation(String name, String info) {
        this.name = name;
        this.info = info;
        this.otherInfomationArrayList = new ArrayList<>();
        this.otherInfomationArrayList.add(new OtherInfomation("Test1", "Test2"));

    }
    public UserInfomation(String test){
//            this.name = name;
//            this.info = info;
            this.otherInfomationArrayList = new ArrayList<>();
            this.otherInfomationArrayList.add( new OtherInfomation("Test3","Test4"));

    }
    public UserInfomation(String name, String info, ArrayList<OtherInfomation> otherInfomationArrayList){
        this.name = name;
        this.info = info;
        this.otherInfomationArrayList = otherInfomationArrayList;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<OtherInfomation> getOtherInfomationArrayList() {
        return otherInfomationArrayList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setOtherInfomationArrayList(ArrayList<OtherInfomation> otherInfomationArrayList) {
        this.otherInfomationArrayList = otherInfomationArrayList;
    }

    @Override
    public String toString() {
        return name;
    }
}
