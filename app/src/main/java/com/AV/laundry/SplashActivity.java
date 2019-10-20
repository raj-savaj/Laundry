package com.AV.laundry;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.github.florent37.viewanimator.ViewAnimator;
import android.view.animation.LinearInterpolator;
public class SplashActivity extends AppCompatActivity {

    Handler h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView=findViewById(R.id.logosplash);
        ViewAnimator
                .animate(imageView)
                .scale(0,1)
                .rotation(360)
                .start();
        h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },5000);

    }
}
