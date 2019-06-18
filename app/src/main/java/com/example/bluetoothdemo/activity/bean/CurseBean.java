package com.example.bluetoothdemo.activity.bean;

public class CurseBean {

    private String num;
    private String name;
    private String teacher;
    private String creadit;
    private String ids; //选择此课程的学号

    public CurseBean(String num, String name, String teacher, String creadit, String ids) {
        this.num = num;
        this.name = name;
        this.teacher = teacher;
        this.creadit = creadit;
        this.ids = ids;
    }

    public void addId(String id){
        this.ids = this.ids + ";" + id;
        if (this.ids.startsWith(";")){  //如果是第一个则移除
            this.ids = this.ids.replaceFirst(";","");
        }
    }

    public void removeId(String id){
        this.ids = this.ids.replaceFirst(id,"");
    }

    public boolean haveId(String id){
        return this.ids.contains(id);
    }

    public CurseBean() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCreadit() {
        return creadit;
    }

    public void setCreadit(String creadit) {
        this.creadit = creadit;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
