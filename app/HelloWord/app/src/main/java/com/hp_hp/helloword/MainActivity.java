package com.hp_hp.helloword;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Runnable{

    private TextView textView;
    private Button wtButton1;
    private Button wtButton2;
    private Button wtButton3;
    private int count = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wtButton1 = (Button)findViewById(R.id.wtButton1);
        wtButton2 = (Button) findViewById(R.id.wtButton2);
        wtButton3 = (Button) findViewById(R.id.wtButton3);
        textView = (TextView)findViewById(R.id.wtTextView);
        wtButton1.setOnClickListener(this);
        wtButton2.setOnClickListener(this);
        handler = new Handler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        textView.setText("count :"+(++count));
        handler.postDelayed(this,5000);
    }

    public class NoThreadHandler implements Runnable{


        @Override
        public void run() {
            try{
                textView.setText("没有用Handler更新UI");
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
    public class ThreadHandler implements Runnable{
        private Handler mHandler;
        public ThreadHandler(Handler handler){
            mHandler = handler;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.obj = "wt i love you forever";
            msg.arg1 = 23;
            mHandler.sendMessage(msg);
        }
    }


    Handler handlere = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String str = String.valueOf(msg.obj);
            textView.setText(msg.arg1 + "wt"+str);
        }
    };
    class RunToast implements Runnable{
        private Context context;
        public RunToast(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            Toast.makeText(context,"wt",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wtButton1:
//                Thread thread = new Thread(new ThreadHandler(handler));
//                thread.start();
//                Handler handler = new Handler();
//                handler.post(thread);
                handler.postDelayed(this,5000);
                break;
            case R.id.wtButton2:
//                Thread mareah = new Thread(new NoThreadHandler());
//                mareah.start();
                handler.postAtTime(new RunToast(this){},android.os.SystemClock.uptimeMillis()+10*1000);
                break;
            case R.id.wtButton3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
            default:
                break;
        }

    }
}
