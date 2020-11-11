package com.example.firebaseauthtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainService extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView tvMessage;
    private Button btJoin;
    EditText etNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);

        tvMessage= (TextView)findViewById(R.id.tv_message);
        btJoin = (Button)findViewById(R.id.bt_join);
        firebaseAuth = FirebaseAuth.getInstance();//firebaseが支援するオブジェクトはこれのみ
        etNick = (EditText)findViewById(R.id.et_nick);
        if(firebaseAuth.getCurrentUser() == null){//ユーザー検索の結果がnull（ない）ならまたログインさせる
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();//사용자 객체 반환이 됨
        tvMessage.setText("welcom!"+currentUser.getEmail());//認証されたユーザーのメアドを返す
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChat(etNick.getText().toString());
            }
        });
    }

    void goChat(String nickname){
        //firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this,Chat.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }
}