package digitechunicorn.meetingschedular.com.meetingschedular;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MeetingListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MeetingList> mMeetingList;

    public MeetingListAdapter(Context mContext, List<MeetingList> mMeetingListList) {
        this.mContext = mContext;
        this.mMeetingList = mMeetingListList;
    }

    @Override
    public int getCount() {
        return mMeetingList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMeetingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mContext,R.layout.my_list_item,null);
        final TextView tvCompanyName=(TextView)v.findViewById(R.id.list_company_name);
        final TextView tvContactPerson=(TextView)v.findViewById(R.id.list_contact_person);
        final TextView tvDate=(TextView)v.findViewById(R.id.list_date);
        final TextView tvTime=(TextView)v.findViewById(R.id.list_time);
        final TextView tvId=(TextView)v.findViewById(R.id.list_id);
        Button del=(Button)v.findViewById(R.id.delete);
        final String del_url="http://192.168.43.253/meetingschedular/deletedata.php";

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data_id=tvId.getText().toString();
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
                    tvCompanyName.setTextColor(Color.RED);
                    tvCompanyName.setText("Deleted");
                    tvContactPerson.setTextColor(Color.RED);
                    tvContactPerson.setText(("Canceled"));
                    tvDate.setText("");
                    tvTime.setText("");
                    MainActivity obj=new MainActivity();
                    obj.getData();
                    return;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        tvCompanyName.setText(mMeetingList.get(position).getCompanyName());
        tvContactPerson.setText(mMeetingList.get(position).getContactPerson());
        tvDate.setText(mMeetingList.get(position).getListDate());
        tvTime.setText(mMeetingList.get(position).getListTime());
        tvId.setText(mMeetingList.get(position).getListId());

        v.setTag(mMeetingList.get(position).getId());

        return v;
    }

}
