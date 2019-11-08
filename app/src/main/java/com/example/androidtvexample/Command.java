package com.example.androidtvexample;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Command /*implements BluetoothService.BluetoothListener*/ {

    private final static String RESPONSE_ID = "RESPONSE_ID_";
    private final static String TAG = "COMMAND";
    private final static int STRING_MAX = 2000;

    private int myCommandId = 0;

    public static void listApps(Context context, int commandId) {

        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
       // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
        JSONArray jsonArray = new JSONArray();
        List<String> jsonListParts = new ArrayList<>();

        for (ResolveInfo resolveInfo : resolveInfoList) {
            try {
                String appName = resolveInfo.loadLabel(context.getPackageManager()).toString();
                String packageName = resolveInfo.activityInfo.packageName;
           //     Intent appIntent = packageManager.getLaunchIntentForPackage(packageName);
                ComponentName name=new ComponentName(packageName, resolveInfo.activityInfo.name);

                JSONObject appInfo = new JSONObject();

                appInfo.put("name", appName);
                appInfo.put("pkg", packageName);
                appInfo.put("main", name.getClassName());

                jsonArray.put(appInfo);

                if (jsonArray.toString().length() > STRING_MAX) {
                    String auxJson = jsonArray.toString();
                    jsonListParts.add(auxJson);
                    jsonArray = new JSONArray();
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        if (jsonListParts.isEmpty()) {
            Log.i(RESPONSE_ID + commandId, jsonArray.toString());
        } else {
            for (String i : jsonListParts) {
                Log.i(RESPONSE_ID + commandId, i);
            }
        }
    }
/*
    public void initializeBluetoothNetworks(Context context, int commandId) {
        try {
            bluetoothService = BluetoothService.startFindDevices(context, this);
            myCommandId = commandId;
        } catch (Exception e) {
            Log.e(TAG, "Error: ", e);
        }
    }

    @Override
    public void action(String action) {

        if (action.compareTo(ACTION_DISCOVERY_STARTED) == 0) {
            Log.i(TAG, "-Search started");
        } else if (action.compareTo(ACTION_DISCOVERY_FINISHED) == 0) {
            listBluetoothNetworks();
            Log.i(TAG, "-Search End.");
        }

    }

    private void listBluetoothNetworks() {
        JSONArray jsonArray = new JSONArray();
        for (BluetoothDevice device : bluetoothService.getDevices()) {
            try {
                JSONObject appInfo = new JSONObject();
                appInfo.put("deviceName", device.getName());
                appInfo.put("deviceAddress", device.getAddress());
                jsonArray.put(appInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(RESPONSE_ID + myCommandId, jsonArray.toString());
    }

    public void initializeWifiSearch(Context context, int commandId) {
        try {
            if (Utils.enableWifi(context)) {
                wifiService.startWifiNetworkScan(context, commandId);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void connectToWifiPass(int myCommandId, Intent intent) {
        try {
            String extraParameter = intent.getStringExtra("data");
            Gson gson = new Gson();
            WifiConfigModel wifiConfig = gson.fromJson(extraParameter, WifiConfigModel.class);
            wifiService.addWifiConfiguration(wifiConfig);
            wifiService.connectToWifiSemp(wifiConfig, myCommandId);
        } catch (Exception e) {
            Log.i(TAG + " error ", e.getMessage());
        }
    } */
}
