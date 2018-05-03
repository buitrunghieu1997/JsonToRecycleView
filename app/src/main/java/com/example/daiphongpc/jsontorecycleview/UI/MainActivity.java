package com.example.daiphongpc.jsontorecycleview.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daiphongpc.jsontorecycleview.Model.Address;
import com.example.daiphongpc.jsontorecycleview.Model.Company;
import com.example.daiphongpc.jsontorecycleview.Model.Geo;
import com.example.daiphongpc.jsontorecycleview.Model.User;
import com.example.daiphongpc.jsontorecycleview.R;
import com.example.daiphongpc.jsontorecycleview.Service.API;
import com.example.daiphongpc.jsontorecycleview.Service.DbManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String ROOT_URL = "https://jsonplaceholder.typicode.com/";

    private final String TAG = "MainActivity";

    public static String DATA_ID = "data_id";
    public static String DATA_NAME = "data_name";
    public static String DATA_USERNAME = "data_username";
    public static String DATA_EMAIL = "data_email";
    public static Address DATA_ADDRESS;
    public static String DATA_STREET = "data_street";
    public static String DATA_SUITE = "data_suite";
    public static String DATA_CITY = "data_city";
    public static String DATA_ZIPCODE = "data_zipcode";
    public static Geo DATA_GEO;
    public static String DATA_LAT = "data_lat";
    public static String DATA_LNG = "data_lng";
    public static String DATA_PHONE = "data_phone";
    public static String DATA_WEBSITE = "data_website";
    public static Company DATA_COMPANY;
    public static String DATA_COMPANY_NAME = "data_company_name";
    public static String DATA_CATCH_PHRASE = "data_catch_pharse";
    public static String DATA_BS = "data_bs";

    final DbManager manager = new DbManager(MainActivity.this);

    List<User> userList;

    private ListView listView;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.list_item);

        try {
            getData();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N) //require nougat
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //có thể dùng view.getContext(), this.getBaseContext() or getApplicationContext() (k nên dễ lỗi)
        builder.setCancelable(false); //k thể hủy khi ấn ra ngoài

        builder.setMessage("Output : " + DATA_ID + ", " + DATA_NAME
                + ", " + DATA_USERNAME + ", " + DATA_EMAIL + ", " + DATA_STREET + ", " + DATA_SUITE + ", " + DATA_CITY
                + ", " + DATA_ZIPCODE + ", " + DATA_LAT + ", " + DATA_LNG + ", " + DATA_PHONE + ", " + DATA_WEBSITE + ", "
                + DATA_COMPANY_NAME + ", " + DATA_CATCH_PHRASE + ", " + DATA_BS);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

    public void getData() throws IOException, InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //API api = Client.getClient().create(API.class);
        API api = retrofit.create(API.class);

        if (isConnected()) {
            Call<List<User>> call = api.getListUser();

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                    userList = response.body();

                    for (int i = 0; i < userList.size(); i++) {
                        manager.addUser(userList.get(i));
                    }
                    showDialog("Loading from Internet", "Please wait! The application is connecting to the host.");
                    showList(userList);
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network Error, Load from database," +
                            " if the screen is blank, It means that the database is empty, try to make" +
                            "Your network available: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            try {
                userList = manager.getAllUser();
                showDialog("No Internet connection, Loading from Database",
                        "Loading from Database, If the screen is blank," +
                                " It means that the Database is empty, try to connect" +
                                " to a stable network.");
                showList(manager.getAllUser());
            } catch (SQLiteException el) {
                Toast.makeText(MainActivity.this, "SQL Exception: " + el.toString(), Toast.LENGTH_SHORT);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N) //require nougat
    private void showDialog(String tilte, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //có thể dùng view.getContext(), this.getBaseContext() or getApplicationContext() (k nên dễ lỗi)
        builder.setCancelable(false); //k thể hủy khi ấn ra ngoài

        builder.setTitle(tilte);
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showList(List<User> list) {
        int size = list.size();
        String[] listName = new String[list.size()];

        for (int i = 0; i < list.size(); ++i) {
            listName[i] = list.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listName); //R.layout.item file custom layout do nguoi dung tu tao
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowDetailData.class);

                User user = userList.get(position);

                intent.putExtra(DATA_ID, user.getId());
                intent.putExtra(DATA_NAME, user.getName());
                intent.putExtra(DATA_EMAIL, user.getEmail());
                intent.putExtra(DATA_USERNAME, user.getUsername());
                intent.putExtra(DATA_STREET, user.getAddress().getStreet());
                intent.putExtra(DATA_SUITE, user.getAddress().getSuite());
                intent.putExtra(DATA_CITY, user.getAddress().getCity());
                intent.putExtra(DATA_ZIPCODE, user.getAddress().getZipcode());
                intent.putExtra(DATA_LAT, user.getAddress().getGeo().getLat());
                intent.putExtra(DATA_LNG, user.getAddress().getGeo().getLng());
                intent.putExtra(DATA_PHONE, user.getPhone());
                intent.putExtra(DATA_WEBSITE, user.getWebsite());
                intent.putExtra(DATA_COMPANY_NAME, user.getCompany().getName());
                intent.putExtra(DATA_CATCH_PHRASE, user.getCompany().getCatchPharse());
                intent.putExtra(DATA_BS, user.getCompany().getBs());

                MainActivity.this.startActivity(intent);
            }
        });
    }

    public List<User> getListFromDb() {
        return manager.getAllUser();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }
}
