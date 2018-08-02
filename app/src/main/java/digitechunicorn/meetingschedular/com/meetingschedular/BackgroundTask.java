package digitechunicorn.meetingschedular.com.meetingschedular;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx=ctx;
    }


    protected String doInBackground(String... params) {

        String reg_url="http://192.168.43.253/meetingschedular/register.php";
        String del_url="http://192.168.43.253/meetingschedular/deletedata.php";
        String method=params[0];
        if(method.equals("register"))
        {
            String company_name=params[1];
            String address=params[2];
            String contact_no=params[3];
            String email=params[4];
            String contact_person=params[5];
            String date_of_visit=params[6];
            String purpose_of_visit=params[7];
            String time_of_visit=params[8];
            String visited_by=params[9];
            String status=params[10];
            String remarks=params[11];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("company_name","UTF-8")+"="+URLEncoder.encode(company_name,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                        URLEncoder.encode("contact_no","UTF-8")+"="+URLEncoder.encode(contact_no,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("contact_person","UTF-8")+"="+URLEncoder.encode(contact_person,"UTF-8")+"&"+
                        URLEncoder.encode("date_of_visit","UTF-8")+"="+URLEncoder.encode(date_of_visit,"UTF-8")+"&"+
                        URLEncoder.encode("purpose_of_visit","UTF-8")+"="+URLEncoder.encode(purpose_of_visit,"UTF-8")+"&"+
                        URLEncoder.encode("time_of_visit","UTF-8")+"="+URLEncoder.encode(time_of_visit,"UTF-8")+"&"+
                        URLEncoder.encode("visited_by","UTF-8")+"="+URLEncoder.encode(visited_by,"UTF-8")+"&"+
                        URLEncoder.encode("status","UTF-8")+"="+URLEncoder.encode(status,"UTF-8")+"&"+
                        URLEncoder.encode("remarks","UTF-8")+"="+URLEncoder.encode(remarks,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Meeting Scheduled";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(method.equals("delete")) {
            String data_id = params[1];

            try {
                URL url=new URL(del_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("data_id","UTF-8")+"="+URLEncoder.encode(data_id,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Record Deleted";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
