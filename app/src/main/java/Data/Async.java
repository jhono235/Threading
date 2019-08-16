package Data;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.threading.R;

import java.util.Arrays;
import java.util.Random;

public class Async extends AsyncTask<String, String, String> {
    AsyncCallback asyncCallback;
    TextView tvAsync;



    public Async(AsyncCallback asyncCallback) {

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
        int[] dataArray = new int[1000];

        // populate the array with randomly generated values
        for (int index = 0; index < 1000; index++) {
            dataArray[index] = random.nextInt(1000);
        }

        Arrays.sort(dataArray);


        for (int i = 0; i <= dataArray.length-1; i++) {
            {


                 Log.d("TAG", ""+dataArray[i]);


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


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //EventBus.getDefault().post(new MessagingEvent(s));
        asyncCallback.returnString(s);
    }



    public interface AsyncCallback{
        void returnString(String string);
    }

}
