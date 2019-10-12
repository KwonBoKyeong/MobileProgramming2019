package com.example.bokyeong.project1;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity  {


    ArrayList<UserInfo> userList = new ArrayList<> (); //회원 정보 list

    boolean idCheck; //id 중복체크 확인
    boolean btnCheck = false; //중복체크 여부 확인
    Button btn_check, btn_signup, btn_cancel;
    RadioButton rd_btn;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_check = (Button)findViewById(R.id.btn_check); //중복체크 버튼
        btn_signup = (Button)findViewById(R.id.btn_signup); //회원가입 버튼
        btn_cancel = (Button)findViewById(R.id.btn_cancel); //취소 버튼
        rd_btn = (RadioButton)findViewById(R.id.rd_btn); //이용약관 버튼

        File files = new File(getFilesDir()+"userInfo.txt");

        //파일이 존재하지 않는 경우, 새로운 파일 생성
        if(files.exists()==false) {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "userInfo.txt", true));
                bw.write("");
                bw.close();
            }catch (Exception e){
                e.printStackTrace();
                Log.i("에러 메세지", "error");
            }
        }

        //중복확인 버튼을 누른 경우
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idCheck = true;
                btnCheck = true;

                EditText newID = (EditText)findViewById(R.id.userID);
                String id = newID.getText().toString();

                //아무 입력이 없는 경우
                if(id.equals("") == true) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                try{
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"userInfo.txt"));
                    String str = "";

                    //아이디가 있는지 확인
                    while((str = br.readLine()) != null){
                        if(str.indexOf(id) != -1) {
                            idCheck = false;
                            break;
                        }
                    }

                    if(idCheck == true) {
                        Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show();
                    }

                    br.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_LONG).show();
                }catch (IOException e) {
                    e.printStackTrace();
                    Log.i("에러 메세지", "error");

                }
            }
        });

        //취소버튼을 누른 경우 처음 화면으로 돌아감
        btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
        });

        //회원가입 버튼을 누른 경우
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //중복확인 했는지 체크
                if(btnCheck == false) {
                    Toast.makeText(getApplicationContext(), "중복확인을 해주세요.", Toast.LENGTH_LONG).show();
                }
                //이용약관에 동의했는지 체크
                else if(!rd_btn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "이용약관에 동의해주세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    EditText newID = (EditText)findViewById(R.id.userID);
                    EditText newPassword = (EditText)findViewById(R.id.password);
                    EditText newName = (EditText)findViewById(R.id.name);
                    EditText newPhoneNumber = (EditText)findViewById(R.id.phoneNumber);
                    EditText newAddress = (EditText)findViewById(R.id.address);

                    String id = newID.getText().toString();
                    String password = newPassword.getText().toString();
                    String name = newName.getText().toString();
                    String phoneNumber = newPhoneNumber.getText().toString();
                    String address = newAddress.getText().toString();


                    UserInfo userinfo = new UserInfo(id,password,name,phoneNumber,address);
                    userList.add(userinfo);

                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "userInfo.txt", true));
                        bw.write(userinfo.toString());
                        bw.newLine();
                        bw.close();
                        Log.i("파일 확인", "ok");

                    }catch (Exception e){
                        e.printStackTrace();
                        Log.i("에러 메세지", "error");
                    }
                        Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_LONG).show();

                        finish();
                }
            }
        });
    }

    public class UserInfo{

        private String userID;
        private String password;
        private String name;
        private String phoneNumber;
        private String address;

        public UserInfo(String userID, String password, String name, String phoneNumber, String address){
            this.userID = userID;
            this.password = password;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public String getUserID() {
            return userID;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String toString() {
            return userID + "\t" + password + "\t" + name + "\t" + phoneNumber + "\t" + address;
        }
    }
}
