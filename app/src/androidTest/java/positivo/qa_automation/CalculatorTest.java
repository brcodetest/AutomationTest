package positivo.qa_automation;

/**
 * Created by Emanuel on 24/11/2016.
 */

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

    @Test
    public void TestMakeBasicOperations() throws Exception {

        util.OpenAppsFromMenu("Calculadora");

        device.wait(Until.hasObject(By.text("9")), 1000);

        UiObject2 buttonNine = device.findObject(By.text("9"));

        buttonNine.click();

        device.findObject(By.desc("mais")).click();

        buttonNine.click();

        UiObject2 buttonEquals = device.findObject(By.desc("igual"));

        buttonEquals.click();

        device.waitForIdle(1000);

        UiObject2 resultText = device.findObject(By.clazz("android.widget.EditText"));
        String result = resultText.getText();

        assertTrue("Calculadora retornou valor diferente do esperado!", result.equals("18"));

    }

    }



