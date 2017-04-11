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
 * Created by Brdroid on 21/02/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * Created by Brdroid on 23/02/2017.
 */



public class DataCall {

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
    public void DataCallTest() throws Exception {
        device.pressHome();
        util.OpenAppsFromMenu("Configurar");
        device.wait(Until.hasObject(By.text("Wi-Fi")), 4000);
        UiObject2 menuWifi = device.findObject(By.text("Wi-Fi"));
        menuWifi.click();



        Thread.sleep(2000);

        if(device.findObject(By.res("com.android.settings:id/switch_widget")).isChecked())
        {
            device.findObject(By.res("com.android.settings:id/switch_widget")).click();

        }

        device.pressHome();
        util.SwipeQuickSettingsBar();

        if(!device.hasObject(By.descContains("barras de sinal do telefone")))
        {
            Assert.fail("SIM Card não inserido!");
        }

        device.findObject(By.descContains("barras de sinal do telefone"));
        UiObject2 dataConnect = device.findObject(By.descContains("barras de sinal do telefone"));

        dataConnect.click();

        Thread.sleep(2000);

        UiObject2 ligData = device.findObject(By.clazz("android.widget.Switch"));

        if(!ligData.isChecked())
        {
            ligData.click();

        }

        device.pressHome();

        Thread.sleep(5000);

        String retorno = device.executeShellCommand("ping -c 3 8.8.8.8 ");

        Assert.assertTrue("Não foi possivel efetuar o ping", retorno.contains("from 8.8.8.8"));




    }
}


