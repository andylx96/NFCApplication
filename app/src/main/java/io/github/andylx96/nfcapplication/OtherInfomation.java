package io.github.andylx96.nfcapplication;

public class OtherInfomation {
    public String name;
    public String info;

    public OtherInfomation(){
        this.name = "";
        this.info = "";

    }

    public OtherInfomation(String name, String info){
        this.name = name;
        this.info = info;
    }
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
