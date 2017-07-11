package positivo.qa_automation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.text.ParsePosition;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.test.uiautomator.Until.findObject;

/**
 * Created by Kowalczuk on 17/02/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomeTest {

    private UiDevice device;
    Utilities util = new Utilities();
    Context context;
    long timeout = util.timeout;

    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();}

    @After
    public void TearDown() throws Exception{

        Thread.sleep(1000);
    }

    @Test
    public void AddAppsToHomeScreen() throws Exception {
        device.pressHome();
        device.wait(Until.hasObject(By.desc("Apps")), timeout);
        device.findObject(By.desc("Apps")).click();
        Thread.sleep(500);

        UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        settingsItem.getChildByText(new UiSelector().className("android.widget.TextView"), "Agenda");

        util.LongClick("text", "Agenda", 100);


        device.pressHome();

        boolean app = device.findObject(By.desc("Agenda")).isClickable();

        Assert.assertTrue("App não encontrado na home screen!", app);

    }

    @Test
    public void RemoveAppsFromHomeScreen() throws Exception {
        device.pressHome();

        UiObject app = new UiObject(new UiSelector().description("Agenda"));

        app.swipeRight(200);
        app.swipeUp(100);

        try {

            boolean appAssert = device.findObject(By.desc("Agenda")).isEnabled();

            if(appAssert == true)
            {
                app.swipeUp(100);
            }
            appAssert = device.findObject(By.desc("Agenda")).isEnabled();

            Assert.assertFalse("App não removido da home screen!", appAssert);

        }
        catch (Exception e)
        {
            if(e.toString().endsWith("null object reference")){
                Assert.assertTrue(true);
            }
        }

    }

    @Test
    public void DismissNotification() throws Exception{

        Thread.sleep(500);
        util.SendNotification(this.context);
        Thread.sleep(2000);
        device.openNotification();
        UiObject notif = new UiObject(new UiSelector().text("Automation"));
        notif.swipeRight(50);

        try {

            boolean notifAssert = device.findObject(By.text("Automation")).isEnabled();

            if(notifAssert == true)
            {
                notif.swipeUp(100);

            }
            notifAssert = device.findObject(By.text("Automation")).isEnabled();

            Assert.assertFalse("Notificação não foi excluída!", notifAssert);

        }
        catch (Exception e)
        {
            if(e.toString().endsWith("null object reference")){
                Assert.assertTrue(true);
            }
        }
    }

    @Test
    public void DismissNotificationLockScreen() throws Exception {

        device.sleep();
        Thread.sleep(500);
        util.SendNotification(this.context);
        device.wakeUp();
        Thread.sleep(1000);
        UiObject notif = new UiObject(new UiSelector().text("Automation"));
        notif.swipeRight(50);

        try {

            boolean notifAssert = device.findObject(By.text("Automation")).isEnabled();

            if(notifAssert == true)
            {
                notif.swipeUp(100);

            }
            notifAssert = device.findObject(By.text("Automation")).isEnabled();

            Assert.assertFalse("Notificação não foi excluída!", notifAssert);

        }
        catch (Exception e)
        {
            if(e.toString().endsWith("null object reference")){
                Assert.assertTrue(true);
            }
        }
        finally {
            device.sleep();
            Thread.sleep(1000);
        }



    }

    @Test
    public void DismissAllNotifications() throws Exception{

        Thread.sleep(500);
        util.SendNotification(this.context);
        Thread.sleep(2000);
        util.SwipeNotificationBar();

        UiObject2 notif;

        try {

            device.findObject(By.desc("Limpar todas as notificações.")).click();

            util.SwipeNotificationBar();

            notif =  device.findObject(By.text("Automation"));
            boolean notifAssert = notif.isEnabled();

            Assert.assertFalse("Notificação não foi excluída!", notifAssert == false);

        }
        catch (Exception e)
        {
            if(e.toString().endsWith("null object reference")){
                Assert.assertTrue(true);
            }
        }
    }

    @Test
    public void OpenRecentApps() throws Exception {
        device.pressRecentApps();
        Thread.sleep(1000);
        boolean recents = device.findObject(By.res("com.android.systemui","recents_view")).isEnabled();

        Assert.assertTrue("Não abriu os apps recentes", recents);

    }

    @Test
    public void AddWidgetToHomeScreen() throws  Exception {

        device.pressMenu();

        device.findObject(By.text("Widgets")).click();

        UiScrollable widgetScroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        widgetScroll.getChildByText(new UiSelector().className("android.widget.TextView"), "Configurações");

        util.LongClick("text", "Atalho para as configurações", 100);

        Thread.sleep(500);
        device.findObject(By.text("Bateria")).click();

        Thread.sleep(500);

        boolean widget = device.findObject(By.desc("Bateria")).isEnabled();

        Assert.assertTrue("Widget não encontrado na home screen!", widget);

    }

    @Test
    public void ChangeWallpaper() throws Exception{
        Thread.sleep(500);
        device.pressHome();
        device.pressMenu();
        device.findObject(By.text("Planos de fundo")).click();

        Thread.sleep(500);

        UiScrollable wp = new UiScrollable(new UiSelector().resourceId("com.android.launcher3:id/wallpaper_scroll_container"));
        wp.swipeLeft(100);
        wp.swipeLeft(100);

        device.findObject(By.text("Balões")).click();

        Thread.sleep(500);

        device.findObject(By.text("Definir plano de fundo")).click();
    }

    @Test
    public void OpenAppFromRecentsMenu() throws Exception{
        util.OpenAppsFromMenu("Telefone");
        Thread.sleep(3000);
        util.OpenAppsFromMenu("Câmera");
        Thread.sleep(3000);
        device.pressHome();

        device.pressRecentApps();
        Thread.sleep(500);

        device.findObject(By.text("Telefone")).click();
        Thread.sleep(3000);

        String packagename = device.getCurrentPackageName();

        Assert.assertEquals("Não abriu o app esperado", "com.android.dialer", packagename);



    }

}
