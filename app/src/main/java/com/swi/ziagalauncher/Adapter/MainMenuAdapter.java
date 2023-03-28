package com.swi.ziagalauncher.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swi.ziagalauncher.MainActivity;
import com.swi.ziagalauncher.Model.MainMenuModel;
import com.swi.ziagalauncher.Paxstore.DownloadManager;
import com.swi.ziagalauncher.R;
import com.swi.ziagalauncher.SessionManager;
import com.swi.ziagalauncher.api.Beep;
import com.swi.ziagalauncher.api.Device;
import com.swi.ziagalauncher.view.dialog.DialogUtils;
import com.swi.ziagalauncher.view.dialog.InputPwdDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


/**
 * Created by safemode on 04/09/19.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {

    private ArrayList<MainMenuModel> mDataset;
    private MainActivity context;
    Typeface typefaceSemiBold;
//    Dialog dialog;
    EditText editPassword;
    Beep beep;
    Device device;
    InputPwdDialog dialog = null;
    private boolean allowCanceledOnTouchOutside = true;
    SessionManager sessionManager;

    public MainMenuAdapter(ArrayList<MainMenuModel> myDataset, MainActivity context) {
        mDataset = myDataset;
        this.context = context;
        typefaceSemiBold = Typeface.createFromAsset(context.getAssets(), "eina-01-semibold.ttf");
        beep = new Beep(context);
        device = new Device(context);
        sessionManager = new SessionManager(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIconTitle;
        TextView tvTitle;
        LinearLayout llMenu;

        ViewHolder(View v) {
            super(v);
            ivIconTitle =  v.findViewById(R.id.iv_icon_title);
            tvTitle =  v.findViewById(R.id.tv_title);
            llMenu =  v.findViewById(R.id.ll_menu);
            tvTitle.setTypeface(typefaceSemiBold);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CONTENTS", mDataset.get(getAdapterPosition()).getTitle());
                    try {
                        String code = mDataset.get(getAdapterPosition()).getCode();
                        switch (code) {
                            case "SETTINGS":
                                dialog = new InputPwdDialog(context, 6, "Please Input Password", "");
                                setOnKeyListener();
                                setPwdListener();
                                setOnCancelListener();
                                dialog.show();
                                break;
                            default:
                                try{
                                    context.startActivity(context.getPackageManager().getLaunchIntentForPackage(mDataset.get(getAdapterPosition()).getAppPackage()));
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    beep.beepButtonWarn();
                                    DialogUtils.showErrMessage(context, "", "Application not found!", new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {

                                        }
                                    },1);
                                }
                                break;
                        }
                    }catch (Exception ex){}
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_menu, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MainMenuModel openMod = mDataset.get(position);
//        String appName = openMod.getTitle();
        holder.tvTitle.setText(openMod.getTitle());

        String filePath = DownloadManager.getInstance().getFilePath()+"icon"+position+".jpg";
        File file = new File(filePath);
        try {
            if(file.exists()){
                Drawable d = Drawable.createFromPath(filePath);
                if(openMod.getAppPackage() == "com.android.providers.settings"){
                    holder.ivIconTitle.setImageDrawable(context.getPackageManager().getApplicationIcon(openMod.getAppPackage()));
                }else {
                    holder.ivIconTitle.setImageDrawable(d);
                }
            }else{
                Resources resources = context.getResources();
                // Extract the last part of the package name
                String[] packageNameParts = openMod.getAppPackage().split("\\.");
                String lastPart =  packageNameParts[packageNameParts.length - 1];

                // Convert the last part to lowercase
                String lastPartLowercase = "zic_"+lastPart.toLowerCase();
                int resourceId = resources.getIdentifier(lastPartLowercase, "drawable", context.getPackageName());

                if (resourceId == 0) {
                    // Resource not found
//                    Log.e(TAG, "Drawable resource not found.");
                } else {                   // Resource found
                    Drawable drawable = resources.getDrawable(resourceId);
                    // Do something with the drawable
                    holder.ivIconTitle.setImageDrawable(drawable);
//                    holder.ivIconTitle
                }

//                holder.ivIconTitle.setImageDrawable(context.getPackageManager().getApplicationIcon(openMod.getAppPackage()));
//                saveAppIconByPackageName(context, openMod.getAppPackage(), appName);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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
                    DialogUtils.showSuccMessage(context,"Success",new DialogInterface.OnDismissListener() {
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
                    DialogUtils.showErrMessage(context, "", "Wrong password!", new DialogInterface.OnDismissListener() {
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

    public static void saveAppIconByPackageName(Context context, String packageName, String appName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Drawable appIcon = packageManager.getApplicationIcon(packageName);

            File directory = new File(Environment.getExternalStorageDirectory(), "Mei");
            if (!directory.exists()) {
               if (directory.mkdirs()){
                   Log.i("Icon", "Folder OK");
               }else Log.i("Icon", "Folder not created");
            }else Log.i("Icon", "Folder EXIST");

            // Extract the last part of the package name
            String[] packageNameParts = packageName.split("\\.");
            String lastPart = packageNameParts[packageNameParts.length - 1];

            // Convert the last part to lowercase
            String lastPartLowercase = lastPart.toLowerCase();
            File file = new File(directory, "zic_"+lastPartLowercase + ".png");
            FileOutputStream outputStream = new FileOutputStream(file);

            Bitmap bitmap = drawableToBitmap(appIcon);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

//            Toast.makeText(context, "App icon saved in " + file.getPath(), Toast.LENGTH_LONG).show();

            Log.i("Icon", "App icon saved in " + file.getPath());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Icon", "Failed to save app icon."+ e.getMessage());
//            Toast.makeText(context, "Failed to save app icon.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}

