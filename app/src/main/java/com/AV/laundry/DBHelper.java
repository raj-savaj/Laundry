package com.AV.laundry;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Param on 1/30/2019.
 */

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(Context context) {
        super(context, "laundry.db", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS laundry(id INTEGER PRIMARY KEY AUTOINCREMENT ,Name varchar(200) NOT NULL,email varchar(200) NOT NULL,mno INTEGER(200) Not Null,pwd Text(200));");
        db.execSQL("CREATE TABLE IF NOT EXISTS member(mid INTEGER PRIMARY KEY AUTOINCREMENT  ,Name varchar(200) NOT NUll,Address varchar(200) NOT NULL,MobileNo text(200) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS clothes(cid INTEGER PRIMARY KEY AUTOINCREMENT , Name varchar(200) NOT NUll,Clothes varchar(200) NOT NULL,Date Text(200),status INTEGER DEFAULT 0,Price INTEGER DEFAULT 0);");
        db.execSQL("CREATE TABLE IF NOT EXISTS account(aid INTEGER PRIMARY KEY AUTOINCREMENT , Name varchar(200) NOT NUll,StartDate Text(200)NOT NULL,EndDate Text(200)NOT NULL,BasePrice Text(200)NOT NULL);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM laundry", null);
        return res;
    }

    public void insertData(String Name, String email, String mno, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertSQL = "INSERT INTO laundry (Name,email,mno,pwd) VALUES (?, ?, ?, ?);";
        db.execSQL(insertSQL, new String[]{Name, email, mno, pwd});
    }

    public int getLastId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT last_insert_rowid()", null);
        cur.moveToFirst();
        return cur.getInt(0);

    }

    public void checkData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * from laundry WHERE id=?";
        db.execSQL(sql, new Integer[]{id});

    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM laundry WHERE id = ?";
        db.execSQL(sql, new Integer[]{id});
    }
    /*----------------Member----------------*/
    public void insertDataMember(String Name, String Address,String MobileNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertSQL = "INSERT INTO member (Name,Address,MobileNo) VALUES (?, ?, ?);";
        db.execSQL(insertSQL, new String[]{Name, Address,MobileNo});
    }


    public Cursor getMember() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM member", null);
            return res;
        }
        catch (Exception ee) {
            Log.e("error", ee.getMessage());
        }
        return getData();
    }

    public Cursor getMembername() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT Name FROM member", null);
            return res;
        }
        catch (Exception ee) {
            Log.e("error", ee.getMessage());
            return null;
        }
    }

    public int getLastIdMember() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT last_insert_rowid()", null);
        cur.moveToFirst();
        return cur.getInt(0);

    }
    public void deleteMember(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM member WHERE mid = ?";
        db.execSQL(sql, new Integer[]{id});
    }

    /*----------------Clothes----------------*/

    public void insertDataClothes(String Name, String Clothes,String Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertSQL = "INSERT INTO clothes (Name,Clothes,Date) VALUES (?, ?, ?);";
        db.execSQL(insertSQL, new String[]{Name, Clothes,Date});
    }

    public Cursor getClths() {
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM clothes", null);
            return res;
        }
        catch (Exception ee) {
            Log.e("error", ee.getMessage());
        }
        return getData();
    }

    public Cursor getSortCloth(String name,String sdate,String edate)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM clothes  WHERE Name=? AND Date BETWEEN ? AND ? ORDER BY status",  new String[]{name, sdate,edate});
            return res;
        }
        catch (Exception ee) {
            Log.e("error", ee.getMessage());
        }
        return null;
    }

    public String AcceptPayment(int cid,int price)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            String insertSQL = "UPDATE clothes set Price="+price+",status=1 where cid="+cid+";";
            db.execSQL(insertSQL);
            return "OK";
        }catch (Exception ee)
        {
            return ee.getMessage();
        }
    }

    public boolean checkUser(String email, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "select * from laundry where email='" + email + "' and pwd='" + pwd + "'";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
            if(cursor!=null)
            {
                if(cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    SharedPreferences pref = this.context.getSharedPreferences("session", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", cursor.getString(1));
                    editor.putInt("id", cursor.getInt(0));
                    editor.putString("mno",cursor.getString(cursor.getColumnIndex("mno")));
                    editor.commit();
                    return true;
                }
                else
                {
                    Log.e("error","0");
                    return false;
                }
            }
            else
            {
                Log.e("Ok","cursor null");
                return false;
            }
        } catch (Exception e) {
            Log.e("Error", "-" + e.getMessage());
            return false;
        }
    }

    public Cursor getMemberInfo()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select c.Name,m.mobileNo,sum(case when c.status=1 then c.clothes*c.Price  else 0 end) as Paid, sum(case when c.status=0 then c.clothes  else 0 end) as Unpaid from clothes c,member m where c.Name=m.Name  group by m.Name;";
        Cursor cursor=db.rawQuery(sql,null);
        return cursor;
    }

    public Map<String,String>  getMemeber()
    {
        Map<String,String> map=new HashMap<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select sum(case when status=1 then clothes*Price  else 0 end) as Paid from clothes group by Name;";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        map.put("totamount","0");
        if(cursor.getCount()>0)
        {
            map.put("totamount",String.valueOf(cursor.getInt(0)));
        }
        cursor.close();
        sql="SELECT count(*) as tot FROM member";
        cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        map.put("totmem","0");
        if(cursor.getCount()>0)
        {
            map.put("totmem",String.valueOf(cursor.getInt(0)));
        }
        return map;
    }

    public void passchange(String pass,int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="UPDATE laundry set pwd='"+pass+"' where id="+id;
        db.execSQL(sql);
    }
    public void deleteaccount(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="DELETE FROM laundry where id="+id;
        db.execSQL(sql);
    }
}
