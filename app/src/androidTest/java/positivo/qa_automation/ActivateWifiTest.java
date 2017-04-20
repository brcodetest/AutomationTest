package positivo.qa_automation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.Service;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by Brdroid on 19/04/2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivateWifiTest {

    private UiDevice device;
    Utilities util = new Utilities();
    Context context;
    long timeout = util.timeout;

    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();
    }

    @AfterClass
    public static void TearDown() throws Exception {

        Thread.sleep(1000);
    }

    @Test
    public void ActivateWifiTest() throws Exception {

        device.pressHome();
        util.OpenAppsFromMenu("Configurar");
        device.wait(Until.hasObject(By.text("Wi-Fi")), 4000);
        UiObject2 menuWifi = device.findObject(By.text("Wi-Fi"));
        menuWifi.click();

        device.wait(Until.hasObject(By.res("com.android.settings:id/switch_bar")), 4000);
        UiObject2 switchWifi = device.findObject(By.res("com.android.settings:id/switch_bar"));

        Thread.sleep(5000);

        if (!switchWifi.isChecked()) {

            switchWifi.click();

        }


        if (!device.hasObject(By.text("Conectado"))){

            //Assert.fail("nao encontrado");
            util.SwipeUntilFindElementAndClick("RecyclerView","PosinfoCode");
            UiObject passwd = new UiObject(new UiSelector().resourceId("com.android.settings:id/password"));
            Thread.sleep(5000);
            passwd.setText(util.wifiPass1);

            device.wait(Until.hasObject(By.text("CONECTAR")), 6000);
            UiObject2 conect = device.findObject(By.text("CONECTAR"));
            Thread.sleep(1000);
            conect.click();


        }



    }
}
