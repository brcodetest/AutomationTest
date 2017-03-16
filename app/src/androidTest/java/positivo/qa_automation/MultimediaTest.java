package positivo.qa_automation;

/**
 * Created by Kowalczuk on 23/02/2017.
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
import android.content.Context;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import junit.framework.Assert;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultimediaTest {
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
    public void AudioStreamingAndDownload() throws Exception {

        util.ClearAppData("com.android.chrome");

        try {

            if (util.CheckInternetConnection(this.context) == true) {
                util.OpenAppsFromMenu("Chrome");
                device.wait(Until.hasObject(By.text("ACEITAR E CONTINUAR")), timeout);
                device.findObject(By.text("ACEITAR E CONTINUAR")).click();

                device.wait(Until.hasObject(By.text("NÃO, OBRIGADO")), timeout);
                device.findObject(By.text("NÃO, OBRIGADO")).click();


                device.wait(Until.hasObject(By.text("Pesquisar ou digitar URL")), timeout);
                device.findObject(By.text("Pesquisar ou digitar URL")).click();
                Thread.sleep(500);
                device.findObject(By.text("Pesquisar ou digitar URL")).setText("http://gsmworld.mobi/blm/downloads/music.mp3");
                device.pressEnter();

                Thread.sleep(1000);

                if(!device.hasObject(By.desc("fazer o download da mídia controle de mídia")))
                {
                    Assert.fail("Versão muito antiga do Chrome!!! Atualize também o Google Play Services. Outros testes de multimedia talvez falharam. ");
                }

                device.wait(Until.hasObject(By.desc("reproduzir iniciar reprodução")), 10000);
                device.findObject(By.desc("reproduzir iniciar reprodução")).click();

                Thread.sleep(5000);

                device.findObject(By.desc("fazer o download da mídia controle de mídia")).click();

                device.wait(Until.hasObject(By.text("ATUALIZAR PERMISSÕES")), timeout);
                if(device.hasObject(By.text("ATUALIZAR PERMISSÕES"))) {
                    device.findObject(By.text("ATUALIZAR PERMISSÕES")).click();
                    util.AllowPermissionsIfNeeded(1);
                }

                device.openNotification();

                device.wait(Until.hasObject(By.desc("Pausar")), timeout);
                device.findObject(By.desc("Pausar")).click();

                device.wait(Until.hasObject(By.text("Download concluído")), 25000);

                util.OpenAppsFromMenu("Gerenciador de arquivos");
                device.findObject(By.textStartsWith("Armazenamento interno")).click();
                util.SwipeUntilFindElementAndClick("ListView", "Download");


                if(!device.hasObject(By.text("music.mp3")))
                {
                    Assert.fail("Não encontrou a música, provavelmente a conexão com a internet está muito lenta!");
                }
            }
            else {
                Assert.fail("Sem conexão com a internet!");
            }
        }

        catch (Exception e)
        {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void VideoStreaming() throws Exception {

        util.ClearAppData("com.android.chrome");

            if (util.CheckInternetConnection(this.context) == true) {
                util.OpenAppsFromMenu("Chrome");
                device.wait(Until.hasObject(By.text("ACEITAR E CONTINUAR")), timeout);
                device.findObject(By.text("ACEITAR E CONTINUAR")).click();

                device.wait(Until.hasObject(By.text("NÃO, OBRIGADO")), timeout);
                device.findObject(By.text("NÃO, OBRIGADO")).click();

                device.wait(Until.hasObject(By.text("Pesquisar ou digitar URL")), timeout);
                device.findObject(By.text("Pesquisar ou digitar URL")).click();
                Thread.sleep(500);
                device.findObject(By.text("Pesquisar ou digitar URL")).setText("http://gsmworld.mobi/blm/downloads/video_stream_720p_a.mp4");
                device.pressEnter();

                Thread.sleep(1000);
                device.wait(Until.hasObject(By.desc("reproduzir iniciar reprodução")), 10000);
                device.findObject(By.desc("reproduzir iniciar reprodução")).click();

                Thread.sleep(5000);

                device.openNotification();

                device.wait(Until.hasObject(By.desc("Pausar")), timeout);
                device.findObject(By.desc("Pausar")).click();

            }
            else {
                Assert.fail("Sem conexão com a internet!");
            }

    }

    @Test
    public void AudioSuspendAndResume()  throws Exception{

        util.OpenAppsFromMenu("Gerenciador de arquivos");
        device.findObject(By.textStartsWith("Armazenamento interno")).click();
        util.SwipeUntilFindElementAndClick("ListView", "Download");

        if(!device.hasObject(By.text("music.mp3")))
        {
            Assert.fail("Não encontrou a música!");
        }
        device.findObject(By.text("music.mp3")).click();
        Thread.sleep(1000);

        if(device.hasObject(By.text("Google Play Música"))){
         device.findObject(By.text("Google Play Música")).click();
        }

        device.findObject(By.text("SÓ UMA VEZ")).click();

        Thread.sleep(2000);
        device.findObject(By.res("com.google.android.music:id/play_pause_button")).click();

        Thread.sleep(2000);
        device.findObject(By.res("com.google.android.music:id/play_pause_button")).click();

        if(!device.hasObject(By.text("Working Life")))
        {
            Assert.fail("Não encontrou a música!");
        }

    }

    @Test
    public void DeleteSongs() throws Exception {
        util.OpenAppsFromMenu("Gerenciador de arquivos");
        device.findObject(By.textStartsWith("Armazenamento interno")).click();
        util.SwipeUntilFindElementAndClick("ListView", "Download");

        if(!device.hasObject(By.text("music.mp3")))
        {
            Assert.fail("Não encontrou a música!");
        }

        util.LongClick("text", "music.mp3", 100);

        device.findObject(By.desc("Excluir")).click();

        device.wait(Until.hasObject(By.text("OK")), timeout);
        device.findObject(By.text("OK")).click();

        if(device.hasObject(By.text("music.mp3")))
        {
            Assert.fail("Não deletou a música!");
        }

    }

    @Test
    public void SoundRecorder_Record() throws Exception{
        util.ClearAppData("com.android.soundrecorder");

        util.OpenAppsFromMenu("Gravador de som");

        device.wait(Until.hasObject(By.res("com.android.soundrecorder:id/recordButton")), timeout);
        device.findObject(By.res("com.android.soundrecorder:id/recordButton")).click();
        util.AllowPermissionsIfNeeded(2);

        UiObject stop;
        stop = new UiObject(new UiSelector().resourceId("com.android.soundrecorder:id/stopButton"));
        stop.click();

        device.wait(Until.hasObject(By.text("SALVAR")), 25000);
        device.findObject(By.text("SALVAR")).click();

        device.wait(Until.hasObject(By.res("com.android.soundrecorder", "fileListButton")), timeout);
        device.findObject(By.res("com.android.soundrecorder", "fileListButton")).click();

        if(!device.hasObject(By.textEndsWith(".3gpp")))
        {
            Assert.fail("Não encontrou a gravação");
        }

    }

    @Test
    public void SoundRecorder_Remove() throws Exception{
        util.ClearAppData("com.android.soundrecorder");
        util.OpenAppsFromMenu("Gravador de som");

        device.wait(Until.hasObject(By.res("com.android.soundrecorder", "fileListButton")), timeout);
        device.findObject(By.res("com.android.soundrecorder", "fileListButton")).click();

        util.AllowPermissionsIfNeeded(1);

        Thread.sleep(500);
        if(!device.hasObject(By.textEndsWith(".3gpp")))
        {
            Assert.fail("Não encontrou a gravação");
        }

        util.LongClick("resourceId", "com.android.soundrecorder:id/record_file_name", 100);

        device.wait(Until.hasObject(By.res("com.android.soundrecorder", "deleteButton")), timeout);
        device.findObject(By.res("com.android.soundrecorder", "deleteButton")).click();

        device.wait(Until.hasObject(By.text("OK")), timeout);
        device.findObject(By.text("OK")).click();

        Thread.sleep(1000);
        if(!device.hasObject(By.text("(Sem arquivo de gravação)")))
        {
            Assert.fail("Não encontrou arquivos para deletar!");
        }

    }

    @Test
    public void DeletePictures() throws Exception{
        util.ClearAppData("com.mediatek.filemanager");

        util.OpenAppsFromMenu("Câmera");

        Thread.sleep(2000);
        device.findObject(By.res("com.mediatek.camera", "shutter_button_photo")).click();
        Thread.sleep(1000);

        util.OpenAppsFromMenu("Gerenciador de arquivos");
        device.findObject(By.textStartsWith("Armazenamento interno")).click();
        util.SwipeUntilFindElementAndClick("ListView", "DCIM");
        device.findObject(By.text("Camera")).click();

        device.pressMenu();

        device.findObject(By.text("Ordenar por")).click();
        device.findObject(By.textStartsWith("Tempo")).click();

        util.LongClick("resourceId", "com.mediatek.filemanager:id/edit_adapter_img", 100);


        device.findObject(By.desc("Excluir")).click();

        util.AllowPermissionsIfNeeded(1);

        device.findObject(By.text("OK")).click();

    }



}
