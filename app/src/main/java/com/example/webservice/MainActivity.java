package com.example.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        b1 = findViewById(R.id.button1);
    }

    class ArkaPlan extends AsyncTask<String,String,String>{
        //Kaç tane parametre olduğunu bilmediğimiz zaman ... şeklinde yazıyoruz, param dizisi içinde döndürülecek.
        protected String doInBackground(String ... params){
            //Metodun ilk elemanı sunucu adresi URL
            //Bir object referrer, pointer oluşturuyoruz
            HttpURLConnection connection = null;
            BufferedReader br = null;

            try{
                URL url = new URL(params[0]);
                //URL i artık biliyoruz. Bağlantı bilgilerini yazacağız.
                connection = (HttpURLConnection) url.openConnection();
                //bağlanacağız
                connection.connect();
                //Buffered reader doğrudan connection'a bağlanamıyor. Araya birkaç stream eklenmeli(boru)
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                //URL>HTTPURLCONNECTION>INPUTSTREAM>INPUTSTREAMREADER>BUFFEREDREADER //// SIRAYLA BAĞLADIK
                String line;
                String dosya="";
                //Sıradaki satırda bir şeyler yazdığı sürece
                while((line=br.readLine())!=null){
                    Log.d("line: " ,line);
                    dosya += line;


                }


            }catch (Exception e){
                e.printStackTrace();
            }
            return "hata";
        }
    }

    protected void onPostExecute(String s){
        Log.d("postExecutetan gelen", s);
        try{
            JSONObject jo = new JSONObject(s);
            tv.setText(jo.getString("k1"));
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public void make(View v){
        tv.setText("sunucu cevabı");
        new ArkaPlan().execute("localhost/dosya");

    }

}
