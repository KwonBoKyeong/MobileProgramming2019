package com.example.bokyeong.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    ArrayList<UserInfo> login = new ArrayList<> (); //회원 정보 list
    boolean check_id = false; //아이디 존재 여부 체크

    Button btn_login,btn_signup;
    EditText editID, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btn_login = (Button)findViewById(R.id.btn_login); //로그인 버튼
        btn_signup = (Button)findViewById(R.id.btn_signup); //회원가입 버튼

        //회원정보 파일을 읽은 후, 로그인과 비밀번호가 담긴 객체 생성
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"userInfo.txt"));

            Log.i("파일 확인", "ok");

            String line = null;
            while ((line = br.readLine()) != null) {
                String lines[] = line.split("\t");
                UserInfo si = new UserInfo(lines[0],lines[1]);
                login.add(si);
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"회원정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

        //로그인 시도하는 경우
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);

                editID = (EditText)findViewById(R.id.userID);
                editPassword = (EditText)findViewById(R.id.password);

                String id = editID.getText().toString();
                String password = editPassword.getText().toString();

                //아무 입력이 없는 경우
                if(id.equals("") == true) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                //해당 로그인을 찾아서 비밀번호가 일치하는지 확인
                for (UserInfo u : login) {
                    if (id.equals(u.userID)) {
                        if(password.equals(u.password)) {
                            check_id = true;
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                }

                //로그인에 실패한 경우
                if(check_id == false) {
                    Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_LONG).show();
                }

            }
        });

        //회원가입 화면으로 이동
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

    }


    public class UserInfo{

        private String userID;
        private String password;

        public UserInfo(String userID, String password){
            this.userID = userID;
            this.password = password;
        }
        public String getUserID() {
            return userID;
        }

        public String getPassword() {
            return password;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String toString() {
            return userID + "\t" + password ;
        }

    }
}
