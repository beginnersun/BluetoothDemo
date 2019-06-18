package com.example.bluetoothdemo.activity.bean;

/**
 * 每选课成功后就会多一个bean
 */
public class ResultBean {

    private String curse_id;
    private String curse_name;
    private String curse_credit;
    private String teacher_name;
    private String student_id;
    private String studentname;
    private String result;

    public ResultBean(String curse_id, String curse_name, String curse_credit, String teacher_name, String student_id, String studentname, String result) {
        this.curse_id = curse_id;
        this.curse_name = curse_name;
        this.curse_credit = curse_credit;
        this.teacher_name = teacher_name;
        this.student_id = student_id;
        this.studentname = studentname;
        this.result = result;
    }

    public ResultBean() {
    }

    public String getCurse_id() {
        return curse_id;
    }

    public void setCurse_id(String curse_id) {
        this.curse_id = curse_id;
    }

    public String getCurse_name() {
        return curse_name;
    }

    public void setCurse_name(String curse_name) {
        this.curse_name = curse_name;
    }

    public String getCurse_credit() {
        return curse_credit;
    }

    public void setCurse_credit(String curse_credit) {
        this.curse_credit = curse_credit;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
