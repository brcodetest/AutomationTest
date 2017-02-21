package positivo.qa_automation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by Kowalczuk on 16/02/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmTest{

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

    @After
    public void TearDown() throws Exception{

        Thread.sleep(1000);
    }

    @Test
    public void CreateAlarm() throws  Exception{
        util.OpenAppsFromMenu("Relógio");

        util.AllowPermissionsIfNeeded();

        device.wait(Until.hasObject(By.desc("Alarme")), timeout);
        device.findObject(By.desc("Alarme")).click();

        device.findObject(By.desc("Adicionar alarme")).click();

        device.wait(Until.hasObject(By.desc("15")), timeout);

        device.findObject(By.desc("15")).click();

        device.findObject(By.desc("15")).click();

        device.findObject(By.text("OK")).click();

        Thread.sleep(500);

        String time = device.findObject(By.descStartsWith("15")).getText();

        Assert.assertEquals("Alarme não encontrado!", time, "15:15");

    }

    @Test
    public void DeleteAlarm() throws Exception{

        Thread.sleep(800);

        device.findObject(By.descStartsWith("Expandir alarme")).click();

        Thread.sleep(800);

        device.findObject(By.desc("Excluir alarme")).click();

        Thread.sleep(800);

        device.findObject(By.descStartsWith("Expandir alarme")).click();

        Thread.sleep(800);

        device.findObject(By.desc("Excluir alarme")).click();

        Thread.sleep(800);

        device.findObject(By.descStartsWith("Expandir alarme")).click();

        Thread.sleep(800);

        device.findObject(By.desc("Excluir alarme")).click();

        String alarme = device.findObject(By.descStartsWith("Nenhum alarme")).getText();

        Assert.assertEquals("Alarme não excluído!", alarme, "Nenhum alarme");


    }

}
