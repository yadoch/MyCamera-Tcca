package tw.com.abc.mycamera;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class PreViewActivity extends AppCompatActivity {
    private SurfaceView surfaceview;
    private SurfaceHolder surfacehorder;
    private Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_view);
        //高速影像處理時需透過surfaceview 來處理
        surfaceview =(SurfaceView) findViewById(R.id.preview);
        surfacehorder=surfaceview.getHolder();

        surfacehorder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfacehorder.addCallback(new MyCallBack());


    }

    private class MyCallBack implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            initCamera();
        }


        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if(camera != null){
                camera.release();
            }
        }
    }
    private void initCamera() {
        camera= Camera.open();
        if (camera != null){Camera.Parameters param = camera.getParameters();
            List<Camera.Size> sizes=
        }



    }

}
