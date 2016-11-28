package output_apk.tamic.com.apkdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    private Button btnUrl, btnPackName, btnChannel, btnModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // UI init
        btnChannel = (Button) findViewById(R.id.channel);
        btnUrl = (Button) findViewById(R.id.url);
        btnPackName = (Button) findViewById(R.id.packageId);
        btnModule = (Button) findViewById(R.id.module);

        // action
        btnModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(BuildConfig.FLAVOR.equals("cdev")+"");

                if (BuildConfig.DEBUG) {

                }
                try {
                    Class.forName("com.tamic.novate.Novate");
                    showToast("===========");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
                if (BuildConfig.FLAVOR.equals("cdev")) {
                    try {
                        Class.forName("com.tamic.novate.Novate");

                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("ip", "21.22.11.33");
                        Novate novate = new Novate.Builder(MainActivity.this)
                                //.addParameters(parameters)
                                .connectTimeout(5)
                                .baseUrl("http://ip.taobao.com/")
                                .addLog(true)
                                .build();


                        novate.get("service/getIpInfo.php", parameters, new BaseSubscriber<ResponseBody>(MainActivity.this) {
                            @Override
                            public void onStart() {

                                // todo onStart

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {

                                try {
                                    showToast(responseBody.bytes().toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }



                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }



                        });

                    } catch (ClassNotFoundException ignored) {

                    }

                }

            }
        });

        btnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(BuildConfig.FLAVOR);
            }
        });

        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(BuildConfig.HOST_URL);
            }
        });
        btnPackName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(BuildConfig.APPLICATION_ID);
            }
        });

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


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
