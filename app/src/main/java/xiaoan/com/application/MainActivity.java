package xiaoan.com.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import xiaoan.com.application.Activity.VideoLoginActivity;
import xiaoan.com.application.Activity.showProcessActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this,showProcessActivity.class));
        startActivity(new Intent(MainActivity.this,VideoLoginActivity.class));

    }
}
