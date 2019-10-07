package com.esgi.pockethealth.application;

import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.Service ;
import android.content.Intent ;
import android.os.Bundle;
import android.os.Handler ;
import android.os.IBinder ;
import android.support.v4.app.NotificationCompat ;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.models.Appointment;
import com.esgi.pockethealth.models.Patient;

import java.text.SimpleDateFormat;
import java.util.Timer ;
import java.util.TimerTask ;


public class NotificationService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    Patient user = null;
    Appointment appointment = null;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        super .onStartCommand(intent , flags , startId) ;
        Bundle bundle = intent.getExtras();
        this.user = (Patient) bundle.getSerializable("user");

        startTimer() ;
        return START_STICKY ;
    }

    @Override
    public void onCreate () {

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("user", this.user);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy () {
        super .onDestroy() ;
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("user", this.user);
        sendBroadcast(broadcastIntent);
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        appointment = user.getClosestAppointment();
        timer .schedule( timerTask, appointment.getCreation_date()) ; //
    }

    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }

    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        createNotification() ;
                    }
                }) ;
            }
        } ;
    }

    private void createNotification () {

        SimpleDateFormat appointmentFormat = new SimpleDateFormat("hh:mm");
        String date = appointmentFormat.format(appointment.getCreation_date());
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Votre rendez-vous approche !" ) ;
        mBuilder.setContentText( "Docteur " + appointment.getDoctor().getName()
                + " " + appointment.getDoctor().getForename() + " à " + date) ;
        mBuilder.setTicker( "Notification Listener Service Example" ) ;
        mBuilder.setSmallIcon(R.drawable.logo ) ;
        mBuilder.setAutoCancel( true ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

}