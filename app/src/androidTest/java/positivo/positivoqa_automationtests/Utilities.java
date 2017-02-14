package positivo.positivoqa_automationtests;

/**
 * Created by Emanuel on 08/12/2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.pm.PackageManager;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.pm.ApplicationInfo;
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
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.net.ConnectivityManager;


public class Utilities {
    private UiDevice device;
    public long timeout = 2000;

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

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));

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
            UiObject about = settingsItem.getChildByText(new UiSelector().className("android.widget.TextView"), appName);
            device.findObject(By.clazz("android.widget.TextView").text(appName));

            device.findObject(By.text(appName)).click();

        }
        catch (Exception e){
            System.out.print(e.toString());
        }
    }

    public void AllowPermissionsIfNeeded() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        if (Build.VERSION.SDK_INT >= 23) {
            UiObject allowPermissions = device.findObject(new UiSelector().text("Permitir"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                }
                catch (UiObjectNotFoundException e) {
                    System.out.print(e.toString() + " There is no permissions dialog to interact with ");
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

    public void SwipeUntilFindElementAndClick(String element){
        try{
            Thread.sleep(500);
            UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            UiObject about = settingsItem.getChildByText(new UiSelector().className("android.widget.LinearLayout"), element);
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


}
