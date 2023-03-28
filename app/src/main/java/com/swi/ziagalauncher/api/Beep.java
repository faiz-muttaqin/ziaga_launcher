package com.swi.ziagalauncher.api;

import android.content.Context;

import com.pax.dal.IDAL;
import com.pax.dal.ISys;
import com.pax.dal.entity.EBeepMode;
import com.pax.neptunelite.api.NeptuneLiteUser;

/**
 * Created by Ditya Geraldy on 19 March 2018.
 */

public class Beep {

    private ISys sys;

    public Beep(Context context) {
        try {
            IDAL dal = NeptuneLiteUser.getInstance().getDal(context);
            this.sys = dal.getSys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beepButton(){
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
    }

    public void beepButtonWarn(){
        sys.beep(EBeepMode.FREQUENCE_LEVEL_5, 300);
    }

    public void beepTap(){
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
    }

    public void beepSuccess(){
        sys.beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_4, 100);
        sys.beep(EBeepMode.FREQUENCE_LEVEL_5, 100);
    }
}
