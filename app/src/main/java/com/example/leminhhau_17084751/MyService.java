package com.example.leminhhau_17084751;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyService extends Service {
    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");

        myPlayer = new MyPlayer(this);
        binder = new MyBinder(); // do MyBinder được extends Binder

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.play();
        // trả về đối tượng binder cho ActivityMain
        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // ở đây mình demo phương thức tua bài hát
    public void fastForward(){

        myPlayer.fastForward(60000); // tua đến giây thứ 120
    }
    public void lowForward(){

        myPlayer.lowForward(-60000); // tua lùi giây thứ 120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }
    public boolean isPlaying(){
        return myPlayer.isPlaying();
    }
    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {

            return MyService.this;
        }
    }

    public void play(){
        myPlayer.play();
    }
    public void pause(){
        myPlayer.pause();
    }

}

// Xây dựng một đối tượng riêng để chơi nhạc
class MyPlayer {
    // đối tượng này giúp phát một bài nhạc
    public static MediaPlayer mediaPlayer;




    public MyPlayer(Context context) {

        // Nạp bài nhạc vào mediaPlayer
        mediaPlayer = MediaPlayer.create(
                context, R.raw.tinher);
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);
    }

    public void fastForward(int pos) {
        mediaPlayer.seekTo(pos);
        //mediaPlayer.pause();

    }

    public void lowForward(int pos) {
        mediaPlayer.seekTo(pos);
        //mediaPlayer.pause();

    }

    public void fastStart() {
        mediaPlayer.start();
    }

    // phát nhạc
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public static ArrayList<Music> getAllMusic(Context context) {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,//path in entity
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                Music music = new Music(path,title,artist,album,duration);
                //take log.e for check
                Log.e("Path",""+path);
                musicArrayList.add(music);
            }
            cursor.close();
        }
        return musicArrayList;
    }

}
