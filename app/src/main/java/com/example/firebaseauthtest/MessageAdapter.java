package com.example.firebaseauthtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {//BaseAdapterはlistview を制御するアダプタ listviewに見せないといけない可視的なものを制御　※dbから持ってきたオブジェクトをレイアウトリソースに載せる作業　吹き出し一つを作る作業
    ArrayList<Message> messages;//動的にオブジェクトを保存する空間　メッセージに関する
    LayoutInflater inflater;//メッセージのリソースレイアウト（自分と相手の２つあるもの）を可視化させるもの　死んでいるリソースを使えるものとして生かす
    String userNickname;//dbから持ってくるメッセージオブジェクトを相手か自分か判別するためのフィールド

    MessageAdapter(ArrayList<Message> ms, LayoutInflater li, String nick){
        messages = ms;
        inflater = li;
        userNickname=nick;
    }

    @Override
    public int getCount(){return messages.size(); }//現在のメッセージの個数をもらう

    @Override
    public Object getItem(int i){return messages.get(i);}//与えられたインデックスを持つオブジェクトを返す

    @Override
    public long getItemId(int i){return i;}//リターン時自動型変換起きる　i はlong型に自動的に変わる　※アイテムの位置を表すもの

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){ //layoutリソースを使ってアイテム（メッセージ）を作る
        Message message= messages.get(i);//dbから持ってきたメッセージオブジェクトでアイテム作り
        View itemView;//メッセージ内容やその他情報を持つそれ自体のオブジェクトのこと　レイアウトリソースのwidgetと関連付けるもの
        if(message.nickname.equals(userNickname)){
            itemView= inflater.inflate(R.layout.my_message,viewGroup,false);//レイアウトリソースを使えるようにした　itemviewとmy_messageは一心同体　itemviewにinflateが命を与えた
            TextView tvNick = itemView.findViewById(R.id.tv_nick);
            TextView tvDate = itemView.findViewById(R.id.tv_date);
            TextView tvTime = itemView.findViewById(R.id.tv_time);
            TextView tvMsg = itemView.findViewById(R.id.tv_msgbox);
            tvNick.setText(message.getNickname());//ゲッターを使って値をセット
            tvDate.setText(message.getDate());
            tvTime.setText(message.getTime());
            tvMsg.setText(message.getMsg());
        }else {
            itemView= inflater.inflate(R.layout.others_message,viewGroup,false);
            TextView tvNick = itemView.findViewById(R.id.tv_nick2);
            TextView tvDate = itemView.findViewById(R.id.tv_date2);
            TextView tvTime = itemView.findViewById(R.id.tv_time02);
            TextView tvMsg = itemView.findViewById(R.id.tv_msgbox2);
            tvNick.setText(message.getNickname());
            tvDate.setText(message.getDate());
            tvTime.setText(message.getTime());
            tvMsg.setText(message.getMsg());
        }
        return itemView;//リソース形態にオブジェクトのデータを変えて返す
    }
}
