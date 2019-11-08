package com.example.androidtvexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


/*
    JobIntentService é uma maneira moderna de executar o serviço em segundo plano
    a partir do aplicativo em segundo plano.
*/

public class MyJobIntentService extends JobIntentService {

    static final int JOB_ID = 1000;
    final String TAG = "MyJobIntenetService";
    final String LIST_APPS_ACTION = "com.example.androidtvexample.LIST.APPS";
    final String LIST_BLUETOOTH_ACTION = "com.example.androidtvexample.LIST.BLUETOOTH";
    final String LIST_WIFI_ACTION = "com.example.androidtvexample.LIST.WIFI";
    final String WIFI_CONNECT_ACTION = "com.example.androidtvexample.WIFI.CONNECT";

    final Command cmd = new Command();

    final Handler mHandler = new Handler();

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int commandId = intent.getIntExtra("id", 0);
            if (action != null && commandId != 0) {
                sendCommand(context, intent, action, commandId);
            }
        }
    };

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        // Ações ao ser ouvidas sempre que acontece alguma alteração de estado.
        IntentFilter filter = new IntentFilter(LIST_APPS_ACTION);
        IntentFilter filterListBluetooth = new IntentFilter(LIST_BLUETOOTH_ACTION);
        IntentFilter filterListWifi = new IntentFilter(LIST_WIFI_ACTION);
        IntentFilter filterWifiConnect = new IntentFilter(WIFI_CONNECT_ACTION);
        registerReceiver(receiver, filter);
        registerReceiver(receiver, filterListBluetooth);
        registerReceiver(receiver, filterListWifi);
        registerReceiver(receiver, filterWifiConnect);
        toast("Stress Carga - Service initialized");

        while (true) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.wtf(TAG, "Stress Carga - Service finalized");
        toast("Stress Carga - Service finalized");
    }

    public static void enqueueWork(Context context, Intent work) {
        // Inicializa o Job.
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    private void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendCommand(Context context, Intent intent, String action, int commandId) {
        switch (action) {
            case LIST_APPS_ACTION:
                Command.listApps(context, commandId);
                toast("List apps");
                break;
            case LIST_BLUETOOTH_ACTION:
                toast("Searching bluetooth networks");
           //     cmd.initializeBluetoothNetworks(context, commandId);
                break;
            case LIST_WIFI_ACTION:
                toast("Searching Wifi networks");
              //  cmd.initializeWifiSearch(context, commandId);
                break;
            case WIFI_CONNECT_ACTION:
                toast("Trying to connect to wifi");
             //   cmd.connectToWifiPass(commandId, intent);
                break;
        }
    }
}