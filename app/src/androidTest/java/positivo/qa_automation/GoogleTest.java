package positivo.qa_automation;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;


import junit.framework.Assert;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

/**
 * Created by Emanuel on 14/12/2016.
 */

@RunWith(AndroidJUnit4.class)
public class GoogleTest {
    private UiDevice device;
    Utilities util = new Utilities();
    Context context;
    long timeout = util.timeout;

   /*public GoogleTest(Context context) {
        this.context = context;
    }*/

    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();}

    @Test
    public void LoginGoogleAccount() throws Exception {

        if(util.CheckInternetConnection(this.context) == true)
        {
            if(util.ValidateGMSversion(context) == true){

                util.OpenAppsFromMenu("Configurar");
                util.SwipeUntilFindElementAndClick("Contas");

                device.wait(Until.hasObject(By.text("Adicionar conta")), 4000);
                UiObject2 adConta = device.findObject(By.text("Adicionar conta"));
                adConta.click();

                sleep(2000);

                device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Google")), timeout);
                UiObject2 selGoogle = device.findObject(By.clazz("android.widget.TextView").text("Google"));
                selGoogle.click();

                sleep(10000);
                device.waitForWindowUpdate("com.google.android.gms", 10000);
                device.wait(Until.hasObject(By.res("com.google.android.gms", "identifierId")), timeout);
                device.findObject(By.res("com.google.android.gms", "identifierId")).click();
                device.findObject(By.res("com.google.android.gms", "identifierId")).setText("brcodetest");

                sleep(2000);

                device.wait(Until.hasObject(By.res("identifierNext")), timeout);
                device.findObject(By.res("identifierNext")).click();

                sleep(10000);

                device.wait(Until.hasObject(By.res("com.google.android.gms", "password")), timeout);
                device.findObject(By.res("com.google.android.gms", "password")).click();
                device.findObject(By.res("com.google.android.gms", "password")).setText("brc0d3test");


                device.wait(Until.hasObject(By.desc("PRÓXIMA")), timeout);
                UiObject2 passNxt = device.findObject(By.desc("PRÓXIMA"));
                passNxt.click();

                Thread.sleep(5000);

                device.wait(Until.hasObject(By.desc("ACEITAR")), 3000);
                UiObject2 acntNxt = device.findObject(By.desc("ACEITAR"));
                acntNxt.click();

                device.waitForWindowUpdate("com.google.android.gms", 20000);

                device.wait(Until.hasObject(By.text( "Próximo")), timeout);
                UiObject2 prox = device.findObject(By.text("Próximo"));
                prox.click();

             }
            else
            {
                Assert.fail("Versão do GMS está desatualizada!");
            }
        }

        else
        {
            Assert.fail("Sem conexão com a internet!");
        }


    }
}