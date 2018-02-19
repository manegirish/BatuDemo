package com.example.batudemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        TextView registerText = (TextView)findViewById(R.id.register_link);
        registerText.setOnClickListener(this);
        Button submitButton = (Button)findViewById(R.id.login_button);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_link:
                Intent next = new Intent(this, RegisterActivity.class);
                startActivity(next);
                break;
            case R.id.login_button:
                login();
                break;
        }
    }

    private class Call extends AsyncTask<RequestBody,Void,String> {
        @Override
        protected String doInBackground(RequestBody... requestBodies) {
            try {
               return MakeCall.post("http://192.168.88.2/batu/login.php", requestBodies[0], RegisterActivity.class.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("login");
                JSONObject object = jsonArray.getJSONObject(0);
                String id = object.getString("user_id");
                String name = object.getString("name");
                Toast.makeText(getApplicationContext(),"Name: "+name,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void login(){
        EditText emailBix = (EditText)findViewById(R.id.login_email);
        EditText passwordBox = (EditText)findViewById(R.id.login_password);

        String email = emailBix.getText().toString();
        String password = passwordBox.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        new Call().execute(requestBody);
    }

    @Override
    public void onBackPressed() {

    }
}
