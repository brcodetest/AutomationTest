package positivo.qa_automation;

/**
 * Created by Kowalczuk on 21/02/2017.
 */

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CalculatorTest.class,
        AlarmTest.class,
        CameraTest.class,
        ContactsTest.class,
        WifiTest.class,
        GoogleTest.class,
        HomeTest.class,
        MultimediaTest.class,
        PropertiesTest.class,
        SettingsTest.class,
        AppsTest.class

})

public class AllTests  extends TestSuite{

    public static Test suite(){
        return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
    }
}
