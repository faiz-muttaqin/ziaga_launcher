package com.swi.ziagalauncher.Helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by safeMode on 11/28/16.
 */

public class ConvertStringJsonHelper {
    public static JsonObject convertToJsonObject(String data)
    {
        JsonObject jsonObject=null;
        try{
            JsonParser jsonParser=new JsonParser();
            jsonObject=jsonParser.parse(data).getAsJsonObject();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return jsonObject;
    }
}
