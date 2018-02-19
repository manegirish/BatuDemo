package com.example.batudemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        Button submitButton = (Button)findViewById(R.id.register_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private class Call extends AsyncTask<RequestBody,Void,String>{
        @Override
        protected String doInBackground(RequestBody... requestBodies) {
            try {
                MakeCall.post("http://192.168.88.2/batu/register.php", requestBodies[0], RegisterActivity.class.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
    }

    private void register(){
        EditText nameBox = (EditText)findViewById(R.id.register_name);
        EditText emailBix = (EditText)findViewById(R.id.register_email);
        EditText passwordBox = (EditText)findViewById(R.id.register_password);

        String name = nameBox.getText().toString();
        String email = emailBix.getText().toString();
        String password = passwordBox.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("name", name)
                .build();
        new Call().execute(requestBody);
    }
}
