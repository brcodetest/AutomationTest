package positivo.qa_automation;

/**
 * Created by Emanuel on 09/12/2016.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;

import android.graphics.Rect;
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

import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactsTest {
    private UiDevice device;
    Utilities util = new Utilities();
    Random rand = new Random();
    String contactName = "Automation Contact " + rand.nextInt(10000);
    long timeout = util.timeout;


    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        util.UnlockDevice();}

    @After
    public void TearDown() throws Exception{
        Thread.sleep(1000);
    }

    @Test
    public void AddNewContact() throws Exception{
        util.OpenAppsFromMenu("Contatos");
        device.wait(Until.hasObject(By.res("com.android.contacts", "floating_action_button")), timeout);
        UiObject2 addNewContact = device.findObject(By.res("com.android.contacts", "floating_action_button"));
        addNewContact.click();

        try{
            device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Google")), timeout);
            if (device.findObject(By.clazz("android.widget.TextView").text("Google")).isEnabled()) {
                device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();
            }
        }
        catch (Exception e)
        {
            Assume.assumeTrue(1 == 1);
        }
        finally {
                //Cadastra novo contato
                device.wait(Until.hasObject(By.text("Nome")), timeout);
                device.findObject(By.text("Nome")).setText(contactName);
                device.findObject(By.clazz("android.widget.EditText").text("Telefone")).setText("41999887766");
                device.findObject(By.res("com.android.contacts", "menu_save")).click();

                Thread.sleep(2000);

                util.AllowPermissionsIfNeeded(3);
                device.pressBack();

                //Pesquisa se o contato foi cadastrado
                device.wait(Until.hasObject(By.res("com.android.contacts", "menu_search")), timeout);
                device.findObject(By.res("com.android.contacts", "menu_search")).click();

                device.wait(Until.hasObject(By.res("com.android.contacts", "search_view")), timeout);
                device.findObject(By.res("com.android.contacts", "search_view")).setText(contactName);

                device.wait(Until.hasObject(By.res("com.android.contacts", "cliv_name_textview")), timeout);
                UiObject2 searchContact = device.findObject(By.res("com.android.contacts", "cliv_name_textview"));

                String result = searchContact.getText();
                Assert.assertEquals("Nome do contato diferente do esperado. Expected: " + result + ", found: " + contactName, result, contactName);

            }
    }

    @Test
    public void ExportContact() throws Exception {

        util.OpenAppsFromMenu("Contatos");
        device.wait(Until.hasObject(By.res("com.android.contacts", "floating_action_button")), timeout);
        device.pressMenu();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Importar/exportar")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Importar/exportar")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Telefone")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();
        Thread.sleep(1500);
        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Telefone")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("PRÓXIMO")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("PRÓXIMO")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Armazenamento")), timeout);
        device.findObject(By.clazz("android.widget.TextView").textContains("Armazenamento")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("PRÓXIMO")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("PRÓXIMO")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Automation")), timeout);
        device.findObject(By.clazz("android.widget.TextView").textContains("Automation")).click();

        Thread.sleep(500);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();

        device.waitForWindowUpdate("com.android.contacts", timeout);
        util.AllowPermissionsIfNeeded(1);

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("OK")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();


        Thread.sleep(500);
        device.pressHome();
        util.SwipeNotificationBar();

        Thread.sleep(1000);

        try {
            Assert.assertTrue("Não exportou o contato!", device.findObject(By.textStartsWith("Exportação de ")).isEnabled());
        }
        catch (Exception e){
            if (e.toString().endsWith("'boolean android.support.test.uiautomator.UiObject2.isEnabled()' on a null object reference")) {
                Assert.assertTrue("Não exportou o contato!", device.findObject(By.textStartsWith("Exportação de ")).isEnabled());
            }

        }
    }

    @Test
    public void RemoveContact() throws Exception {

        util.OpenAppsFromMenu("Contatos");

        device.wait(Until.hasObject(By.res("com.android.contacts", "menu_search")), timeout);
        device.findObject(By.res("com.android.contacts", "menu_search")).click();

        device.wait(Until.hasObject(By.res("com.android.contacts", "search_view")), timeout);
        device.findObject(By.res("com.android.contacts", "search_view")).setText("Automation Contact");

         util.LongClick("resourceId", "com.android.contacts:id/cliv_name_textview", 100);

        device.pressMenu();

        device.wait(Until.hasObject(By.text("Excluir")), timeout);
        device.findObject(By.text("Excluir")).click();

        device.wait(Until.hasObject(By.text("EXCLUIR")), timeout);
        device.findObject(By.text("EXCLUIR")).click();

        Thread.sleep(1000);

        device.openNotification();
        Thread.sleep(1000);


        if(!device.hasObject(By.text("Contatos excluídos com sucesso")))
        {
            Assert.fail("Não excluiu o contato ou a mensagem está errada! Experado: 'Contatos excluídos com sucesso'");
        }


    }

    @Test
    public void ImportContact() throws Exception {
        util.OpenAppsFromMenu("Contatos");
        device.wait(Until.hasObject(By.res("com.android.contacts", "floating_action_button")), timeout);
        device.pressMenu();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Importar/exportar")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Importar/exportar")).click();

        Thread.sleep(1000);
        device.findObject(By.textStartsWith("Armazenamento interno")).click();

        device.findObject(By.text("PRÓXIMO")).click();

        Thread.sleep(1000);
        device.findObject(By.text("Telefone")).click();

        device.findObject(By.text("PRÓXIMO")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("OK")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("OK")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();

        Thread.sleep(3000);

        device.pressHome();

        util.SwipeNotificationBar();

        Assert.assertTrue("Não importou o contato!", device.findObject(By.textStartsWith("Importação do vCard ")).isEnabled());

    }
}
