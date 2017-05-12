package positivo.qa_automation;

/**
 * Created by Kowalczuk on 21/02/2017.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;


import junit.framework.Assert;

import java.util.Random;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingsTest {
    private UiDevice device;
    Utilities util = new Utilities();
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
    public void ScreenTimeout15s() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Tela");
        util.SwipeUntilFindElementAndClick("ListView", "Modo de espera");
        device.findObject(By.text("15 segundos")).click();

        device.pressHome();

        Thread.sleep(15500);

        Assert.assertFalse("Tela não desligou em 15 segundos!", device.isScreenOn());

    }

    @Test
    public void ScreenTimeout30s() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Tela");
        util.SwipeUntilFindElementAndClick("ListView", "Modo de espera");
        device.findObject(By.text("30 segundos")).click();

        device.pressHome();

        Thread.sleep(30500);

        Assert.assertFalse("Tela não desligou em 30 segundos!", device.isScreenOn());

    }

    @Test
    public void CreateAudioProfile() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Som e notificação");
        Thread.sleep(500);
        device.pressMenu();
        device.findObject(By.text("Adicionar")).click();

        Thread.sleep(1500);
        device.findObject(By.res("com.android.settings", "edittext")).setText("Automation Profile");
        device.findObject(By.text("OK")).click();

        Thread.sleep(500);
        device.findObject(By.desc("Configurações do dispositivo")).click();
        Thread.sleep(500);

        UiObject volumeAlarme = new UiObject(new UiSelector().resourceId("android:id/seekbar"));
        volumeAlarme.swipeLeft(100);

        device.findObject(By.text("Toque do telefone")).click();
        Thread.sleep(1000);
        util.SwipeUntilFindElementAndClick("ListView", "Cygnus");
        Thread.sleep(200);
        device.findObject(By.text("OK")).click();
        Thread.sleep(1000);

        device.pressBack();

        String profileCreated = device.findObject(By.res("com.android.settings:id/profiles_text")).getText();

        Assert.assertEquals("Não criou o perfil de áudio!", "Automation Profile", profileCreated );

    }

    @Test
    public void ResetAudioProfile() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Som e notificação");

        device.wait(Until.hasObject(By.res("com.android.settings:id/profiles_text")), timeout);
        String profileCreated = device.findObject(By.res("com.android.settings:id/profiles_text")).getText();

        device.pressMenu();
        device.findObject(By.text("Redefinir")).click();
        device.wait(Until.hasObject(By.text("OK")), timeout);
        device.findObject(By.text("OK")).click();
        device.wait(Until.hasObject(By.res("com.android.settings:id/profiles_text")), timeout);
        String firstProfile = device.findObject(By.res("com.android.settings:id/profiles_text")).getText();

        Assert.assertNotSame("Não redefeniu os perfis de áudio!", profileCreated, firstProfile);

    }

    @Test
    public void PhoneLockWithPin() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Segurança");

        device.wait(Until.hasObject(By.text("Bloqueio de tela")), timeout);
        device.findObject(By.text("Bloqueio de tela")).click();

        device.wait(Until.hasObject(By.text("PIN")), timeout);
        device.findObject(By.text("PIN")).click();

        device.wait(Until.hasObject(By.text("Continuar")), timeout);
        device.findObject(By.text("Continuar")).click();

        device.wait(Until.hasObject(By.res("com.android.settings", "password_entry")), timeout);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("1234");

        device.findObject(By.text("Continuar")).click();

        device.wait(Until.hasObject(By.res("com.android.settings", "password_entry")), timeout);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("1234");

        device.wait(Until.hasObject(By.text("OK")), timeout);
        device.findObject(By.text("OK")).click();

        device.wait(Until.hasObject(By.text("Concluído")), timeout);
        device.findObject(By.text("Concluído")).click();
        Thread.sleep(500);
        device.sleep();
        Thread.sleep(2000);

        device.wakeUp();

        UiObject tela;
        tela = new UiObject(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
        tela.swipeUp(5);

        device.wait(Until.hasObject(By.res("com.android.systemui", "pinEntry")), timeout);
        device.findObject(By.text("1")).click();
        device.findObject(By.text("2")).click();
        device.findObject(By.text("3")).click();
        device.findObject(By.text("4")).click();

        String pckLockScreen = device.getCurrentPackageName();
        device.findObject(By.desc("Enter")).click();

        Thread.sleep(1000);


        device.findObject(By.text("Bloqueio de tela")).click();
        Thread.sleep(200);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("1234");
        device.pressEnter();
        device.findObject(By.text("Nenhuma")).click();
        Thread.sleep(200);
        device.findObject(By.text("Sim, remover")).click();

        device.pressHome();
        String pckUnlocked = device.getCurrentPackageName();

        assertNotEquals("Não desbloqueou a tela!", pckLockScreen, pckUnlocked);

    }

    @Test
    public void PhoneLockWithPwd() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Segurança");

        device.wait(Until.hasObject(By.text("Bloqueio de tela")), timeout);
        device.findObject(By.text("Bloqueio de tela")).click();

        device.wait(Until.hasObject(By.text("Senha")), timeout);
        device.findObject(By.text("Senha")).click();

        device.wait(Until.hasObject(By.text("Continuar")), timeout);
        device.findObject(By.text("Continuar")).click();

        device.wait(Until.hasObject(By.res("com.android.settings", "password_entry")), timeout);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("abcde");

        device.findObject(By.text("Continuar")).click();

        device.wait(Until.hasObject(By.res("com.android.settings", "password_entry")), timeout);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("abcde");

        device.wait(Until.hasObject(By.text("OK")), timeout);
        device.findObject(By.text("OK")).click();

        device.wait(Until.hasObject(By.text("Concluído")), timeout);
        device.findObject(By.text("Concluído")).click();

        Thread.sleep(1000);

        device.sleep();
        Thread.sleep(2000);

        device.wakeUp();

        UiObject tela;
        tela = new UiObject(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
        tela.swipeUp(5);

       device.findObject(By.res("com.android.systemui:id/passwordEntry")).setText("abcde");

        String pckLockScreen = device.getCurrentPackageName();
        device.pressEnter();

        Thread.sleep(1000);

        device.findObject(By.text("Bloqueio de tela")).click();
        Thread.sleep(200);
        device.findObject(By.res("com.android.settings", "password_entry")).setText("abcde");
        device.pressEnter();
        device.findObject(By.text("Nenhuma")).click();
        Thread.sleep(200);
        device.findObject(By.text("Sim, remover")).click();


        device.pressHome();
        String pckUnlocked = device.getCurrentPackageName();

        assertNotEquals("Não desbloqueou a tela!", pckLockScreen, pckUnlocked);

    }

    @Test
    public void PhoneLockWithSwipe() throws Exception{
        util.OpenAppsFromMenu("Configurar");
        util.SwipeUntilFindElementAndClick("ScrollView", "Segurança");

        device.wait(Until.hasObject(By.text("Bloqueio de tela")), timeout);
        device.findObject(By.text("Bloqueio de tela")).click();

        device.pressEnter();

        device.wait(Until.hasObject(By.text("Deslizar")), timeout);
        device.findObject(By.text("Deslizar")).click();

        Thread.sleep(500);
        device.sleep();
        Thread.sleep(2000);

        device.wakeUp();

        UiObject tela;
        tela = new UiObject(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
        tela.swipeUp(5);

        String pckLockScreen = device.getCurrentPackageName();
        device.pressEnter();

        Thread.sleep(1000);
        device.pressHome();
        String pckUnlocked = device.getCurrentPackageName();

        assertNotEquals("Não desbloqueou a tela!", pckLockScreen, pckUnlocked);

    }


}
