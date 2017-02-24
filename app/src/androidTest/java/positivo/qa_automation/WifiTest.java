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

public class WifiTest {

    private UiDevice device;
    Utilities util = new Utilities();
    Context context;
    long timeout = util.timeout;

    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();}

    @AfterClass
    public static void TearDown() throws Exception{

        Thread.sleep(1000);
    }

    @Test
    public void ConnectWifiandNavigate () throws Exception{

        util.ClearAppData("com.android.chrome");
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Wi-Fi");

        UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        settingsItem.getChildByText(new UiSelector().className("android.widget.LinearLayout"), util.wifiAp1);
        device.findObject(By.clazz("android.widget.TextView").text(util.wifiAp1)).click();

        UiObject passwd = new UiObject(new UiSelector().resourceId("com.android.settings:id/password"));
        passwd.setText(util.wifiPass1);

        device.wait(Until.hasObject(By.text("Conectar")), 4000);
        UiObject2 conect = device.findObject(By.text("Conectar"));
        Thread.sleep(1000);
        conect.click();

        device.wait(Until.hasObject(By.text("Conectado")),15000);
        UiObject con = new UiObject(new UiSelector().text("Conectado"));
        con.click();

        UiObject cancel = new UiObject(new UiSelector().text("Cancelar"));
        cancel.click();

        device.pressHome();
        util.OpenAppsFromMenu("Chrome");


            Thread.sleep(5000);
            UiObject2 acept = device.findObject(By.text("Aceitar e continuar"));
            acept.click();
            Thread.sleep(3000);
            UiObject2 not = device.findObject(By.text("Não, obrigado"));
            not.click();
            UiObject2 serch = device.findObject(By.text("Pesquisar ou digitar URL"));
            UiObject addr = new UiObject(new UiSelector().resourceId("com.android.chrome:id/search_box_text"));
            addr.click();

        UiObject url = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        url.setText("teste");

        device.pressEnter();

        Assert.assertTrue(device.wait(Until.hasObject(By.desc("www.minhaconexao.com.br")), 10000));
    }


    @Test
    public void EnableAirplaneMode () throws Exception {

        //util.UnlockDevice();
        device.pressHome();
        util.SwipeQuickSettingsBar();

        device.findObject(By.text("Modo avião"));
        UiObject2 mdaviao = device.findObject(By.text("Modo avião"));
        mdaviao.click();

        Thread.sleep(15000);

        mdaviao.click();


    }

    @Test
    public void WifiSwitch () throws Exception {

        device.pressHome();
        util.SwipeQuickSettingsBar();

        UiObject data = new UiObject(new UiSelector().text("Conexão de dados"));
        data.click();

        Thread.sleep(3000);

        UiObject wifiIcon = new UiObject(new UiSelector().descriptionStartsWith("Wifi three bars.."));
        wifiIcon.click();


        Thread.sleep(15000);

        UiObject data1 = new UiObject(new UiSelector().text("Conexão de dados"));
        data1.click();

        Thread.sleep(5000);

        UiObject wifiIcon1 = new UiObject(new UiSelector().descriptionStartsWith("Wi-Fi"));
        wifiIcon1.click();


        UiObject screenWifi = new UiObject(new UiSelector().className("android.widget.FrameLayout"));
        screenWifi.swipeUp(5);

        device.pressHome();

    }

    @Test
    public void NotificationWifi () throws Exception {

        util.OpenAppsFromMenu("Configurar");
        device.wait(Until.hasObject(By.text("Wi-Fi")), 4000);
        UiObject2 menuWifi = device.findObject(By.text("Wi-Fi"));
        menuWifi.click();

        UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        settingsItem.getChildByText(new UiSelector().className("android.widget.LinearLayout"), util.wifiAp1);
        device.findObject(By.clazz("android.widget.TextView").text(util.wifiAp1)).click();

        device.wait(Until.hasObject(By.text("Conectado")),15000);
        UiObject con = new UiObject(new UiSelector().text("Conectado"));
        con.click();

        UiObject forget = new UiObject(new UiSelector().text("Esquecer"));
        forget.click();

        device.pressHome();

        UiObject screen;
        screen = new UiObject(new UiSelector().resourceId("com.android.launcher3:id/workspace"));
        screen.swipeDown(8);

        UiObject notification = new UiObject(new UiSelector().text("Redes Wi-Fi disponíveis"));
        notification.swipeRight(50);

        util.OpenAppsFromMenu("Configurar");
        device.wait(Until.hasObject(By.text("Wi-Fi")), 4000);
        UiObject2 menuWifi1 = device.findObject(By.text("Wi-Fi"));
        menuWifi1.click();

        UiScrollable settingsItem1 = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        settingsItem1.getChildByText(new UiSelector().className("android.widget.LinearLayout"), util.wifiAp1);
        device.findObject(By.clazz("android.widget.TextView").text(util.wifiAp1)).click();

        UiObject passwd = new UiObject(new UiSelector().resourceId("com.android.settings:id/password"));
        passwd.setText(util.wifiPass1);

        device.wait(Until.hasObject(By.text("Conectar")), 4000);
        UiObject2 conect = device.findObject(By.text("Conectar"));
        Thread.sleep(1000);
        conect.click();

        device.wait(Until.hasObject(By.text("Conectado")),15000);
        UiObject con1 = new UiObject(new UiSelector().text("Conectado"));
        con1.click();

        UiObject cancel = new UiObject(new UiSelector().text("Cancelar"));
        cancel.click();

        device.pressHome();





    }








}





