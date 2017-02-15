package positivo.positivoqa_automationtests;

/**
 * Created by Kowalczuk on 14/02/2017.
 */

import android.content.Context;
import android.graphics.Rect;
import android.hardware.camera2.CameraManager;
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
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;


import junit.framework.Assert;

import java.io.IOError;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CameraTest {
    private UiDevice device;
    Utilities util = new Utilities();
    Context context;
    long timeout = util.timeout;
    private int cameraId;

    @Before
    public void SetUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        util.UnlockDevice();}

    @Test
    public void FrontCamera_ContinuousShot(){
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        try {

            device.wait(Until.hasObject(By.desc("Escolher câmera")), timeout);
            device.findObject(By.desc("Escolher câmera")).click();

            Thread.sleep(500);
            SelectResolutions("QVGA", "Configurações para foto");
            device.pressBack();

            UiObject CameraButton = new UiObject(new UiSelector().description("Tirar foto"));
            Rect CameraButton_rect = CameraButton.getBounds();
            device.swipe(CameraButton_rect.centerX(), CameraButton_rect.centerY(), CameraButton_rect.centerX(), CameraButton_rect.centerY(), 100);

            Thread.sleep(10000);

        }
        catch (Exception e)
        {
            Assert.fail(e.toString());
        }

    }

    @Test
    public void FrontCamera_RecordVideoWithAllResolutions() throws Exception{
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        try {

            Thread.sleep(500);

            device.wait(Until.hasObject(By.res("com.mediatek.camera", "setting_indicator")), timeout);

            SelectResolutions("320P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder


            Thread.sleep(300);

            SelectResolutions("480P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);

            SelectResolutions("720P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);

            SelectResolutions("1080P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);
        }
        catch (Exception e)
        {
            Assert.fail(e.toString());

        }

    }

    @Test
    public void FrontCamera_TakePicturesWithAllResolutions() throws Exception{
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        Thread.sleep(500);

        device.wait(Until.hasObject(By.res("com.mediatek.camera", "setting_indicator")), timeout);

        SelectResolutions("QVGA", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("VGA", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("1 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("2 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("3 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("4 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("5 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("8 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("13 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("16 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("Quantum", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();
    }

    @Test
    public void RearCamera_ContinuousShot(){
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        try {

            device.wait(Until.hasObject(By.desc("Escolher câmera")), timeout);
            device.findObject(By.desc("Escolher câmera")).click();

            device.wait(Until.hasObject(By.res("com.mediatek.camera", "com.mediatek.camera:id/shutter_button_photo")), timeout);

            SelectResolutions("QVGA", "Configurações para foto");
            device.pressBack();

            UiObject CameraButton = new UiObject(new UiSelector().description("Tirar foto"));
            Rect CameraButton_rect = CameraButton.getBounds();
            device.swipe(CameraButton_rect.centerX(), CameraButton_rect.centerY(), CameraButton_rect.centerX(), CameraButton_rect.centerY(), 400);

            Thread.sleep(10000);

        }
        catch (Exception e)
        {
            Assert.fail(e.toString());
        }

    }

    @Test
    public void RearCamera_RecordVideoWithAllResolutions() throws Exception{
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        try {


            device.wait(Until.hasObject(By.res("com.mediatek.camera", "setting_indicator")), timeout);

            SelectResolutions("320P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder


            Thread.sleep(300);

            SelectResolutions("480P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);

            SelectResolutions("720P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);

            SelectResolutions("1080P", "Configurações de vídeo");
            device.pressBack();
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Start video recorder
            Thread.sleep(5000);
            device.findObject(By.res("com.mediatek.camera", "shutter_button_video")).click(); //Stop video recorder

            Thread.sleep(300);
        }
        catch (Exception e)
        {
            Assert.fail(e.toString());

        }

    }

    @Test
    public void RearCamera_TakePicturesWithAllResolutions() throws Exception{
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();


        device.wait(Until.hasObject(By.res("com.mediatek.camera", "setting_indicator")), timeout);

        SelectResolutions("QVGA", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("VGA", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("1 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("2 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("3 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("4 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("5 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("8 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("13 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("16 MP", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

        Thread.sleep(300);

        SelectResolutions("Quantum", "Configurações para foto");
        device.pressBack();
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();
    }

    @Test
    public void SwitchCamerasSeveralTimes() throws Exception {
        util.OpenAppsFromMenu("Câmera");
        util.AllowPermissionsIfNeeded();

        try {

            Thread.sleep(500);

            for(int i = 0; i <= 15; i++){

                device.wait(Until.hasObject(By.res("com.mediatek.camera", "shutter_button_photo")), timeout);
                device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();

                device.wait(Until.hasObject(By.desc("Escolher câmera")), timeout);
                device.findObject(By.desc("Escolher câmera")).click();
            }
        }
        catch (Exception e)
        {
            Assert.fail(e.toString());
        }


    }











    private void SelectResolutions(String textResolution, String tipo){
        String element = "Tamanho da imagem";

        if(tipo == "Configurações de vídeo")
        {
            element = "Qualidade do vídeo";
        }

        try{

            device.findObject(By.res("com.mediatek.camera", "setting_indicator")).click();
            device.findObject(By.desc(tipo)).click();
            // device.findObject(new UiSelector(). className("android.widget.ImageView").index(1).resourceId("tabs")).click();

            Thread.sleep(500);
            UiScrollable imageSize = new UiScrollable(new UiSelector().className("android.widget.ListView"));
            imageSize.getChildByText(new UiSelector().className("android.widget.TextView"), element);
            device.findObject(By.clazz("android.widget.TextView").text(element)).click();

            UiScrollable res = new UiScrollable(new UiSelector().className("android.widget.ListView"));
            res.getChildByText(new UiSelector().className("android.widget.TextView"), textResolution);
            device.findObject(By.clazz("android.widget.TextView").textStartsWith(textResolution)).click();

        }
        catch (Exception e){
            System.out.print(e.toString());
        }

    }




}
