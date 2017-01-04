package positivo.positivoqa_automationtests;

import android.content.Context;
import android.support.test.espresso.core.deps.dagger.Component;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;


import junit.framework.Assert;

import java.io.IOError;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

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
    public void LoginGoogleAccount(){
        if(util.CheckInternetConnection(this.context) == true)
        {
            util.OpenAppsFromMenu("Configurar");
            util.SwipeUntilFindElementAndClick("Contas");
            device.findObject(By.clazz("android.widget.TextView").textStartsWith("Adicionar")).click();

            device.findObject(By.clazz("android.widget.TextView").text("Google")).click();
            device.waitForWindowUpdate("com.google.android.gms", timeout);

            device.findObject(By.res("com.google.android.gms", "identifierId")).setText("brcodetest");

            device.wait(Until.hasObject(By.res("com.google.android.gms", "identifierNext")), timeout);
            device.findObject(By.res("com.google.android.gms", "identifierNext")).click();

            device.wait(Until.hasObject(By.res("com.google.android.gms", "password")), timeout);
            device.findObject(By.res("com.google.android.gms", "password")).setText("brc0d3test");

            device.wait(Until.hasObject(By.res("com.google.android.gms", "passwordNext")), timeout);
            device.findObject(By.res("com.google.android.gms", "passwordNext")).click();

            device.wait(Until.hasObject(By.res("com.google.android.gms", "next")), timeout);
            device.findObject(By.res("com.google.android.gms", "next")).click();

        }

        else
        {
            Assert.fail("Sem conexão com a internet!");
        }

    }
}
