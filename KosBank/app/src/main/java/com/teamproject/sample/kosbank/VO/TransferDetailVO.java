package com.teamproject.sample.kosbank.VO;

public class TransferDetailVO {
    private int         money;
    private String      account;
    private String      in_comment;
    private String      out_comment;
    private String      in_out;
    private String      in_outday;
    private String      in_outtime;
    private int         balance;

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getIn_comment() {
        return in_comment;
    }
    public void setIn_comment(String in_comment) {
        this.in_comment = in_comment;
    }
    public String getOut_comment() {
        return out_comment;
    }
    public void setOut_comment(String out_comment) {
        this.out_comment = out_comment;
    }
    public String getIn_out() {
        return in_out;
    }
    public void setIn_out(String in_out) {
        this.in_out = in_out;
    }
    public String getIn_outday() {
        return in_outday;
    }
    public void setIn_outday(String in_outday) {
        this.in_outday = in_outday;
    }
    public String getIn_outtime() {
        return in_outtime;
    }
    public void setIn_outtime(String in_outtime) {
        this.in_outtime = in_outtime;
    }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
