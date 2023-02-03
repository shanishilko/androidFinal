package com.example.myfinalproject;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.List;
import java.util.Scanner;

public class SMSReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean flagOfOpenClass;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if(taskInfo.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
            // The app is in the foreground
            flagOfOpenClass = true;
        } else {
            // The app is closed
            flagOfOpenClass = false;
        }

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        SmsMessage message = messages[0];
        if(message != null)
        {
            String sender = message.getDisplayOriginatingAddress();
            String body = message.getDisplayMessageBody();
            Scanner scanner = new Scanner(body);
            scanner.useDelimiter(" ");
            if (scanner.hasNext()){
                if(scanner.next().equals("shoppingList")){
                    while(scanner.hasNext()){
                        String prodectName = scanner.next();
                        if(scanner.hasNext()){
                            String prodectAmount = scanner.next();
                            Integer num = Integer.valueOf(prodectAmount);
                            if(num!=null){
                                insertToShoppingList(prodectName,num ,flagOfOpenClass);
                            }
                        }
                    }
                }
            }

            String data = "New message from: " + sender + "\nThe message: " + body;
            Toast.makeText(context, data, Toast.LENGTH_LONG).show();
        }
    }

    private void insertToShoppingList(String prodectName, Integer num,boolean flagOfOpenClass) {
        if(flagOfOpenClass){

        }else{

        }
    }

}