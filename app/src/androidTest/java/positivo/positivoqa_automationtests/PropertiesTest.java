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
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.text.DateFormat;

import static org.junit.Assert.assertTrue;

/**
 * Created by Emanuel on 16/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PropertiesTest {
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

    @Test
    public void CheckFingerprint(){
        String bootimageFP = util.AdbCommand("getprop ro.bootimage.build.fingerprint");
        String buildFP = util.AdbCommand("getprop ro.build.fingerprint");
        Assert.assertEquals("bootimage.fingerprint é diferente do build.fingerprint", bootimageFP, buildFP);
    }

    @Test
    public void CheckClientID(){
        String clientId = util.AdbCommand("getprop ro.com.google.clientidbase").toString();
        Assert.assertEquals("ClientId com valor inexperado.", clientId, "android-positivo");
    }

    @Test
    public void CheckSecurityPatchShelfLife(){
        String securityPatchDate = util.AdbCommand("getprop ro.build.version.security_patch");

        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dtt = df.parse(securityPatchDate);
            Date dataSecurityPath = new java.sql.Date(dtt.getTime());

            Date validadeSecurityPath = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(validadeSecurityPath);
            c.add(Calendar.DATE, -90);
            validadeSecurityPath = c.getTime();

            if(dataSecurityPath.before(validadeSecurityPath))
            {
                Assert.fail("Security patch vencido!");
            }
        }
        catch (Exception e){
            System.out.print(e.toString());
        }

    }

    @Test
    public  void CheckSecurityPathInAbout(){
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("Sobre o telefone");
        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Nível do patch")), timeout);
        UiObject2 sp = device.findObject(By.clazz("android.widget.TextView").textContains("Nível do patch"));
        Assert.assertTrue("Security path não encontrado no menu Sobre", sp.isEnabled());

    }

    @Test
    public void CheckModelNumberInAbout() throws InterruptedException{
        String modelQuantum = "";
        String modelPositivo = "";


        String modelNumber = util.AdbCommand("getprop ro.product.model");

        util.OpenAppsFromMenu("Configurar");
        Thread.sleep(500);
        util.SwipeUntilFindElementAndClick("Sobre o telefone");
        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Número do modelo")), timeout);

        try {
            modelQuantum = device.findObject(By.clazz("android.widget.TextView").textStartsWith("Quantum")).getText();
            modelPositivo = device.findObject(By.clazz("android.widget.TextView").textStartsWith("Positivo")).getText();
        }
        catch (Exception e){
            Assert.assertEquals(1, 1);
        }

        String modelAbout = "";

        if(modelQuantum != ""){
            modelAbout = modelQuantum;
        }
        if(modelPositivo != ""){
            modelAbout = modelPositivo;
        }
        Assert.assertEquals("ro.product.model é diferente do 'Sobre' o telefone", modelNumber, modelAbout);
    }


}
