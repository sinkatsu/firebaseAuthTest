package com.example.firebaseauthtest;

public class Group {

    String nickname;
    String msg;
    String time;

    public Group (String n ,String m, String t){
        nickname = n;
        msg = m;
        time = t;
    }
    public Group(){}//リアルタイムdbで使う　publicなのはリアルタイムDBが外にあるから

    public String getNickname(){return nickname; }
    public String getMsg(){return msg; }
    public String getTime(){return time; }
}
