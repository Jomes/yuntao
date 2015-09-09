package com.yuntao.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.Constants;
import com.yuntao.MyApplication;
import com.yuntao.R;
import com.yuntao.base.BaseFragment;
import com.yuntao.iter.OnBindAndAppoinmentListener;
import com.yuntao.mode.BindReslut;
import com.yuntao.utils.PreferenceManager;
import com.yuntao.utils.TitleHelperUtils;
import com.yuntao.widget.FileUpload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jomeslu on 2015/3/5.
 */
public class LoginFragmentWebView extends BaseFragment implements OnBindAndAppoinmentListener {

    private WebView mWebView;
    private String oldUid;

    private String urlSuccess = "http://m.yyyt.com";// 正式环境
    //http://m.yyyt.com/passport/login
    private String defaultLoginUrl = urlSuccess + "/passport/login?type=android";
    private SimpleProgressDialog mProgressDialog;
    public final static int FILECHOOSER_RESULTCODE = 1;
    private static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        initDatas();
        View view = View.inflate(mContext, R.layout.activity_login_web, null);
        mProgressDialog = new SimpleProgressDialog(mContext,
                R.style.myProgressdialog);
        iniCookes();
        initView(view);
        initTitle(view);
        initData(defaultLoginUrl);

        return view;
    }

    private void initDatas() {
        MyApplication.getInstance().registBindAndAppoinmentListener(this);
        if (getArguments() != null) {
            defaultLoginUrl = getArguments().getString("url");

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApplication.getInstance().unRegisterBindAndAppoinmentListener(this);
    }

    @Override
    protected void initTitleView(TitleHelperUtils mTitleHelper) {
        mTitleHelper.setLeftOnClickLisenter(this);
        mTitleHelper.setLeftText("登录");

    }


    public void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.login_webview);
        mWebView.requestFocus();
        mWebView.setInitialScale(100);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            mWebView.getSettings().setDisplayZoomControls(false);
        }
        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        mWebView.setWebChromeClient(new WebChromeClientImpl());
        mWebView.setWebViewClient(new ViewClient());


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (!TextUtils.isEmpty(cookes))
            cookieManager.setCookie(urlSuccess, cookes);//cookies是在HttpClient中获得的cookie

    }

    private boolean isLoading = false;

    @Override
    public void onBindResult(BindReslut reslut, int mode) {

        if (mode == Constants.EVENT_PUSH_RECOMMOD) {
            if (reslut != null) {
                mWebView.loadUrl(reslut.getUrl());
            }

        } else if (mode == Constants.EVENT_PUSH_LOGOUT) {

            LogUtils.i("退出！！！！！！");
            // 退出登录
            isSaved = false;
            String preToken = PreferenceManager.getInstance(mContext).getStringData(Constants.pre_token, "");
            PushManager.getInstance().unBindAlias(mContext, preToken, true);
            PreferenceManager.getInstance(mContext).saveData(Constants.pre_token, "");
        }


    }

    private class ViewClient extends WebViewClient {

        public ViewClient() {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            LogUtils.i("onPageFinished__" + url);
            super.onPageFinished(view, url);
            mWebView.getSettings().setBlockNetworkImage(false);
            isLoading = false;
            String cookes = CookieManager.getInstance().getCookie(url);
            saveCookes(cookes);
            mProgressDialog.dismiss();


        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.e("onPageStarted" + url);
            super.onPageStarted(view, url, favicon);
            if (!mContext.isFinishing())
                mProgressDialog.show();
            if (!isLoading) {
                isLoading = true;
                String cookes = CookieManager.getInstance().getCookie(url);
                LogUtils.e("onPageStarted:" + cookes);
                saveCookes(cookes);

            }

            // onRefresh();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (mWebView != null) {
                mWebView.stopLoading();
            }
            mProgressDialog.dismiss();
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    private void initData(String url) {
        mWebView.loadUrl(url);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        CookieManager.getInstance().removeAllCookie();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean isHasCookies(String cookes) {

        if (TextUtils.isEmpty(cookes))
            return false;
        return cookes.contains("ASPXAUTH") ? true : false;

    }


    /**
     * **只保存一次**
     */
    private boolean isSaved = false;

    private void saveCookes(String cookes) {
        if (!isSaved) {
            if (isHasCookies(cookes)) {
                isSaved = true;
                PreferenceManager.getInstance(mContext).saveData(Constants.pre_cookies, cookes);
                String token = getToken(cookes);
                if (token.indexOf("=") == 0) {
                    token = token.substring(1, token.length());
                }
                PreferenceManager.getInstance(mContext).saveData(Constants.pre_token, token);
                MyApplication.getInstance().setAlia();
                MyApplication.getInstance().bindleInfo();
            }

        }

    }


    private String key = "yyytcode=";

    private String getToken(String cookies) {
        if (!cookies.contains(";"))
            return "";
        String[] split = cookies.split(";");
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            if (str.contains(key)) {
                return str.substring(key.length(), str.length());
            }

        }
        return "";

    }

    private String cookes = "";

    /**
     * 初始话Cookes
     */
    private void iniCookes() {
        cookes = PreferenceManager.getInstance(mContext).getStringData(Constants.pre_cookies, "");
    }


    //----------------------------------------------------------

    private ValueCallback<Uri[]> mFilePathCallback;
    public ValueCallback<Uri> mUploadMessage;
    public static final int INPUT_FILE_REQUEST_CODE = 11;
    private String mCameraPhotoPath;

    private class WebChromeClientImpl extends WebChromeClient {

        //扩展支持alert事件
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("一元云淘").setMessage(message).setPositiveButton("确定", null);
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_launcher);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();
            return true;
        }

        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePathCallback;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
//                    Log.e(TAG, "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;
        }


        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (mUploadMessage != null) return;
            mUploadMessage = uploadMsg;
            selectImage();
//               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//               i.addCategory(Intent.CATEGORY_OPENABLE);
//               i.setType("*/*");
//                   startActivityForResult( Intent.createChooser( i, "File Chooser" ), FILECHOOSER_RESULTCODE );
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }


    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


//        if(resultCode==REQ_CAMERA||resultCode==REQ_CHOOSE){
//
//            if (null == mUploadMessage)
//                return;
//            Uri uri = null;
//            if (requestCode == REQ_CAMERA) {
//                afterOpenCamera();
//                uri = cameraUri;
//            } else if (requestCode == REQ_CHOOSE) {
//                uri = afterChosePic(data);
//            }
//            mUploadMessage.onReceiveValue(uri);
//            mUploadMessage = null;
//            super.onActivityResult(requestCode, resultCode, data);
//
//        }else {


        if (requestCode == INPUT_FILE_REQUEST_CODE) {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
            return;

        } else {
            if (null == mUploadMessage)
                return;
            Uri uri = null;
            if (requestCode == REQ_CAMERA) {
                afterOpenCamera();
                uri = cameraUri;
            } else if (requestCode == REQ_CHOOSE) {
                uri = afterChosePic(data);
            }
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
            super.onActivityResult(requestCode, resultCode, data);


        }


//        }


    }


    //------------------------------5.0一下的兼容----
    String imagePaths;
    Uri cameraUri;
    private String compressPath = "";

    /**
     * 打开照相机
     */
    private void openCarcme() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imagePaths = Environment.getExternalStorageDirectory().getPath()
                + "/fuiou_wmp/temp/"
                + (System.currentTimeMillis() + ".jpg");
        // 必须确保文件夹路径存在，否则拍照后无法完成回调
        File vFile = new File(imagePaths);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        cameraUri = Uri.fromFile(vFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, REQ_CAMERA);
    }

    /**
     * 拍照结束后
     */
    private void afterOpenCamera() {
        File f = new File(imagePaths);
        addImageGallery(f);
        File newFile = FileUpload.compressFile(f.getPath(), compressPath);
    }

    /**
     * 解决拍照后在相册中找不到的问题
     */
    private void addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        mContext.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 本地相册选择图片
     */
    private void chosePic() {
        FileUpload.delFile(compressPath);
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    /**
     * 选择照片后结束
     *
     * @param data
     */
    private Uri afterChosePic(Intent data) {

        if (data == null)
            return null;

        // 获取图片的路径：
        String[] proj = {MediaStore.Images.Media.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = mContext.managedQuery(data.getData(), proj, null, null, null);
        if (cursor == null) {
            showToast("上传的图片仅支持png或jpg格式");
            return null;
        }
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") || path.endsWith(".JPG"))) {
            File newFile = FileUpload.compressFile(path, compressPath);
            return Uri.fromFile(newFile);
        } else {
            showToast("上传的图片仅支持png或jpg格式");
        }
        return null;
    }

    protected final void selectImage() {
        if (!checkSDcard())
            return;
        String[] selectPicTypeStr = {"camera", "photo"};
        new AlertDialog.Builder(mContext)
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    // 相机拍摄
                                    case 0:
                                        openCarcme();
                                        break;
                                    // 手机相册
                                    case 1:
                                        chosePic();
                                        break;
                                    default:
                                        break;
                                }
                                compressPath = Environment
                                        .getExternalStorageDirectory()
                                        .getPath()
                                        + "/fuiou_wmp/temp";
                                new File(compressPath).mkdirs();
                                compressPath = compressPath + File.separator
                                        + "compress.jpg";
                            }
                        }).show();
    }

    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public final boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
            showToast("请插入手机存储卡再使用本功能");
        }
        return flag;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.title_left:
                finishCurrent();
                break;

        }

    }
}
