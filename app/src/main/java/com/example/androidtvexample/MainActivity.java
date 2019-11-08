package com.example.androidtvexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends Activity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = new Intent(Intent.ACTION_ALL_APPS, null);
        intent2 .addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
        List<ResolveInfo> apps = this.getPackageManager().queryIntentActivities( intent2 , 0);
        Log.i("Resultado", "entrou");
        for (ResolveInfo resolveInfo : apps) {

                Log.i("Resultado", "entrou2");
                String appName = resolveInfo.loadLabel(this.getPackageManager()).toString();
                String packageName = resolveInfo.activityInfo.packageName;
                String className = intent2.getComponent().getClassName();

                Log.i("Resultado", appName);
                Log.i("Resultado", packageName);
                Log.i("Resultado", className);
                Log.i("Resultado", "");
        }

        Intent intent = new Intent(this, MyJobIntentService.class);
        MyJobIntentService.enqueueWork(this, intent);

        finish();
    }
}
