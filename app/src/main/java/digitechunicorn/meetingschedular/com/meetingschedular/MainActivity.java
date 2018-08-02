package digitechunicorn.meetingschedular.com.meetingschedular;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button add;
    ListView lv;

    String urlAddress="http://192.168.43.253/meetingschedular/retrive.php";
    InputStream is=null;
    String line=null;
    String result=null;
    String[] data;

    private Context mContext;

    public MeetingListAdapter adapter;
    public List<MeetingList> mMeetingList;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=(Button)findViewById(R.id.add);
        lv= (ListView) findViewById(R.id.listView);

        mMeetingList =new ArrayList<>();

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getData();


        adapter=new MeetingListAdapter(getApplicationContext(), mMeetingList);
        lv.setAdapter(adapter);

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Clicked Item="+view.getTag(),Toast.LENGTH_SHORT).show();
            }
        });*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Clicked item="+position,Toast.LENGTH_SHORT).show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iii=new Intent(MainActivity.this,Registration.class);
                startActivity(iii);
            }
        });



    }

    public void getData()
    {
        try
        {
            URL url=new URL(urlAddress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();

            con.setRequestMethod("GET");

            is=new BufferedInputStream(con.getInputStream());

        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Internet Connection or Server are not responding",Toast.LENGTH_LONG).show();
        }

        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();

            while((line=br.readLine()) != null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try
        {
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;

            data=new String[ja.length()];

            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String Data_id=jo.getString("id");
                String Company_name=jo.getString("company_name");
                String Contact_person=jo.getString("contact_person");
                String Date_of_visit=jo.getString("date_of_visit");
                String Time_of_visit=jo.getString("time_of_visit");

                mMeetingList.add(new MeetingList(i,Company_name,Contact_person,Date_of_visit,Time_of_visit,Data_id));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteData(String method,String data_id){
        String method_name=method;
        String row_id=data_id;
        BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
        backgroundTask.execute(method_name,row_id);

    }

}
