package com.example.perez_finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Admin extends AppCompatActivity  {

    EditText Name, Pass, Delete, CurrName, NewName, NewPass;
    MyDbAdapter helper;
    RadioGroup Usertype;
    RadioButton UserRdBtn, AdminRdBtn;
    Button deletebtn;

    public static boolean SuperAdminACC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Name = (EditText) findViewById(R.id.editName);
        Pass = (EditText) findViewById(R.id.editPass);
        Delete = (EditText) findViewById(R.id.editText6);
        CurrName = (EditText) findViewById(R.id.editText3);
        NewName = (EditText) findViewById(R.id.editText5);
        NewPass = (EditText) findViewById(R.id.editText7);
        Usertype = (RadioGroup) findViewById(R.id.radiogroup1);
        UserRdBtn = (RadioButton) findViewById(R.id.userradiobtn);
        UserRdBtn.setChecked(true);
        AdminRdBtn = (RadioButton) findViewById(R.id.adminradiobtn);
        deletebtn = (Button) findViewById(R.id.DeleteBtn);


        helper = new MyDbAdapter(this);

        if(!SuperAdminACC)
        {
            AdminRdBtn.setVisibility(View.GONE);
            deletebtn.setVisibility(View.GONE);
        }
    }
    public void addUser(View view)
    {
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        String typeuser = "USER";

        if(SuperAdminACC){
            if(Usertype.getCheckedRadioButtonId() == AdminRdBtn.getId()) {
                typeuser = "ADMIN";
            }else{
                typeuser="USER";
            }
        }

        if(t1.isEmpty() || t2.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter Both Name and Password");
        }
        else
        {

        }
        long id = helper.insertData(t1,t2, typeuser);
        if(id<=0)
        {
            Message.message(getApplicationContext(),"Insertion Unsuccessful");
        } else
        {
            Message.message(getApplicationContext(),"Insertion Successful");
        }
        Name.setText("");
        Pass.setText("");
    }
    public void viewdata(View view)
    {
        String data = helper.getData();
        Message.message(this,data);
    }


    public void delete( View view) {
        String uname = Delete.getText().toString();
        if (uname.isEmpty())
        {
            Message.message(getApplicationContext(), "Enter Data");
        } else
        {
            int a = -1;

            if (!helper.CheckerAdmin(uname) || SuperAdminACC)
            {
                a = helper.delete(uname);
            }

            if (a <= 0)
            {
                Message.message(getApplicationContext(), "Unsuccessful");
                Delete.setText("");
            } else {
                Message.message(this, "DELETED");
                Delete.setText("");
            }
        }
    }


    public void update( View view)
    {
        String u1 = CurrName.getText().toString();
        String u2 = NewName.getText().toString();
        String u3 = NewPass.getText().toString();
        if(u1.isEmpty() || u2.isEmpty() || u3.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter Data");
        }
        else
        {
            int a = -1;

            if(!helper.CheckerAdmin(u1) || SuperAdminACC)
            {
                a = helper.updateName( u1, u2, u3);
            }

            if(a<=0)
            {
                Message.message(getApplicationContext(),"Unsuccessful");
                CurrName.setText("");
                NewName.setText("");
                NewPass.setText("");
            }
            else
            {
                Message.message(getApplicationContext(),"Updated");
                CurrName.setText("");
                NewName.setText("");
                NewPass.setText("");
            }
        }
    }

    public void deleteAll(View view)
    {

                Integer var = helper.deleteAllData();
                if(var > 0)
                {
                    Toast.makeText(Admin.this,"Data has been deleted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Admin.this, "Deletion Error", Toast.LENGTH_SHORT).show();
                }
    }

    public void close(View view)
    {
        finish();
    }


}