package com.example.perez_finalproject;

import android.content.Context;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;



public class MyDbAdapter {

    myDbHelper myhelper;


    public  MyDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;   // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD= "Password";    // Column III
        private  static final String USERTYPE= "Usertype";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225),"+ USERTYPE+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context)
        {
            super(context, DATABASE_NAME, null,DATABASE_Version);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }

    }

    public long insertData(String name, String pass , String usertype)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        contentValues.put(myDbHelper.USERTYPE, usertype);
        long id = -1;

        if(!IsUserExists(name)){
            id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        }
        return id;
    }

    public String getData()  {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD,myDbHelper.USERTYPE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())   {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            @SuppressLint("Range") String  password 		=cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            @SuppressLint("Range") String  usertype 		=cursor.getString(cursor.getColumnIndex(myDbHelper.USERTYPE));
            buffer.append(cid+ "   " + name + "   " + password + " " + usertype +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName, String newPass)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        contentValues.put(myDbHelper.MyPASSWORD,newPass);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    public void createSuperAdminAcc()
    {
        insertData("admin", "admin", "SUPERADMIN");
        insertData("admintest", "admintest", "ADMIN");
    }

    public boolean IsUserExists(String Name)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD, myDbHelper.USERTYPE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())   {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            @SuppressLint("Range") String  password 		=cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            @SuppressLint("Range") String  usertype 		=cursor.getString(cursor.getColumnIndex(myDbHelper.USERTYPE));

            if(name.equals(Name))
            {
                return true;
            }
        }
        return false;
    }

    public boolean CheckerLogIn(String NameLog, String PassLog)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD, myDbHelper.USERTYPE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())   {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            @SuppressLint("Range") String  password 		=cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            @SuppressLint("Range") String  usertype 		=cursor.getString(cursor.getColumnIndex(myDbHelper.USERTYPE));

            if(name.equals(NameLog) && password.equals(PassLog))
            {
                return true;
            }
        }
        return false;
    }

    public boolean CheckerAdmin(String checkUser)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD, myDbHelper.USERTYPE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())   {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            @SuppressLint("Range") String usertype =cursor.getString(cursor.getColumnIndex(myDbHelper.USERTYPE));

            if(name.equals(checkUser))
            {
                return usertype.equals("ADMIN") || usertype.equals("SUPERADMIN");
            }
        }

        return false;
    }

    public String CheckerUserType(String NameLog, String PassLog)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD, myDbHelper.USERTYPE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())   {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            @SuppressLint("Range") String  password 		=cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            @SuppressLint("Range") String  usertype 		=cursor.getString(cursor.getColumnIndex(myDbHelper.USERTYPE));

            if(name.equals(NameLog) && password.equals(PassLog))
            {
                return usertype;
            }
        }

        return null;
    }

    public Integer deleteAllData()
    {
        Integer retVal = 0;
        String[] whereArgs= {"ADMIN"};
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME, myDbHelper.USERTYPE+" = ?", whereArgs);
        whereArgs = new String[]{"USER"};
        retVal = db.delete(myDbHelper.TABLE_NAME, myDbHelper.USERTYPE+" = ?", whereArgs);

        return retVal;
    }




}

