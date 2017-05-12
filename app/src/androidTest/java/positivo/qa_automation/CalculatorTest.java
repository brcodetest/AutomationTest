package positivo.qa_automation;

/**
 * Created by Emanuel on 24/11/2016.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;


import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CalculatorTest {
    private UiDevice device;
    Utilities util = new Utilities();

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        util.UnlockDevice();
}

    @After
    public void TearDown() throws Exception{

        Thread.sleep(1000);
    }

    @Test
    public void MakeBasicOperations() throws Exception {

        util.ClearAppData("com.google.android.calculator");
        util.ClearAppData("com.google.android.calculator2");

        util.OpenAppsFromMenu("Calculadora");

        Thread.sleep(1000);

        device.findObject(By.text("9")).click();

        device.findObject(By.desc("mais")).click();

        device.findObject(By.text("9")).click();

        device.findObject(By.desc("igual")).click();

        Thread.sleep(1000);

        String result;

        if(!device.hasObject(By.res("com.android.calculator2:id/formula")))
        {
            result = device.findObject(By.res("com.android.calculator2:id/result")).getText();
        }
        else
        {
            result = device.findObject(By.res("com.android.calculator2:id/formula")).getText();
        }

        assertTrue("Calculadora retornou valor diferente do esperado!", result.equals("18"));

    }

    }



