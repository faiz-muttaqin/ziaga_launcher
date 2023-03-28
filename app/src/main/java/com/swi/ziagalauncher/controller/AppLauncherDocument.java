package com.swi.ziagalauncher.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.swi.ziagalauncher.Paxstore.DocumentBase;
import com.swi.ziagalauncher.Paxstore.DownloadManager;
import com.swi.ziagalauncher.SessionManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ditya Geraldy on 20 February 2019.
 */
public class AppLauncherDocument extends DocumentBase {

    private Context context;
    private SessionManager sessionManager;

    public AppLauncherDocument(String filePath, Context context) {
        super(filePath);
        this.context = context;
        this.sessionManager = new SessionManager(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public int parse() {
        Document document = getDocument();
        if (document == null) {
            return -1;
        }
        Log.i("DIT", "parse" );

        NodeList root = document.getChildNodes();
        Node param = root.item(0);
        NodeList node = param.getChildNodes();
        int index;
        String text;
        int countAds=0;
        int countIcon=0;
        for (int i = 1; i < node.getLength(); i = index) {
            index = i;

            //pass
            text = node.item(index).getTextContent();
            if (text == null || text.isEmpty()) {
                return -1;
            }
            index += 2;
            sessionManager.setPassword(text);

            //app
            text = node.item(index).getTextContent();
            if (text == null || text.isEmpty()) {
                return -1;
            }
            index += 2;
            StringBuilder dataTxt = readFile(text);
            String text2 =  dataTxt.toString();
            String data = "{\"menu\": ["+text2+"]}";
            deleteFile(text);
            sessionManager.setMenuApp(data);

            //ads interval time
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                int adsTime=Integer.parseInt(text)*1000;
                sessionManager.seAdsTime(adsTime);
            }
            index += 2;

            //ads1
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text, String.valueOf(countAds));
                countAds++;
            }else{
                deleteFile(String.valueOf(countAds));
            }
            index += 2;

            //ads2
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,String.valueOf(countAds));
                countAds++;
            }else{
                deleteFile(String.valueOf(countAds));
            }
            index += 2;

            //ads3
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,String.valueOf(countAds));
                countAds++;
            }else{
                deleteFile(String.valueOf(countAds));
            }
            index += 2;
//______________________________________________________________________________________________________________
            //icon1
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon2
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon3
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon4
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon5
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon6
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon7
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon8
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon9
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon10
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon11
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon12
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon13
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon14
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;

            //icon15
            text = node.item(index).getTextContent();
            if (text != null && !text.isEmpty()) {
                rename(text,"icon"+ String.valueOf(countIcon));
            }else{
                deleteFile("icon"+String.valueOf(countIcon));
            }
                countIcon++;
            index += 2;


            sessionManager.setTotalAds(countAds);

//            {"name": "CIMB NIAGA","package": "com.pax_cimb_single.edc"},{"name": "PPOB","package": "co.id.swi.simpatindo"},{"name": "SPAY","package": "cn.swiftpass.enterprise.overseas.pos.A920.intl_pos"},{"name": "POS","package": "com.swi.hausjo_pos"}
//            Log.i("DIT","Push Password " + text);
//            Toast.makeText(context, "Push password success", Toast.LENGTH_LONG).show();
        }
        return 0;
    }

    @Override
    public void save(Context context) {

    }

    private void rename(String dari, String ke){
        File directory = new File(DownloadManager.getInstance().getFilePath());
        File from      = new File(directory, dari);
        File to        = new File(directory, ke + ".jpg");
        from.renameTo(to);
    }

    private void deleteFile(String fileName){
        File directory = new File(DownloadManager.getInstance().getFilePath());
        File file      = new File(directory, fileName+".jpg");
        if (file.exists()) {
            file.delete();
        }
    }

    private StringBuilder readFile(String dataFile){
        //Read text from file
        StringBuilder text = new StringBuilder();
        File directory = new File(DownloadManager.getInstance().getFilePath());
        File file      = new File(directory, dataFile);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text;
    }
}
