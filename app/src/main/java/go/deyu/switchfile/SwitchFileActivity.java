package go.deyu.switchfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import go.deyu.switchfile.jni.Encrypt;

public class SwitchFileActivity extends ActionBarActivity {



    private final String TAG = getClass().getSimpleName();
    private String FilePath = "";
    private Encrypt e = new Encrypt();

    @InjectView(R.id.tv_file_path)TextView mPathTv;

    @InjectView(R.id.tv_status)TextView mStatusTv;

    @OnClick(R.id.btn_swith_file)
    void switchfile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String status = e.encryptfile(FilePath) == 0 ? "Success" : "Fail";
                runOnUiThread(new ShowTextViewRunalbe(mStatusTv , status));
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_activity_main);
        Intent intent = getIntent();
        FilePath = RealPathUtil.getRealPathFromURI_API11to18(this,intent.getData());
        if(TextUtils.isEmpty(FilePath))FilePath = intent.getData().getPath();
        ButterKnife.inject(this);
        mPathTv.setText(FilePath);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    class ShowTextViewRunalbe implements Runnable{
        private TextView tv;
        private String showmessage;
        public ShowTextViewRunalbe(TextView target , String message){
            tv = target;
            showmessage = message;
        }
        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            tv.setText(showmessage);
        }
    }
}
