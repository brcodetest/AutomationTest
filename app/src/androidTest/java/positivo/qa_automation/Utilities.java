package positivo.qa_automation;

/**
 * Created by Emanuel on 08/12/2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v4.app.NotificationCompat;
import android.net.ConnectivityManager;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Utilities {
    private UiDevice device;
    public long timeout = 2000;

    //wifi data
    public String wifiAp1 = "PosinfoCode";
    public String wifiPass1 ="pGUUMcwd";

    public void UnlockDevice(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        try{
            // Start from the home screen
            if(!device.isScreenOn()){
                device.wakeUp();
                UiObject tela;
                tela = new UiObject(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
                tela.swipeUp(5);
                device.pressHome();
            }
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }

    }

    public void AdbCommandNoReturn(String comm) throws Exception{
        Runtime.getRuntime().exec(comm + " --user 0");
        Thread.sleep(1000);
    }

    public String AdbCommand(String cmd){

        String s = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the cmd
            String result = "";
            while ((s = stdInput.readLine()) != null) {
                result = result + s;
            }

            // read any errors from the attempted comd
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            return result;

        } catch (IOException e) {
            System.out.println("exception here's what I know: ");
            e.printStackTrace();
            return "Exception occurred";
        }

    }

    public void OpenAppsFromMenu(String appName){
        try{
            device.pressHome();
            device.wait(Until.hasObject(By.desc("Apps")), timeout);
            device.findObject(By.desc("Apps")).click();
            Thread.sleep(500);

            UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
            settingsItem.getChildByText(new UiSelector().className("android.widget.TextView"), appName);
            device.findObject(By.clazz("android.widget.TextView").text(appName));

            device.findObject(By.text(appName)).click();

            AllowPermissionsIfNeeded(1);

        }
        catch (Exception e){
            System.out.print(e.toString());
        }
    }

    public void AllowPermissionsIfNeeded(int repeticoes){


        for(int i = 0; i < repeticoes; i++) {
            device.wait(Until.hasObject(By.text("PERMITIR")), timeout);
            if (Build.VERSION.SDK_INT >= 23) {
                UiObject allowPermissions = device.findObject(new UiSelector().text("PERMITIR"));
                if (allowPermissions.exists()) {
                    try {
                        allowPermissions.click();
                    } catch (UiObjectNotFoundException e) {
                        System.out.print(e.toString() + " Não há permissões para serem concedidas!");
                    }
                }
            }
        }

    }

    public boolean CheckInternetConnection(Context con){

        ConnectivityManager conMgr = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean ret = true;
        if (conMgr != null) {
            NetworkInfo i = conMgr.getActiveNetworkInfo();

            if (i != null) {
                if (!i.isConnected()) {
                    ret = false;
                }

                if (!i.isAvailable()) {
                    ret = false;
                }
            }

            if (i == null)
                ret = false;
        } else
            ret = false;
        return ret;

    }

    /**
     * @param type ScrollView ou ListView
     * @param element Nome do elemento
     * */
    public void SwipeUntilFindElementAndClick(String type, String element){
        try{
            Thread.sleep(500);
            UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.widget." + type));
            settingsItem.getChildByText(new UiSelector().className("android.widget.LinearLayout"), element);
            device.findObject(By.clazz("android.widget.TextView").text(element)).click();

        }
        catch (Exception e){
            System.out.print(e.toString());
        }
    }

    public boolean ValidateGMSversion(Context con){
        int versionGms = 0;
        try {
           versionGms = con.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(versionGms < 10000000)
        {
            return false;
        }

        return true;
    }

    public void SendNotification(Context con){

        NotificationManager notificationManager = (NotificationManager)con.getSystemService(NOTIFICATION_SERVICE);

        Notification n  = new Notification.Builder(con)
                .setContentTitle("Automation")
                .setContentText("This is a notification created by Automation!")
                .setSmallIcon(R.drawable.ic_stat_name).build();
        notificationManager.notify(0, n);

    }

    public void SwipeNotificationBar() throws Exception{

            UiObject screen;
            screen = new UiObject(new UiSelector().resourceId("com.android.launcher3:id/workspace"));
            screen.swipeDown(5);
    }

    public void SwipeQuickSettingsBar() throws Exception{

        UiObject screen;
        screen = new UiObject(new UiSelector().resourceId("com.android.launcher3:id/workspace"));
        screen.swipeDown(8);

        UiObject header;
        header = new UiObject(new UiSelector().resourceId("com.android.systemui:id/header"));
        header.swipeDown(5);
    }

    /**
     * @param findObjectBy text, description ou resourceId
     * */
    public void LongClick(String findObjectBy, String name, int steps) throws Exception{

        if(findObjectBy.equals("text")){
            UiObject appAdd = new UiObject(new UiSelector().text(name));
            Rect appButton_rect = appAdd.getBounds();
            device.swipe(appButton_rect.centerX(), appButton_rect.centerY(), appButton_rect.centerX(), appButton_rect.centerY(), steps);
        }

        if(findObjectBy.equals("description")){
            UiObject appAdd = new UiObject(new UiSelector().description(name));
            Rect appButton_rect = appAdd.getBounds();
            device.swipe(appButton_rect.centerX(), appButton_rect.centerY(), appButton_rect.centerX(), appButton_rect.centerY(), steps);
        }

        if(findObjectBy.equals("resourceId")){
            UiObject appAdd = new UiObject(new UiSelector().resourceId(name));
            Rect appButton_rect = appAdd.getBounds();
            device.swipe(appButton_rect.centerX(), appButton_rect.centerY(), appButton_rect.centerX(), appButton_rect.centerY(), steps);
        }
    }

    public void ClearAppData(String packageName) throws Exception {
        try {
            device.executeShellCommand("pm clear " + packageName);
        }
        catch (Exception e)
        {
            System.out.print("Falha ao limpar dados do pacote " + packageName);

        }

    }

}
