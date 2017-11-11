package tw.com.abc.mycamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private ImageView img,img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                    },
                    0);
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }

    private void init(){
        img= (ImageView) findViewById(R.id.img);
        img2=(ImageView) findViewById(R.id.img2);



    }

    public void test1(View view) {
        // 呼叫外部相機，按確定後給Bitmap(不存檔)
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
        //startActivityForResult(intent,requestCode:1);
    }

   public void test2(View view) {


        //呼叫外部相機,透過URI存檔
       //http://www.jianshu.com/p/8ba7f2f16af9
       // https://developer.android.com/training/camera/photobasics.html#TaskPhotoView
       // API >= 24 以後的開發版本要用下面方式(FileProvider)
       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       File photoFile=null;
       
       // 上課有加try catch ,自己打卻沒錯,應該是之前的程式用到未刪除       
       
       // 要注意 Environment.DIRECTORY_PICTURES 會對應到XML檔中的路徑
       File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       photoFile = new File(storageDir,"brad4.jpg");
       /*
       * 開發版本 API >= 24 必須建立 XML目錄和XML檔案-file_paths.xml(可改名,需搭配AndroidManifest.xml中的設定)
         * 和於AndroidManifest.xml 中加入 <provieder> 宣告,定義檔案位置和專案名稱
       * */
       Uri photoURI = FileProvider.getUriForFile(this,"tw.com.abc.mycamera",photoFile);
       intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
       startActivityForResult(intent,2);






   }

    public void test3(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode== RESULT_OK){
                Log.i("geoff","OK");
                takepic(data);
            }else if(resultCode == RESULT_CANCELED){
                Log.i("geoff","XX");
            }
        }else if(requestCode ==2){
            if(resultCode== RESULT_OK){
                Log.i("geoff","OK");
                takepic2();
        }
    }

    private void takepic2() {
        // 在測試時卡關很久,後來只測呼叫相機,沒留意是否可以顯示,可能要改路徑

        File root = Environment.getExternalStorageDirectory();
        File save = new File(root, "brad2.jpg");
        Bitmap bmp = BitmapFactory.decodeFile(save.getAbsolutePath());
        img.setImageBitmap(bmp);

    }

    private void takepic(Intent data) {
        // 直接顯示相機傳回的縮圖
        Bitmap bmp=(Bitmap) data.getExtras().get("data");
        img.setImageBitmap(bmp);
        try {
            // 由相機存檔的資料作顯示
            //取得路徑
            File root = Environment.getExternalStorageDirectory();
            //存檔位置
            File save = new File(root,"brad.jpg");

            FileOutputStream fout = new FileOutputStream(save);
            bmp.compress(Bitmap.CompressFormat.JPEG,85,fout);

            //取出 brad.jpg
            FileInputStream fin = new FileInputStream(save);
            Bitmap bmp2 = BitmapFactory.decodeStream(fin);
            img2.setImageBitmap(bmp2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
