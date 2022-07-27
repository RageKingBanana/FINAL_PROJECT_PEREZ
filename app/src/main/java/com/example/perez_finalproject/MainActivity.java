package com.example.perez_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button LoginBTN;
    EditText Username, Password;
    MyDbAdapter helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.login_username_input);
        Password = (EditText) findViewById(R.id.login_password_input);
        LoginBTN = (Button) findViewById(R.id.login_btn);

        helper = new MyDbAdapter(this);
        helper.createSuperAdminAcc();

        LoginBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == LoginBTN)
        {
            String Usermain;
            String Passwordmain;

            Usermain = Username.getText().toString();
            Passwordmain = Password.getText().toString();

            if(helper.CheckerLogIn(Usermain, Passwordmain) && helper.CheckerUserType(Usermain, Passwordmain).equals("SUPERADMIN"))
            {
                Intent intent = new Intent(this, Admin.class);
                Admin.SuperAdminACC = true;
                startActivity(intent);
            }
            else if(helper.CheckerLogIn(Usermain, Passwordmain) && helper.CheckerUserType(Usermain, Passwordmain).equals("ADMIN"))
            {
                Intent intent = new Intent(this, Admin.class);
                Admin.SuperAdminACC  = false;
                startActivity(intent);
            }
            else if(helper.CheckerLogIn(Usermain, Passwordmain) && helper.CheckerUserType(Usermain, Passwordmain).equals("USER"))
            {
                Intent intent = new Intent(this, User.class);
                User.Username = Usermain + "\n";
                startActivity(intent);
            }
            else
            {
                Message.message(getApplicationContext(),"Invalid Username or Password!");
                Username.setText("");
                Password.setText("");
            }


        }
    }

}