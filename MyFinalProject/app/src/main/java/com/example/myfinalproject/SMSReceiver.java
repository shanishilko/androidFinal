package com.example.myfinalproject;



import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SMSReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context m_context;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean flagOfOpenClass = false;

        m_context = context;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : runningProcesses) {
            if(process.processName.equals("com.example.myfinalproject")){
                flagOfOpenClass = true;
            }
        }



//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//        if(taskInfo.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
//            // The app is in the foreground
//            flagOfOpenClass = true;
//        } else {
//            // The app is closed
//            flagOfOpenClass = false;
//        }

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
//        m_context.getApplicationContext(),
        if(flagOfOpenClass){
            MainViewModel.getInstance().setItemsListByFile(prodectName, String.valueOf(num));
        }else{
            boolean bExist = false;

            try {
                ArrayList<ShoppingItem> shoppingList = new ArrayList<>();
                shoppingList = getItemsListByFile();
                clearListByFile();   // clear file before writing
//                FileOutputStream outputStreamWriter = m_context.openFileOutput(m_context.openFileOutput("shoppingList.txt");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(m_context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));

                for (int i = 0; i < shoppingList.size(); i++) {
                    if (shoppingList.get(i).getName().equals(prodectName)) { // if list contains the product, just update quantity
                        shoppingList.get(i).setQuantity(Integer.valueOf(num));
                        bExist = true;
                    }
                    // then write to file  -- need to delete before or auto??
                    outputStreamWriter.append(shoppingList.get(i).getName() + " " + shoppingList.get(i).getQuantity() + "\n");
                }
                if(!bExist){
                    ShoppingItem newItem = new ShoppingItem(prodectName, Integer.valueOf(num));
//                    shoppingList.add(newItem);
                    outputStreamWriter.append(prodectName + " " + num + "\n");

                }
//                this.itemsLiveData.setValue(shoppingList);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }

    public ArrayList<ShoppingItem> getItemsListByFile() {
        ArrayList<ShoppingItem> ret = new ArrayList<>();

        try {
            String data = "New message from: " +   "\nThe message: " ;
            Toast.makeText(m_context, data, Toast.LENGTH_LONG).show();
            InputStream inputStream = m_context.openFileInput("shoppingList.txt");


            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];

                inputStreamReader.read(buffer);
                inputStream.close();
                String str = new String(buffer);
                String parts[] = str.split("\n");
                for (String part : parts)
                {
                    if(part == ""){
                        continue;
                    }
                    String name_quantity[] = part.split(" ");
                    ShoppingItem item = new ShoppingItem(name_quantity[0], Integer.parseInt(name_quantity[1]));
                    ret.add(item);
                }
//                this.itemsLiveData.setValue(ret);

            }
        }catch (Exception e) {
            e.printStackTrace();
//            this.itemsLiveData.setValue(ret);
        }

        return ret;
    }
    private void clearListByFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(m_context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}