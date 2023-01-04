package com.example.sharememories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        EditText groupId = (EditText) findViewById(R.id.groupId);
        EditText passWord = (EditText) findViewById(R.id.passWord);

        Button signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(groupId.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (passWord.getText().toString().equals(document.get("pw").toString()))
                                {
                                    String userNickname = document.get("nickname").toString();
                                    Toast myToast = Toast.makeText(getApplicationContext(), userNickname.concat("님, 환영합니다 :)"), Toast.LENGTH_SHORT);
                                    myToast.show();
                                    Intent intent = new Intent(LoginActivity.this, RetrieveActivity.class);
                                    intent.putExtra("groupId", groupId.getText().toString());
                                    startActivity(intent);
                                }
                                else {
                                    Toast myToast = Toast.makeText(getApplicationContext(), "올바르지 않은 비밀번호입니다.", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            } else {
                                // 로그인 실패
                                Toast myToast = Toast.makeText(getApplicationContext(), "올바르지 않은 그룹아이디입니다.", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        } else {
                            // 오류
                            Toast myToast = Toast.makeText(getApplicationContext(), "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }
                });
            }
        });

        Button signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}