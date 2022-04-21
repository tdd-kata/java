package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory.GUIFactory;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory.MacFactory;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory.WindowsFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class ApplicationConfiguratorTest {

    @Test
    void sut_windows_button() {

        // String osName = System.getProperty("os.name");
        String osName = "Windows 10";

        GUIFactory factory = getGuiFactory(osName);

        Application app = new Application(factory);
        app.createUI();

        assertThat(app.paint()).isEqualTo("Windows button");
        assertThat(app.isChecked()).isTrue();
    }

    @Test
    void sut_mac_button() {

        String osName = "Mac";

        GUIFactory factory = getGuiFactory(osName);

        Application app = new Application(factory);
        app.createUI();

        assertThat(app.paint()).isEqualTo("Mac button");
        assertThat(app.isChecked()).isFalse();
    }

    private GUIFactory getGuiFactory(String osName) {
        GUIFactory factory;
        switch (osName) {
            case "Windows 10":
                factory = new WindowsFactory();
                break;
            case "Mac":
                factory = new MacFactory();
                break;
            default:
                factory = null;
                fail("Unknown OS: " + osName);
        }
        return factory;
    }

}
