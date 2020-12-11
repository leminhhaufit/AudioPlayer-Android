package com.example.leminhhau_17084751;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    SeekBar seekBar;
    private RecyclerView rclname;
    private NamelistAdapter namelistAdapter;
    private ArrayList<Music> nameArrayList;
    TextView tenbaihat, lbduration, lbposition;
    Runnable runnable;
    Handler handler;
    ImageView play, pause, hinhnen, stop, skippre, skipnext, tatam, laplai, ngaunhien, tuanguoc, tuatoi;

    //RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rclname = findViewById(R.id.recly);
        nameArrayList = new ArrayList<>();
        nameArrayList.add(new Music("tinher", "Về đâu mái tóc người thương - Quang Lê", "", "", "340"));
        nameArrayList.add(new Music("tinher", "Về đâu mái tóc người thương - Quang Lê", "", "", "340"));
        nameArrayList.add(new Music("tinher", "Về đâu mái tóc người thương - Quang Lê", "", "", "340"));
        nameArrayList.add(new Music("tinher", "Về đâu mái tóc người thương - Quang Lê", "", "", "340"));
        namelistAdapter = new NamelistAdapter(nameArrayList, this);
        rclname.setAdapter(namelistAdapter);
        rclname.setLayoutManager(new LinearLayoutManager(this));


        tenbaihat = findViewById(R.id.lbTen);
        lbduration = findViewById(R.id.lbDuration);
        lbposition = findViewById(R.id.lbPos);
        play = findViewById(R.id.btnplay);
        //pause = findViewById(R.id.btn);
        skippre = findViewById(R.id.btnSkipPre);
        skipnext = findViewById(R.id.btnSkipNext);
        tatam = findViewById(R.id.btnTatam);
        laplai = findViewById(R.id.btnRepeat);
        ngaunhien = findViewById(R.id.btnNgaunhien);
        tuanguoc = findViewById(R.id.btnTuanguoc);
        tuatoi = findViewById(R.id.btnTuatoi);
        seekBar = findViewById(R.id.seekBar);


        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder binder = (MyService.MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };







        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);
        bindService(intent, connection,
                Context.BIND_AUTO_CREATE);
        Toast.makeText(MainActivity.this,
                "Mở bài hát", Toast.LENGTH_SHORT).show();
        Log.d("Serveice", "Service dang mo");
        // Khởi tạo intent


       /* runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(MyPlayer.mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };
        int duration = MyPlayer.mediaPlayer.getDuration();
        String sduration = convertFormat(duration);
        lbduration.setText(sduration);*/

        /*ngaunhien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent =
                        new Intent(MainActivity.this,
                                MyService.class);
                // Bắt đầu một service sủ dụng bind
                bindService(intent, connection,
                        Context.BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this,
                        "Mở bài hát", Toast.LENGTH_SHORT).show();
                // Đối thứ ba báo rằng Service sẽ tự động khởi tạo
            }
        });*/

       /* stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if (isBound == true) {
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                    Toast.makeText(MainActivity.this,
                            "Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        skipnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua tới bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnplay).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isBound) {
                            // tua bài hát)
                            myService.fastStart();
                            Toast.makeText(MainActivity.this,
                                    "Mo bài hát", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    if (myService.isPlaying()) {
                        myService.pause();
                        play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        //handler.removeCallbacks(runnable);

                        Toast.makeText(MainActivity.this,
                                "Tạm dừng", Toast.LENGTH_SHORT).show();
                        Log.d("Play", "Nhac dang mo....");
                    } else {
                        myService.play();

                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                        seekBar.setMax(MyPlayer.mediaPlayer.getDuration());
                        //handler.postDelayed(runnable,0);

                        Toast.makeText(MainActivity.this,
                                "Tiếp tục", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tuatoi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua tơi bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tuanguoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.lowForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua lui bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private String convertFormat(int duration){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

}