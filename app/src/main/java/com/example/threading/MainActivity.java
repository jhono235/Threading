package com.example.threading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

import Data.Async;
import Data.LooperThread;

public class MainActivity extends AppCompatActivity implements Async.AsyncCallback {
    TextView tvPi, tvLooper;
    Async asyncTask;
    int[] dataArray;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPi = findViewById(R.id.tvPi);
        tvLooper = findViewById(R.id.tvLooper);

        //Looper
        LooperThread looperThread;
        //instantiate the looper, and handle results from the looper to the main Looper
        looperThread = new LooperThread(new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //get info back out of message received
                Bundle bundle = msg.getData();
                tvLooper.setText(bundle.getString("key"));
            }
        });
        looperThread.start();
        looperThread.workerThreadHandler.sendMessage(new Message());




        Thread thread = new Thread(runnable());
        tvPi.setText("Calculating Pi...");

        thread.start();
    }



    DecimalFormat numberFormat = new DecimalFormat("#.000000000000");
    double number = Math.PI;


    public Runnable runnable() {
        Runnable returnRunnable = new Runnable() {
            @Override
            public void run() {


                try {
                    Thread.sleep(10000);
                    tvPi.setText(""+ numberFormat.format(number) );
                   // Log.d("TAG", "Thread 1 is done napping");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return returnRunnable;
    }




    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btnGoToAsync:
                asyncTask = new Async(this);
                asyncTask.execute("Async Task");
                break;



        }

    }

    @Override
    public void returnString(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public class Async extends AsyncTask<String, String, String> {
        Data.Async.AsyncCallback asyncCallback;
        TextView tvAsync;



        public Async(Data.Async.AsyncCallback asyncCallback) {

            this.asyncCallback = asyncCallback;
        }

        @Override
        protected void onPreExecute() {


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Random random = new Random(1L);

            // create an int array of 1000 elements
            dataArray = new int[1000];

            // populate the array with randomly generated values
            for (int index = 0; index < 1000; index++) {
                dataArray[index] = random.nextInt(1000);
            }

            Arrays.sort(dataArray);


            for (i = 0; i <= dataArray.length-1; i++) {
                {





                }
                strings[0] = "Run Number: " + dataArray[i];
                publishProgress(String.valueOf(dataArray[i]));
            }

            return strings[strings.length - 1];
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // Log.d("TAG", "onProgressUpdate: " + values[0])
            tvAsync = findViewById(R.id.tvAsync);
            tvAsync.setText("onProgressUpdate: " + values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //EventBus.getDefault().post(new MessagingEvent(s));
            asyncCallback.returnString(s);
        }





    }


}
