package com.example.firebaseauthtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MemberRegister extends AppCompatActivity {
    FirebaseAuth firebaseAuth;//firebaseが提供するたった一つのクラス　認証に関するもの
    EditText emailTextbox, passTextbox;
    Button btUpload, btCancel;

    protected void onCreate(Bundle saveInstancestate){
        super.onCreate(saveInstancestate);
        setContentView(R.layout.member_register);

        firebaseAuth = FirebaseAuth.getInstance();//FirebaseAuthのインスタンス生成
        emailTextbox = (EditText)findViewById(R.id.email_textbox2);
        passTextbox = (EditText)findViewById(R.id.password_textbox2);
        btUpload= (Button)findViewById(R.id.bt_uplord);
        btCancel = (Button)findViewById(R.id.bt_cancel);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runUpload();

            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runCancel();

            }
        });
    }


    public void runCancel(){
        Toast.makeText(this,"cancel==",Toast.LENGTH_LONG);
        startActivity(new Intent(this,MainActivity.class));

    }

    public void runUpload(){//データを持ってこなきゃ
        String email = emailTextbox.getText().toString();//layoutで入力されたデータを持ってくる
        String password = passTextbox.getText().toString();

        //emailを入力せず登録ボタンを押してしまったミスの処理
        if(email==null || email.equals("")){
            Toast.makeText(this,"email 등록하세요",Toast.LENGTH_LONG);
            return;//メソッドの動作は止まる　uploadの処理を完成してはいけないから

        }
        if(password==null || password.equals("")||password.length()<6){//パスワード項目が空欄または６文字以下
            Toast.makeText(this,"６자리 이상의 암호를 입력하세요",Toast.LENGTH_LONG);
            return;//メソッドの動作は止まる　uploadの処理を完成してはいけないから

        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//AuthResultは認証された結果　OnCompleteListenerでそれが返されるということ
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//AuthResultをtaskという名前をつけて使用
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));//thisで問題が発生するときgetApplicationContext()に置き換える
                            //thisを使えないのは内部で新しく作られるインターフェイスを具現するオブジェクトなので
                        }else{
                            Toast.makeText(getApplicationContext(),"등록 실패",Toast.LENGTH_LONG);//getApplicationContext() 現在のアプリの情報を持ってくる関数　this変わり
                        }
                    }
                });

    }
}
