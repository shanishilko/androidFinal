package com.example.myfinalproject;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Scanner;

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context m_context;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean flagOfOpenClass = false;

        m_context = context;

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
                                String data = "Add to shopping list: " + prodectName + " " + num;
                                Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }
    }

    private void insertToShoppingList(String prodectName, Integer num,boolean flagOfOpenClass) {

        if(MainViewModel.getInstance() != null){
            MainViewModel.getInstance().setItemsListByFile(prodectName, String.valueOf(num));
        }else{
            // Write the data to the file
            String data = prodectName + " " + String.valueOf(num) + "\n";
            try {
                FileOutputStream fos = m_context.openFileOutput("smsShoppingList.txt", Context.MODE_APPEND);
                fos.write(data.getBytes());

                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}