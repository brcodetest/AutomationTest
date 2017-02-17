package positivo.qa_automation;

import android.content.Context;
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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParsePosition;

import static android.support.test.uiautomator.Until.findObject;

/**
 * Created by Kowalczuk on 17/02/2017.
 */

@RunWith(AndroidJUnit4.class)
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

    @Test
    public void AddAppsToHomeScreen() throws Exception
    {
        device.pressHome();
        device.wait(Until.hasObject(By.desc("Apps")), timeout);
        device.findObject(By.desc("Apps")).click();
        Thread.sleep(500);

        UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        settingsItem.getChildByText(new UiSelector().className("android.widget.TextView"), "Agenda");

        device.findObject(By.text("Agenda")).longClick();

        device.pressHome();

        boolean app = device.findObject(By.desc("Agenda")).isClickable();

        Assert.assertTrue("App não encontrado na home screen!", app);

    }

    @Test
    public void RemoveAppsFromHomeScreen() throws Exception
    {
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
    public void ClearNotificationBar() throws Exception{

        Thread.sleep(1000);
        util.SendNotification(this.context);
        Thread.sleep(10000);

    }

}
