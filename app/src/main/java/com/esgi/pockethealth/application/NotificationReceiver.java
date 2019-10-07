package com.esgi.pockethealth.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent broadcastIntent = new Intent(context, NotificationService.class);
        broadcastIntent.putExtras(intent.getExtras());
        context.startService(broadcastIntent);
    }
}
