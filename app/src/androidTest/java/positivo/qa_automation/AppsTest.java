package positivo.qa_automation;

import android.content.Context;
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

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Kowalczuk on 23/02/2017.
 */


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppsTest {
    private UiDevice device;
    Utilities util = new Utilities();
    long timeout = util.timeout;
    Context context;

    @Before
    public void SetUp() throws Exception {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();
    }

    @After
    public void TearDown() throws Exception{
        Thread.sleep(1000);
    }

    @Test
    public void OpenAllAppsFromMenu() throws Exception{

        //Abre menu de apps
        device.pressHome();
        device.wait(Until.hasObject(By.desc("Apps")), timeout);
        device.findObject(By.desc("Apps")).click();
        Thread.sleep(500);

        //Cria lista para receber os apps
        List<UiObject2> listApps = device.findObject(By.res("com.android.launcher3","apps_list_view")).getChildren();
        List<String> listName = new ArrayList<String>();

        //Joga todos os elementos encontrados na listName
        for (UiObject2 a: listApps ) {
            listName.add(a.getText());
        }

        //Pega o ultimo app encontrado
        int index = listName.size();
        String lastApp = listName.get(index -1);

        //Faz scroll até que o ultimo app não esteja mais visivel
        int i = 0;
        while(device.findObject(By.text(lastApp)).isEnabled()) {
            UiScrollable settingsItem = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
            settingsItem.swipeUp(230);
            i++;
            if(i == 2)
            {
                break;
            }
        }

        //Refaz o processo para pegar os apps e jogar na listName
        List<UiObject2> listApps2 = device.findObject(By.res("com.android.launcher3","apps_list_view")).getChildren();
        for (UiObject2 a: listApps2 ) {
            listName.add(a.getText());
        }

        //Cria listAllApps sem os apps duplicados
        List listAllApps = new ArrayList(new HashSet(listName));


        List<String> listAppsFail =  new ArrayList<String>();


        for(int z = 0; z < listAllApps.size(); z++)
        {

            util.OpenAppsFromMenu(listAllApps.get(z).toString());
            Thread.sleep(8000);

            if(device.hasObject(By.text("O aplicativo " + listAllApps.get(z).toString() + " parou.")))
            {
                listAppsFail.add(listAllApps.get(z).toString());

                if(device.hasObject(By.text("OK")))
                {
                    device.findObject(By.text("OK")).click();
                }
                else{
                    device.pressBack();
                }
            }
        }


        if(listAppsFail.size() > 0) {
            Assert.fail("Os seguintes apps falharam ao abrir: " + listAppsFail.toString());
        }


    }

}
