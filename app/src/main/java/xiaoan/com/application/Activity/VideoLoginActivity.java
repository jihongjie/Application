package xiaoan.com.application.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import xiaoan.com.application.R;

/**
 * Created by An on 2017/7/2.
 */

public class VideoLoginActivity extends AppCompatActivity {

    private VideoView viewById;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_videol0ogin);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewById.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        viewById = (VideoView) findViewById(R.id.video_login);
        viewById.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mox));

        viewById.start();
        viewById.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewById.start();
            }
        });

        final Button btn = (Button) findViewById(R.id.video_login_sign);

        btn.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    btn.setBackgroundColor(Color.parseColor("#F918C4"));
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    btn.setBackgroundColor(Color.parseColor("#FF4081"));
                }
                return true;
            }
        });

    }
}
