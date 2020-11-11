package com.example.firebaseauthtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Chat extends AppCompatActivity {//ログイン時ニックネームを決めた後に生成される
    EditText input;//送信するメッセージを入力するテキストボックス
    Button btSend;
    ListView listView;
    Intent it;
    ArrayList<Message> messages = new ArrayList<>();
    MessageAdapter madapter;
    FirebaseDatabase firebasedb;//このオブジェクトはシングルトーンである　立った１つのオブジェクト　なぜならこのクラスからコンストラクタを使ってオブジェクトを作るわけではなくただあるものを持ってきて使うだから
    //例えばカレンダーオブジェクトも別で作る必要はない　getinstanceで持ってこればいい　別のいんすたんすの中からでもそのまま持ってくればいいい
    DatabaseReference root;
    //DatabaseReference childRef;　ユーザー情報をdbから持ってくるためのフィールド　まだ構築前
    DatabaseReference childRef2;//dbからチャットデータを参照するフィールド


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        it = getIntent();
        final String userNickname = it.getStringExtra("nickname");//ニックネームを入力後にチャットが始まり誰が送信してるか表示するため
        input = findViewById(R.id.et_input);
        btSend = findViewById(R.id.bt_send);
        listView = findViewById(R.id.listview_chatarea);
        madapter = new MessageAdapter(messages,getLayoutInflater(),userNickname);//今のレイアウトに作られたLayoutInflater（リソースレイアウトを開始化させるもの）を渡す
        listView.setAdapter(madapter);
        firebasedb = FirebaseDatabase.getInstance();
        root = firebasedb.getReference();//dbの一番上のノードを持ってくる 課題ではルートに持ってくるのは下から２番目
        childRef2 = root.child("ChatData");//ルートノードの下の（chatdataという名の）ノードを持ってくる

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageToDB(userNickname);
            }
        });

        //ここまでchatが始まるまでの一連の準備

        childRef2.addChildEventListener(new ChildEventListener() {//childRef2の下にノードが生成されたのでこれが動作する
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message newMessage = snapshot.getValue(Message.class);//Message.classの意味　クラス形態でデータをもらう
                messages.add(newMessage);//arraylistに新しいメッセージを保存
                madapter.notifyDataSetChanged();//データセットが変わったので listviewが動作しろという意味
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessageToDB(String nickname){//sendボタンを押したとき実行　引数でニックネーム
        String inputMessage = input.getText().toString();
        Calendar calendar = Calendar.getInstance();//生成するのではなく持ってくるだけ　プログラムが実行されれば一個だけオブジェクトが生成される
        String date = calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.DAY_OF_MONTH);
        String time = (calendar.get(Calendar.HOUR_OF_DAY)+9)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
        Message messageObject = new Message(nickname,inputMessage, date, time);//メッセージオブジェクトを作る

        DatabaseReference msgNode = childRef2.push();//dbのルートノードの下につながるchildRef2の下に一個ノードを作るということ　pushはノード１つ作るという意味　★msgNodeの親ノードがchildRef2
        msgNode.setValue(messageObject);//ノードはオブジェクトを持つ

        input.setText("");//使用者が入力したメッセージをなくす
    }
}
