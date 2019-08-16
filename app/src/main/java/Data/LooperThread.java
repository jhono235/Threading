package Data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LooperThread extends Thread {

    //Our handler for the Worker Thread
    public Handler workerThreadHandler;
    Handler mainThreadHandler;

    public static int fib( int n) {
        int prev1 = 0, prev2 = 1;
        for(int i = 0; i < n; i++) {
            int savePrev1 = prev1;
            prev1 = prev2;
            prev2 = savePrev1 + prev2;
        }
        return prev1;
    }

    public static String getFib(int n) {
        StringBuilder sb = new StringBuilder(fib(0));
        for (int i = 1; i <= n; ++i) {
            sb.append(", ");
            sb.append(fib(i));
        }
        return sb.toString();
    }

    public LooperThread(Handler handler){
        super();
        mainThreadHandler = handler;
        workerThreadHandler = new android.os.Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {
                // When child thread handler get message from child thread message queue.
                Log.i("CHILD_THREAD", "Receive message from main thread.");
                Message message = new Message();
                message.what = msg.what;


                //put data in the message
                Bundle bundle = new Bundle();
                bundle.putString("key", ""+ getFib(20));
                message.setData(bundle);
                // Send the message back to main thread message queue use main thread message Handler.
                mainThreadHandler.sendMessage(message);
            }
        };
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        Looper.loop();
    }
}
