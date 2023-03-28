package com.swi.ziagalauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.pax.dal.IDAL;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.swi.ziagalauncher.Adapter.MainMenuAdapter;
import com.swi.ziagalauncher.Model.MainMenuModel;
import com.swi.ziagalauncher.Paxstore.DemoConstants;
import com.swi.ziagalauncher.Paxstore.DownloadManager;
import com.swi.ziagalauncher.api.Beep;
import com.swi.ziagalauncher.api.Device;
import com.swi.ziagalauncher.controller.AppLauncherDocument;
import com.swi.ziagalauncher.view.dialog.DialogUtils;
import com.swi.ziagalauncher.view.dialog.InputPwdDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    Beep beep;
    Device device;
    SessionManager sessionManager;
    String serialNumber;
    IDAL dal;
    DownloadManager downloadManager;
    String XML_PATH = "ziagalauncher.xml";
    private MsgReceiver msgReceiver;

    protected RecyclerView rvGrid;
    protected GridLayoutManager gridLayoutManager;
    ArrayList<MainMenuModel> listMenu = new ArrayList<>();
    int INTERVAL;
    private View myView = null;
    int i = 0;
    Thread bg = null;

    ImageView settingImage;
    InputPwdDialog dialog = null;
    private boolean allowCanceledOnTouchOutside = true;
    TextView textViewTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingImage = findViewById(R.id.settingImage);
        textViewTime = findViewById(R.id.textViewTime);

        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);

//                dialog = new InputPwdDialog(MainActivity.this, 6, "Please Input Password", "");
//                setOnKeyListener();
//                setPwdListener();
//                setOnCancelListener();
//                dialog.show();
            }
        });

        sessionManager = new SessionManager(this);
        beep = new Beep(this);
        device = new Device(this);
        device.enableHomeAndRecentKey(false);
        device.enableStatusBar(false);

        //receiver to get UI update.
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DemoConstants.UPDATE_VIEW_ACTION);
        registerReceiver(msgReceiver, intentFilter);
        try {
            dal = NeptuneLiteUser.getInstance().getDal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        serialNumber = dal.getSys().getTermInfo().get(ETermInfoKey.SN);
        INTERVAL = sessionManager.getAdsTime();
        downloadParamXml();

        initView();
        setEvent();
//        updateBg();
        final Handler handlerTime = new Handler(Looper.getMainLooper());

        // Create a Runnable object to update the time
        Runnable updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                // Create a SimpleDateFormat object to format the time
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                // Get the current time as a Date object
                Date now = new Date();

                // Format the time as a string
                String formattedTime = dateFormat.format(now);

                // Set the text of the TextView to the formatted time
                textViewTime.setText(formattedTime);

                // Schedule the task to run again after 1 second
                handlerTime.postDelayed(this, 1000);
            }
        };

        // Schedule the task to run after 1 second
        handlerTime.postDelayed(updateTimeRunnable, 1000);

        // Get the system UI manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }

    private void updateBg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String filePath = DownloadManager.getInstance().getFilePath()+i+".jpg";
                File file = new File(filePath);
                if(file.exists()) {
                    Drawable d = Drawable.createFromPath(filePath);
                    myView.setBackground(d);
                }
                i++;
                if (i == sessionManager.getTotalAds()) {
                    i = 0;
                }
            }
        });
    }

    private void initView() {
        rvGrid = findViewById(R.id.rv_grid);
        rvGrid.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
        rvGrid.setLayoutManager(gridLayoutManager);
        sessionManager = new SessionManager(this);

        myView =findViewById(R.id.myview);
    }

    private void setEvent() {
        listMenu.clear();
        listMenu = new ArrayList<>();
        if (true) {
//            JsonObject jsonObject = ConvertStringJsonHelper.convertToJsonObject(sessionManager.getMenuApp());
//            JsonArray data = jsonObject.get("menu").getAsJsonArray();
            List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(0);
            for (ApplicationInfo appInfo : apps) {
                if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    // This is a non-system app
                    if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 ||
                            (appInfo.flags & ApplicationInfo.FLAG_INSTALLED) != 0) {
                        // This is an app that can be opened
                        String appName = appInfo.loadLabel(getPackageManager()).toString();
                        String packageName = appInfo.packageName;
                        Log.d("App Info", "App Name: " + appName + " Package Name: " + packageName);

                        MainMenuModel model = new MainMenuModel();
                        model.setCode("app");
                        model.setTitle(appName);
                        model.setAppPackage(packageName);

                        listMenu.add(model);
                    }
                }
            }

//            List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(0);
//            for (ApplicationInfo appInfo : apps) {
//                String appName = appInfo.loadLabel(getPackageManager()).toString();
//                String packageName = appInfo.packageName;
//                Log.d("App Info", "App Name: " + appName + " Package Name: " + packageName);
//
//                MainMenuModel model = new MainMenuModel();
//                model.setCode("app");
//                model.setTitle(appName);
//                model.setAppPackage(packageName);
//
//                listMenu.add(model);
//            }

//            for (int i = 0; i < data.size(); i++) {
//                MainMenuModel model = new MainMenuModel();
//                model.setCode("app");
//                model.setTitle(data.get(i).getAsJsonObject().get("name").getAsString());
//                model.setAppPackage(data.get(i).getAsJsonObject().get("package").getAsString());
//                listMenu.add(model);
//            }
        }
//        MainMenuModel model = new MainMenuModel();
//        model.setCode("SETTINGS");
//        model.setTitle("SETTINGS");
//        model.setAppPackage("com.android.providers.settings");
////        model.setIcon();
//        listMenu.add(model);

        MainMenuAdapter mAdapter = new MainMenuAdapter(listMenu, MainActivity.this);
        rvGrid.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if (bg!=null){
            bg.interrupt();
        }
        bg = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(INTERVAL);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateBg();
                }
            }
        });
        bg.start();
        updateBg();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            // do nothing
            Toast.makeText(this, "tekan", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onResume() {
        super.onResume();
        downloadParamXml();
        device.enableHomeAndRecentKey(false);
        device.enableStatusBar(false);
        Runnable runnable = new CountDownRunner();
        Thread clockThread = new Thread(runnable);
        clockThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);
        super.onDestroy();
        device.enableHomeAndRecentKey(true);
        device.enableHomeAndRecentKey(true);
    }

    @Override
    public void onBackPressed() {
        beep.beepButton();
    }

    public void downloadParamXml(){
        downloadManager = DownloadManager.getInstance();
        downloadManager.setFilePath(getFilesDir() + "/Download/");
        downloadManager.addDocument(new AppLauncherDocument(XML_PATH, MainActivity.this));
    }

    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //update main page UI for push status
            int resultCode = intent.getIntExtra(DemoConstants.DOWNLOAD_RESULT_CODE, 0);
            switch (resultCode){
                case DemoConstants.DOWNLOAD_STATUS_SUCCESS:
                    Toast.makeText(MainActivity.this,DemoConstants.DOWNLOAD_SUCCESS,Toast.LENGTH_SHORT).show();
                    break;
                case DemoConstants.DOWNLOAD_STATUS_START:
                    Toast.makeText(MainActivity.this,DemoConstants.DOWNLOAD_START,Toast.LENGTH_SHORT).show();
                    break;
                case DemoConstants.DOWNLOAD_STATUS_FAILED:
                    Toast.makeText(MainActivity.this,DemoConstants.DOWNLOAD_FAILED,Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    File file = new File(downloadManager.getFilePath()+XML_PATH);
                    if(file.exists()) {
                        downloadManager.updateData(MainActivity.this);
                        INTERVAL = sessionManager.getAdsTime();
                        setEvent();
                        Log.i("PR", "update Data");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void setOnKeyListener() {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    dialog.dismiss();
                }
                return false;
            }
        });
        dialog.setCancelable(false);
    }

    private void setPwdListener() {
        dialog.setPwdListener(new InputPwdDialog.OnPwdListener() {
            @Override
            public void onSucc(String data) {
                if(data.equals(sessionManager.getPassword())){
                    beep.beepSuccess();
                    DialogUtils.showSuccMessage(MainActivity.this,"Success",new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            device.enableStatusBar(true);
                            device.enableHomeAndRecentKey(true);
                            dialog.dismiss();
                            System.exit(0);
                        }
                    },1);
                } else {
                    beep.beepButtonWarn();
                    /*Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show();
                    editPassword.setText("");*/
                    DialogUtils.showErrMessage(MainActivity.this, "", "Wrong password!", new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    },1);
                }
                dialog.dismiss();
            }

            @Override
            public void onErr() {
                dialog.dismiss();
            }
        });
    }
    private void setOnCancelListener() {
        //AET-50
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(allowCanceledOnTouchOutside); // AET-17
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_1:
                        dialog = new InputPwdDialog(MainActivity.this, 6, "Please Input Password", "");
                        setOnKeyListener();
                        setPwdListener();
                        setOnCancelListener();
                        dialog.show();
                        return true;
//                    case R.id.item_2:
//                        // Handle item 2
//                        return true;
//                    case R.id.item_3:
//                        // Handle item 3
//                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
}