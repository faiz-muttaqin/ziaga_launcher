/*
 * ============================================================================
 * COPYRIGHT
 *              Pax CORPORATION PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with Pax Corporation and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *      Copyright (C) 2017 - ? Pax Corporation. All rights reserved.
 * Module Date: 2017-5-19
 * Module Author: Kim.L
 * Description:
 *
 * ============================================================================
 */
package com.swi.ziagalauncher.Helper;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.swi.ziagalauncher.quickclick.QuickClickProtection;

public abstract class EditorActionListener implements TextView.OnEditorActionListener {

    private QuickClickProtection quickClickProtection = QuickClickProtection.getInstance();

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d("silly muhua", "actionId=" + actionId + ", event=" + event);
        if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                Log.d("silly muhua", "物理按键的Enter");
                onKeyOk();
                return true;
            }
        } else if (actionId == EditorInfo.IME_ACTION_DONE) {
            quickClickProtection.start();
            onKeyOk();
            return true;
        } else if (actionId == EditorInfo.IME_ACTION_NONE) {
            quickClickProtection.start();
            onKeyCancel();
            return true;
        }
        return false;
    }

    protected abstract void onKeyOk();

    protected abstract void onKeyCancel();
}
