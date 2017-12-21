package com.qinnovation.sample.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileUtil {

    public static String getJson(Context context) throws IOException {
        StringBuilder buf = new StringBuilder();

        String json = "";
        InputStream inputStream = context.getAssets().open("product.json");

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }

        in.close();
        inputStream.close();

        return json;
    }

    public static String getJsonFormAsset(AssetManager assetManager, String fileName) throws IOException {
        StringBuilder buf = new StringBuilder();

        String json = "";
        InputStream inputStream = assetManager.open(fileName);

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }

        in.close();
        inputStream.close();

        return buf.toString();
    }
}
