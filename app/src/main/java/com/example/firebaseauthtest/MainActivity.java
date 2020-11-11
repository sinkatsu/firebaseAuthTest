package com.example.firebaseauthtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth; //firebase에서 인증해주는 클래스
    EditText emailTextbox,passTextbox;//나중에   layout에서 메시지가 나옴
    Button btLogin,btRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();//firebase 인증을 위한 객체 가져오기

        emailTextbox = (EditText)findViewById(R.id.email_textbox);
        passTextbox = (EditText)findViewById(R.id.pass_textbox);
        btLogin= (Button) findViewById(R.id.bt_login);
        btRegister= (Button) findViewById(R.id.bt_register);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runLogin();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });
    }

    void runLogin(){
        String email= emailTextbox.getText().toString();
        String password= passTextbox.getText().toString();
        //firebase를 이용한 로그인
        firebaseAuth.signInWithEmailAndPassword(email,password)//名前の通りのメソッド　login를 위한 메소드
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//loginが成功したことを聞くリスナーメソッド
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//task안에 login 결과가 들어 있다.
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(),"login success",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainService.class));//mainActivity로 이동 intent처리 생략
                        }else{
                            Toast.makeText(getApplicationContext(),"login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goRegister(){
        startActivity(new Intent(this,MemberRegister.class));//同一なオブジェクトの中だとthisでもgetApplicationContext()でもok
    }

}