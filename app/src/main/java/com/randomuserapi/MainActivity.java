package com.randomuserapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv_name, tv_email, tv_gender, tv_birthdate, tv_age;
    ImageView iv_user_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_gender = findViewById(R.id.tv_gender);
        tv_birthdate = findViewById(R.id.tv_birthdate);
        tv_age = findViewById(R.id.tv_age);
        iv_user_picture = findViewById(R.id.iv_user_picture);

        findViewById(R.id.btn_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask nt = new NetworkTask();
                nt.execute();
            }
        });
    }


    class NetworkTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL("https://randomuser.me/api/");
                InputStream is = url.openStream();
                byte[] buffer = new byte[4096];
                StringBuilder sb = new StringBuilder("");


                while (is.read(buffer) != -1) {
                    sb.append(new String(buffer));
                }

                Log.i("apiresponse", sb.toString());

                try {
                    JSONObject obj = new JSONObject(sb.toString());
                    JSONArray results = obj.getJSONArray("results");
                    JSONObject user = results.getJSONObject(0);
                    JSONObject nameObj = user.getJSONObject("name");

                    String name = nameObj.getString("title") + ". " + nameObj.getString("first") + " " + nameObj.getString("last");
                    String email = user.getString("email");
                    String gender = user.getString("gender");
                    String image_url = user.getJSONObject("picture").getString("medium");
                    String birthdate = user.getJSONObject("dob").getString("date");
                    String age = user.getJSONObject("dob").getString("age");

                    Person result =  new Person(name, email, gender, image_url, birthdate, age);
                    publishProgress(result);


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            Person person = (Person) values[0];
            tv_name.setText("Name: " +  person.getName());
            tv_email.setText("Email: " + person.getEmail());
            tv_gender.setText("Gender: " + person.getGender());
            Picasso.get().load(person.getImage_url()).into(iv_user_picture);
            tv_birthdate.setText("Date: " + person.getBirthdate());
            tv_age.setText("Age: " + person.getAge());
        }
    }
}

