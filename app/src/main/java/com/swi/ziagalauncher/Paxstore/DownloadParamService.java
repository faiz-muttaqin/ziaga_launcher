package com.swi.ziagalauncher.Paxstore;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pax.market.android.app.sdk.StoreSdk;
import com.pax.market.api.sdk.java.base.constant.ResultCode;
import com.pax.market.api.sdk.java.base.dto.DownloadResultObject;
import com.pax.market.api.sdk.java.base.exception.NotInitException;
import com.pax.market.api.sdk.java.base.exception.ParseXMLException;
import com.swi.ziagalauncher.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcy on 2016/12/2 0002.
 */
public class DownloadParamService extends IntentService {

    private static final String TAG = DownloadParamService.class.getSimpleName();
    public static String saveFilePath;
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SPUtil spUtil;
    //App Store
//    private static DownloadManager downloadManager;

    public DownloadParamService(){
        super("DownloadParamService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        spUtil=new SPUtil();
        //todo Specifies the download path for the parameter file, you can replace the path to your app's internal storage for security.
        saveFilePath = getFilesDir() + "/Download/";
//        saveFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "ParamEDC"+ File.separator;
        updateUI(DemoConstants.DOWNLOAD_STATUS_START);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //todo Call SDK API to download parameter files into your specific directory,
                DownloadResultObject downloadResult = null;
                try {
                    Log.i(TAG, "call sdk API to download parameter");
                    downloadResult = StoreSdk.getInstance().paramApi().downloadParamToPath(getApplication().getPackageName(), BuildConfig.VERSION_CODE, saveFilePath);
                    Log.i(TAG, downloadResult.toString());
                } catch (NotInitException e) {
                    Log.e(TAG, "e:" + e);
                }

//                businesscode==0, means download successful, if not equal to 0, please check the return message when need.
                if (downloadResult != null && downloadResult.getBusinessCode() == ResultCode.SUCCESS.getCode()) {
                    Log.i(TAG, "download successful.");

                    //todo start to add your own logic.
                    //below is only for demo
//                    readDataToDisplay();
                    updateUI(DemoConstants.DOWNLOAD_STATUS_SUCCESS);
                } else {
                    //todo check the Error Code and Error Message for fail reason
                    Log.e(TAG, "ErrorCode: "+downloadResult.getBusinessCode()+"ErrorMessage: "+downloadResult.getMessage());
                    //update download fail info in main page for Demo
                    spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_TITLE, DemoConstants.DOWNLOAD_FAILED);
                    spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_TEXT,"Your push parameters file task failed at "+ sdf.format(new Date())+", please check error log.");
                    updateUI(DemoConstants.DOWNLOAD_STATUS_FAILED);
                }

            }
        });
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void readDataToDisplay() {
        spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_TITLE, DemoConstants.DOWNLOAD_SUCCESS);

        // get specific display data resource <File>sys_cap.p</File>
        File parameterFile = getDisplayFile();

        //save data for display in main page for demo
        saveDisplayFileDataToSp(parameterFile);

        //update successful info in main page for Demo
        updateUI(DemoConstants.DOWNLOAD_STATUS_SUCCESS);
    }

    /**
     * save data for display in main page for demo
     * @param parameterFile data resource
     */
    private void saveDisplayFileDataToSp(File parameterFile) {
        if (parameterFile != null) {
            String bannerTextValue = "Your push parameters  - " + parameterFile.getName()
                    + " have been successfully pushed at " + sdf.format(new Date()) + ".";
            String bannerSubTextValue = "Files are stored in " + parameterFile.getPath();
            Log.i(TAG, "run=====: " + bannerTextValue);
            //save result for demo display
            spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_TEXT, bannerTextValue);
            spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_SUBTEXT, bannerSubTextValue);
            List<Map<String, Object>> datalist = getParameters(parameterFile);

            //save result for demo display
            spUtil.setDataList(DemoConstants.PUSH_RESULT_DETAIL, datalist);
        } else {
            Log.i(TAG, "parameterFile is null ");
            spUtil.setString(DemoConstants.PUSH_RESULT_BANNER_TEXT, "Download file not found. This demo only accept parameter file with name 'sys_cap.p'");
        }
    }

    /**
     * get specific display data resource <File>sys_cap.p</File>
     * @return specific file, return null if not exists
     */
    @Nullable
    private File getDisplayFile() {
        File parameterFile=null;
        File[] filelist = new File(saveFilePath).listFiles();
        if (filelist != null && filelist.length>0) {
            for (File f : filelist) {
                //todo Noted. this is for demo only, here hard code the xml name to "sys_cap.p". this demo will only parse with the specified file name
                if (DemoConstants.DOWNLOAD_PARAM_FILE_NAME.equals(f.getName())) {
                    parameterFile = f;
                }
            }

        }
        return parameterFile;
    }

    @NonNull
    private List<Map<String, Object>> getParameters(File parameterFile) {
        try {
            //parse the download parameter xml file for display.
            List<Map<String, Object>> datalist = new ArrayList<>();
            //todo call API to parse xml
            LinkedHashMap<String, String> resultMap = StoreSdk.getInstance().paramApi().parseDownloadParamXmlWithOrder(parameterFile);

            if (resultMap != null && resultMap.size() > 0) {
                //convert result map to list for ListView display.
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("label", entry.getKey());
                    map.put("value", entry.getValue());
                    datalist.add(map);
                }
            }
            return datalist;

        } catch (ParseXMLException e) {
            e.printStackTrace();
        } catch (NotInitException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * notify MainActivity to display downloaded files, just for demo display
     */
    private void updateUI(int stateCode) {
        Intent intent = new Intent(DemoConstants.UPDATE_VIEW_ACTION);
        intent.putExtra(DemoConstants.DOWNLOAD_RESULT_CODE, stateCode);
        sendBroadcast(intent);
    }

}

