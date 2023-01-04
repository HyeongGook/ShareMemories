package com.example.sharememories;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity {
    public EditText groupId;
    public EditText pw;
    public EditText nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        groupId = (EditText) findViewById(R.id.groupIdUp);
        nickname = (EditText) findViewById(R.id.nicknameUp);
        pw = (EditText) findViewById(R.id.passWordUp);

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveBtn = (Button) findViewById(R.id.saveAc);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference newUser = db.collection("users");
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("nickname", nickname.getText().toString());
                userInfo.put("pw", pw.getText().toString());
                newUser.document(groupId.getText().toString()).set(userInfo);
                finish();
            }
        });
    }
}