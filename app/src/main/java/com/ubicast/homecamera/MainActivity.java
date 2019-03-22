package com.ubicast.homecamera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    boolean isRecord = false;
    Camera camera;
    private Button startBtn;// 開始錄製按鈕
    private Button stopBtn;// 停止錄製按鈕
    private SurfaceView surfaceview;// 顯示視頻的控制項
    public MediaRecorder mediaRecorder;
    // 用來顯示視頻的一個介面，我靠不用還不行，也就是說用mediarecorder錄製視頻還得給個介面看
    // 想偷偷錄視頻的同學可以考慮別的辦法。。嗯需要實現這個介面的Callback介面
    public SurfaceHolder surfaceHolder;
    static int CAMERA_PERMISSION_REQUEST = 911;
    static int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 912;
    static int RECORD_AUDIO_PERMISSION_REQUEST = 913;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(!checkPermisson()){
       //     Toast.makeText(this,"Permission not accept", Toast.LENGTH_LONG).show();
            //return;
        //}
        //surfaceView = findViewById(R.id.surfaceView);
        //SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        //holder.addCallback(this); // holder加入回檔介面
        // setType必須設置，要不出錯.
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        /*
        mediaRecorder = new MediaRecorder();
        init(mediaRecorder);
        Toast.makeText(this, "init finish", Toast.LENGTH_LONG).show();
        try {
            mediaRecorder.prepare();
            //Set PreView
            mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
        }
*/
        init(new MediaRecorder());

    }

    private void init(MediaRecorder mediaRecorder ){

        startBtn = (Button) this.findViewById(R.id.startBtn);
        stopBtn = (Button) this.findViewById(R.id.stopBtn);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceView);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回檔介面
        // setType必須設置，要不出錯.
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        /*
        mediaRecorder.reset();
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        /*
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频输出格式为3gp  DEFAULT THREE_GPP
		mMediaRecorder.setVideoFrameRate(100);//每秒3帧
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//录制视频编码 264
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频编码为amr_nb   AMR_NB DEFAULT AAC



		mMediaRecorder.setVideoSize(640, 480);//设置录制视频尺寸     mWidth   mHeight
		mMediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024 );
		mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制




		mMediaRecorder.setOutputFile(mVecordFile.getAbsolutePath());//设置视频输出文件路径


		Log.e("wocaowocaowocao", mVecordFile.getAbsolutePath());
		//TODO
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
         * /

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        mediaRecorder.setVideoSize(320,480); //視頻大小
        mediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
        mediaRecorder.setVideoFrameRate(1);//per second to capture
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); //音頻格式
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263); //視頻格式

        File videoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".mp4");
        mediaRecorder.setOutputFile(videoFile.getPath());
        */


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startBtn:
                mediaRecorder = new MediaRecorder();// 創建mediarecorder物件
                // 設置錄製視頻源為Camera(相機)
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 設置錄製完成後視頻的封裝格式THREE_GPP為3gp.MPEG_4為mp4
                mediaRecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                // 設置錄製的視頻編碼h263 h264
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // 設置視頻錄製的解析度。必須放在設置編碼和格式的後面，否則報錯
                //mediaRecorder.setVideoSize(176, 144); //加了會StartFail：-19
                // 設置錄製的視頻幀率。必須放在設置編碼和格式的後面，否則報錯
                //mediaRecorder.setVideoFrameRate(20);//加了會StartFail：-19
                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                // 設置視頻檔輸出的路徑
                //mediaRecorder.setOutputFile("/sdcard/love.mp4");
                File videoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".mp4");
                mediaRecorder.setOutputFile(videoFile.getPath());
                Toast.makeText(this,"Start Record", Toast.LENGTH_LONG).show();
                try {
                    // 準備錄製
                    mediaRecorder.prepare();
                    // 開始錄製
                    mediaRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.stopBtn:
                if (mediaRecorder != null) {
                    // 停止錄製
                    mediaRecorder.stop();
                    // 釋放資源
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
                break;
        }
    }

    private boolean checkPermisson(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            return false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_REQUEST);
            return false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
            return false;
        }

        return true;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 將holder，這個holder為開始在oncreat裡面取得的holder，將它賦給surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 將holder，這個holder為開始在oncreat裡面取得的holder，將它賦給surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的時候同時物件設置為null
        surfaceview = null;
        surfaceHolder = null;
        mediaRecorder = null;
    }
}
