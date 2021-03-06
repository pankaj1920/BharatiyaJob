package com.bharatiyajob.bharatiyajob.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bharatiyajob.bharatiyajob.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.bharatiyajob.bharatiyajob.Login.EnterOtpActivity.Channel_id;

public class FCMMessageReceiverService extends FirebaseMessagingService {

    final String TAG="MyTag";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"onMessageReceived: called"+remoteMessage.getFrom());
        if (remoteMessage.getNotification()!=null){
            String Title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();

            Notification notification=new NotificationCompat.Builder(this,Channel_id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle(Title)
                    .setContentText(body)
                    .build();

            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(102,notification);


        }

        if (remoteMessage.getData().size()>0) {


            for (String key :remoteMessage.getData().keySet()) {
                Log.d(TAG,"key " + key + " " + "Data " + remoteMessage.getData().get(key) + "\n");

            }
            Log.d(TAG, "onMessageReceived :Data" + remoteMessage.getData().toString());
        }
    }



    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG,"onDeletedMessages: called");

    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG,"onNewToken: called");
//        Upload this to app server

    }
}
