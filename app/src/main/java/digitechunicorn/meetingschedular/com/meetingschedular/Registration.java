package digitechunicorn.meetingschedular.com.meetingschedular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    Button submit;
    EditText company_name,address,contact_no,email_id,contact_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        submit=(Button)findViewById(R.id.submit);
        company_name=(EditText)findViewById(R.id.company_name);
        address=(EditText)findViewById(R.id.address);
        contact_no=(EditText)findViewById(R.id.contact);
        email_id=(EditText)findViewById(R.id.email);
        contact_person=(EditText)findViewById(R.id.contact_person);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(company_name.getText().toString().equals("") || address.getText().toString().equals("") || contact_no.getText().toString().equals("") || email_id.getText().toString().equals("") || contact_person.getText().toString().equals(""))) {
                    if(contact_no.getText().length()<10) {
                        Toast.makeText(getApplicationContext(),"Plese Enter Valid Contact No.",Toast.LENGTH_SHORT).show();
                    }
                    else if(email_id.getText().toString().contains("@") && email_id.getText().toString().contains(".")) {

                        Intent i = new Intent(Registration.this, Meeting_Schedular.class);
                        i.putExtra("company_name", company_name.getText().toString());
                        i.putExtra("address", address.getText().toString());
                        i.putExtra("contact_no", contact_no.getText().toString());
                        i.putExtra("email_id", email_id.getText().toString());
                        i.putExtra("contact_person", contact_person.getText().toString());
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Plese Enter Valid Email Id.",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Plese Fill All Fields then submit",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
