package com.zhuxiaoming.kr36.login;

/**
 * Created by zhuxiaoming on 16/5/28.
 * 登录时用EventBus向我的界面传值的自定义类
 */
public class LoginEventBean {
    String userName;
    String headImage;
    String id;
    String gender;

    public LoginEventBean() {
    }

    public LoginEventBean(String userName, String headImage, String id, String gender) {
        this.userName = userName;
        this.headImage = headImage;
        this.id = id;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
