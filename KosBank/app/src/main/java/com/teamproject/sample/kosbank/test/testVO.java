package com.teamproject.sample.kosbank.test;

public class testVO {

    String text1;
    String text2;
    String text3;
    private String testimg;

    public testVO(String text1, String text2, String text3, String testimg) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.testimg = testimg;
    }

    public String getTestimg() {
        return testimg;
    }
    public void setTestimg(String testimg) {
        this.testimg = testimg;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }
}
