package com.example.firebaseauthtest;

public class Message {//使用者が送信したmessageを含むすべての情報がまとめてオブジェクトとして送られる
    String nickname;
    String msg;
    String date;
    String time;

    public Message(String n,String m, String d, String t){
        nickname = n;
        msg = m;
        date = d;
        time = t;
    }
    public Message(){}//リアルタイムdbで使う　publicなのはリアルタイムDBが外にあるから

    public String getNickname(){return nickname; }
    public String getMsg(){return msg; }
    public String getDate(){return date; }
    public String getTime(){return time; }
}
