package com.tianjin.beichentiyu.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.UploadImageResultsBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传图片
 */
public class UploadPictures {

    private static final String SD_PATH = "/sdcard/shuhua/pic/";
    private static final String IN_PATH = "/pic/";

    private Context context;
    private Uri uri;
    private String imagePath;
    private Callback callback;
    public UploadPictures(Context context,Uri uri){
        this.context = context;
        this.uri = uri;
    }

    /**
     * 设置回调
     * @param callback
     */
    public UploadPictures setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 上传图片
     */
    public void upload(){
        //压缩图片
        if (uri == null) {
            uploadedError("上传失败 图片原始路径为空");
            return;
        }
        Bitmap photoBmp = null;
        try {
            photoBmp = getBitmapFormUri(context, uri);
        } catch (IOException e) {
            e.printStackTrace( );
            uploadedError("上传失败 图片压缩异常:" + e.toString());
            return;
        }

        //保存图片到本地
        if(photoBmp == null) {
            uploadedError("上传失败 图片对象不存在");
            return;
        }
        imagePath = saveBitmap(context, photoBmp);

        //上传图片
        if (TextUtils.isEmpty(imagePath)) {
            uploadedError("上传失败 图片本地路径为空");
            return;
        }
        File file = new File(imagePath);
        if(!file.exists()){
            uploadedError("上传失败 图片不存在");
            return;
        }
        final RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        ApiManager.getBusinessService().uploadImg(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UploadImageResultsBean>() {
                    @Override
                    protected void onSuccees(UploadImageResultsBean loginBean) throws Exception {
                        if(loginBean.isSuccessful()){
                            uploadedSuccess(loginBean.getUrl(),imagePath);
                        }else{
                            uploadedError("上传失败 cdoe:"+loginBean.getCode()+" msg:" + loginBean.getMsg());
                        }
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        uploadedError("上传失败 Throwable:"+e.toString()+" isNetWorkError:" + isNetWorkError);
                    }
                });
    }

    /**
     * 图片上传成功
     * @param url
     * @param path
     */
    public void uploadedSuccess(String url,String path){
        if(callback != null){
            callback.uploadedSuccess(url,path);
        }
    }

    /**
     * 图片上传失败
     * @param msg
     */
    public void uploadedError(String msg){
        if(callback != null){
            callback.uploadedError(msg);
        }
    }

    public static Bitmap getBitmapFormUri(Context context, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
      /*  if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {//data/data/com.dasdad

        }*/
        savePath = context.getApplicationContext().getFilesDir().getAbsolutePath()+ IN_PATH;
        try {
            filePic = new File(savePath + generateFileName() + ".png");
            if(!filePic.getParentFile().getParentFile().exists()){
                filePic.getParentFile().getParentFile().mkdirs();
            }
            if(!filePic.getParentFile().exists()){
                filePic.getParentFile().mkdirs();
            }
            if (!filePic.exists()) {
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public interface Callback{
        /**
         * 上传图片成功
         * @param url   图片网络地址
         * @param path  图片本地路径
         */
        void uploadedSuccess(String url,String path);

        /**
         * 上传图片失败
         * @param msg 失败原因
         */
        void uploadedError(String msg);
    }
}
