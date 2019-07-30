package com.example.bluetoothdemo.activity.login;

public class ParentBean {

    private int id;
    private String title;
    private int childTypeId;

    public ParentBean(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public ParentBean() {
    }

    public int getId() {
        return id;
    }

    public int getChildTypeId() {
        return childTypeId;
    }

    public void setChildTypeId(int childTypeId) {
        this.childTypeId = childTypeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
