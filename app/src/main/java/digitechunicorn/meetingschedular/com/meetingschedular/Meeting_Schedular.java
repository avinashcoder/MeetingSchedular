package digitechunicorn.meetingschedular.com.meetingschedular;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Meeting_Schedular extends AppCompatActivity {
    Button schedule;
    EditText date_visit, purpose_visit, time_visit, visited, status, remarks;
    Calendar myCalendar = Calendar.getInstance();
    String notification_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting__schedular);
        schedule = (Button) findViewById(R.id.schedule_button);
        date_visit = findViewById(R.id.date_of_visit);
        purpose_visit = (EditText) findViewById(R.id.purpose_of_visit);
        time_visit = (EditText) findViewById(R.id.time_of_visit);
        visited = (EditText) findViewById(R.id.visited_by);
        status = (EditText) findViewById(R.id.status);
        remarks = (EditText) findViewById(R.id.remark);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date_visit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Meeting_Schedular.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time_visit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Meeting_Schedular.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_visit.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        Bundle bundle=getIntent().getExtras();
        final String company_name=bundle.getString("company_name");
        final String address=bundle.getString("address");
        final String contact_no=bundle.getString("contact_no");
        final String email_id=bundle.getString("email_id");
        final String contact_person=bundle.getString("contact_person");

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(date_visit.getText().toString().equals("") || purpose_visit.getText().toString().equals("") || time_visit.getText().toString().equals("") || visited.getText().toString().equals("") || status.getText().toString().equals("") || remarks.getText().toString().equals(""))) {
                    String method = "register";
                    BackgroundTask backgroundTask = new BackgroundTask(Meeting_Schedular.this);
                    backgroundTask.execute(method, company_name, address, contact_no, email_id, contact_person, date_visit.getText().toString(), purpose_visit.getText().toString(), time_visit.getText().toString(), visited.getText().toString(), status.getText().toString(), remarks.getText().toString());
                    notification_text=company_name+"   "+contact_person+"  "+date_visit.getText().toString()+"  "+time_visit.getText().toString();
                    //Show Notification
                    showNotification();


                    Intent main=new Intent(Meeting_Schedular.this,MainActivity.class);
                    startActivity(main);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Plese fill all the Fields",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_visit.setText(sdf.format(myCalendar.getTime()));
    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Meeting Scheduled")
                .setSmallIcon(R.drawable.ic_action_clock)
                .setContentTitle("Meeting Scheduled")
                .setContentText(notification_text)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
