package com.example.bluetoothdemo.activity.login;

public class ChildBean {

    public final static int PARENT = 1;
    public final static int CHILD = 2;

    private int childId;
    private int parentId;
    private String title;
    private int type;

    public ChildBean(int parentId, int childId, String title,int type) {
        this.parentId = parentId;
        this.childId = childId;
        this.title = title;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isParnet(){
        return type == PARENT;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChildBean() {
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getChildId() {
        return childId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
