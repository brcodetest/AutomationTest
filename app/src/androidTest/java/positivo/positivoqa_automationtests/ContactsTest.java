package positivo.positivoqa_automationtests;

/**
 * Created by Emanuel on 09/12/2016.
 */
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
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
    public void CleanUp(){
        device.pressHome();

    }

    @Test
    public void CreateNewContact(){
        util.OpenAppsFromMenu("Contatos");
        device.wait(Until.hasObject(By.res("com.android.contacts", "floating_action_button")), timeout);
        UiObject2 addNewContact = device.findObject(By.res("com.android.contacts", "floating_action_button"));
        addNewContact.click();

        try{
            device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Google")), timeout);
            if (device.findObject(By.clazz("android.widget.TextView").text("Google")).isEnabled()) {
                device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();
            }}
        catch (Exception e)
        {
            Assume.assumeTrue(1 ==1);
        }


        finally {
                //Cadastra novo contato
                device.wait(Until.hasObject(By.text("Nome")), timeout);
                device.findObject(By.text("Nome")).setText(contactName);
                device.findObject(By.clazz("android.widget.EditText").text("Telefone")).setText("41999887766");
                device.findObject(By.clazz("android.widget.EditText").text("E-mail")).setText("automation@positivo.com.br");
                device.findObject(By.res("com.android.contacts", "menu_save")).click();
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
    public void ExportContact() throws InterruptedException {
        try{
            util.AdbCommand("pm clear com.android.contacts");
        }
        catch (Exception e){
            System.out.print(e.toString());
        }

        util.OpenAppsFromMenu("Contatos");
        device.wait(Until.hasObject(By.res("com.android.contacts", "floating_action_button")), timeout);
        device.findObject(By.descContains("Mais")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Importar/exportar")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Importar/exportar")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Telefone")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();
        Thread.sleep(1500);
        device.wait(Until.hasObject(By.clazz("android.widget.TextView").text("Telefone")), timeout);
        device.findObject(By.clazz("android.widget.TextView").text("Telefone")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("Próximo")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("Próximo")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Armazenamento")), timeout);
        device.findObject(By.clazz("android.widget.TextView").textContains("Armazenamento")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("Próximo")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("Próximo")).click();

        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Automation")), timeout);
        device.findObject(By.clazz("android.widget.TextView").textContains("Automation")).click();

        Thread.sleep(1000);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();


        device.waitForWindowUpdate("com.android.contacts", timeout);
        util.AllowPermissionsIfNeeded();

        device.wait(Until.hasObject(By.clazz("android.widget.Button").text("OK")), timeout);
        device.findObject(By.clazz("android.widget.Button").text("OK")).click();

        util.OpenAppsFromMenu("Gerenciador de arquivos");
        device.wait(Until.hasObject(By.clazz("android.widget.TextView").textContains("Armazenamento")), timeout);
        device.findObject(By.clazz("android.widget.TextView").textContains("Armazenamento")).click();

        device.swipe(600, 1000, 600, 300, 2);
        String fileName = device.findObject(By.clazz("android.widget.TextView").textEndsWith("vcf")).getText();

        Assert.assertEquals("Arquivo não encontrado, contato não exportado", "vcf", fileName.endsWith("vcf"));


    }

    @Test
    public  void ImportContact(){}
}
