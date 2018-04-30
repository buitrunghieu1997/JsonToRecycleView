package com.example.daiphongpc.jsontorecycleview.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.daiphongpc.jsontorecycleview.Model.Address;
import com.example.daiphongpc.jsontorecycleview.Model.Company;
import com.example.daiphongpc.jsontorecycleview.Model.Geo;
import com.example.daiphongpc.jsontorecycleview.Model.User;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Json_Data";

    private static final String TABLE_NAME = "Json_lite"; //dồn vào một bảng chứ không chia ra làm nhiều bảng đối tượng cho đơn giản
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String STREET = "street";
    private static final String SUITE = "suite";
    private static final String CITY = "city";
    private static final String ZIPCODE = "zipcode";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String PHONE = "phone";
    private static final String WEBSITE = "website";
    private static final String COMPANYNAME = "companyname";
    private static final String CATCHPHRASE = "catchphrase";
    private static final String BS = "bs";

    private Context context;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DbManager", "DbManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + "integer primary key, " +
                NAME + " TEXT, " +
                USERNAME + " TEXT, " +
                EMAIL + " TEXT, " +
                STREET + " TEXT, " +
                SUITE + " TEXT, " +
                CITY + " TEXT, " +
                ZIPCODE + " TEXT, " +
                LAT + " TEXT, " +
                LNG + " TEXT, " +
                PHONE + " TEXT, " +
                WEBSITE + " TEXT, " +
                COMPANYNAME + " TEXT, " +
                CATCHPHRASE + " TEXT, " +
                BS + " TEXT)";

        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, user.getId());
        values.put(NAME, user.getName());
        values.put(USERNAME, user.getUsername());
        values.put(EMAIL, user.getEmail());
        values.put(STREET, user.getAddress().getStreet());
        values.put(SUITE, user.getAddress().getSuite());
        values.put(CITY, user.getAddress().getCity());
        values.put(ZIPCODE, user.getAddress().getZipcode());
        values.put(LAT, user.getAddress().getGeo().getLat());
        values.put(LNG, user.getAddress().getGeo().getLat());
        values.put(PHONE, user.getPhone());
        values.put(WEBSITE, user.getWebsite());
        values.put(COMPANYNAME, user.getCompany().getName());
        values.put(CATCHPHRASE, user.getCompany().getCatchPharse());
        values.put(BS, user.getCompany().getBs());

        db.insert(TABLE_NAME, null, values); //để như thế này thì null sẽ bị lỗi

        db.close();
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME, new String[]{ID, NAME,
                        USERNAME, EMAIL, STREET, SUITE, CITY, LAT, LNG,
                        PHONE, WEBSITE, COMPANYNAME, CATCHPHRASE, BS},
                ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Geo geo = new Geo(
                cursor.getString(9),
                cursor.getString(10));

        Company company = new Company(
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15));

        Address address = new Address(
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                geo);

        User user = new User(
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                address,
                cursor.getString(11),
                cursor.getString(12),
                company);

        cursor.close();
        db.close();

        return user;
    }

    public int Update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, user.getId());
        values.put(NAME, user.getName());
        values.put(USERNAME, user.getUsername());
        values.put(EMAIL, user.getEmail());
        values.put(STREET, user.getAddress().getStreet());
        values.put(SUITE, user.getAddress().getSuite());
        values.put(CITY, user.getAddress().getCity());
        values.put(ZIPCODE, user.getAddress().getZipcode());
        values.put(LAT, user.getAddress().getGeo().getLat());
        values.put(LNG, user.getAddress().getGeo().getLat());
        values.put(PHONE, user.getPhone());
        values.put(WEBSITE, user.getWebsite());
        values.put(COMPANYNAME, user.getCompany().getName());
        values.put(CATCHPHRASE, user.getCompany().getCatchPharse());
        values.put(BS, user.getCompany().getBs());

        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Geo geo = new Geo(
                        cursor.getString(9),
                        cursor.getString(10));

                Company company = new Company(
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15));

                Address address = new Address(
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        geo);

                User user = new User(
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        address,
                        cursor.getString(11),
                        cursor.getString(12),
                        company);

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

 public void deleteUser(User user) {
     SQLiteDatabase db = this.getWritableDatabase();

     db.delete(TABLE_NAME, ID+ " = ?",
             new String[] {String.valueOf(user.getId())});

     db.close();
 }

 public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME; //Có thể dùng select count(*) from...
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
 }
}
