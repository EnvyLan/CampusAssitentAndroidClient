package com.example.envylan.campusassitentandroidclient.models;

import java.io.Serializable;

/**
 * Created by EnvyLan on 2016/3/14 0014.
 */
public class ClassInfo implements Serializable {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    private String className;
    private int fromClassNum;
    private int classNumLen;
    private int weekday;
    private String classRoom;
    private String teacherName;

    public void setPoint(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public int getFromX() {
        return fromX;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    public int getToX() {
        return toX;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public int getToY() {
        return toY;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getFromClassNum() {
        return fromClassNum;
    }

    public void setFromClassNum(int fromClassNum) {
        this.fromClassNum = fromClassNum;
    }

    public int getClassNumLen() {
        return classNumLen;
    }

    public void setClassNumLen(int classNumLen) {
        this.classNumLen = classNumLen;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
