/*
 * ============================================================================
 * COPYRIGHT
 *              Pax CORPORATION PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with Pax Corporation and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *      Copyright (C) 2017 - ? Pax Corporation. All rights reserved.
 * Module Date: 2017-8-31
 * Module Author: laiyi
 * Description:
 *
 * ============================================================================
 */
package com.swi.ziagalauncher.Paxstore;
import android.content.Context;

import org.dom4j.tree.AbstractDocument;

import java.util.LinkedHashSet;
import java.util.Set;


public class DownloadManager {
    //    private List<AbstractDocument> documentList = new ArrayList<>();
    private Set<DocumentBase> documentList = new LinkedHashSet<>();

    private String appKey;
    private String appSecret;
    private String sn;
    private String filePath;
    private static DownloadManager instance;

    public static synchronized DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();

        }
        return instance;
    }

    public DownloadManager addDocument(DocumentBase document) {
        documentList.add(document);
        return instance;
    }

    public void updateData(Context context) {
        for (DocumentBase document : documentList) {
            if (document.parse() == 0) {
                document.save(context);
            }
            document.delete();
        }
    }


    private void deleteAll() {
        for (DocumentBase document : documentList) {
            document.delete();
        }
    }

    public void remove(AbstractDocument document) {
        documentList.remove(document);
    }

    public void clear() {
        documentList.clear();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public boolean hasUpdateParam() {
        for (DocumentBase document : documentList) {
            if (document.isExit()) {
                return true;
            }
        }
        return false;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
