package com.swi.ziagalauncher.api;

import android.content.Context;

import com.pax.dal.ISys;

/**
 * Created by Ditya Geraldy on 28 February 2019.
 */

public class Device {

    private ISys iSys;

    public Device(Context context) {
        try {
//            IDAL dal = NeptuneLiteUser.getInstance().getDal(context);
//            this.iSys = dal.getSys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableHomeAndRecentKey(boolean enable) {
        //untuk lock/unlock tombol home dan recent
//        iSys.enableNavigationKey(ENavigationKey.HOME, enable);
//        iSys.enableNavigationKey(ENavigationKey.RECENT, enable);
//        iSys.enableNavigationKey(ENavigationKey.BACK, true);
    }

    public void enableStatusBar(boolean enable) {
        //untuk lock/unlock status bar
//        iSys.enableStatusBar(enable);
    }
}