package org.xpdojo.designpatterns._02_structural_patterns._02_bridge;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._02_structural_patterns._02_bridge.device.Radio;
import org.xpdojo.designpatterns._02_structural_patterns._02_bridge.device.Tv;
import org.xpdojo.designpatterns._02_structural_patterns._02_bridge.remote.AdvancedRemote;
import org.xpdojo.designpatterns._02_structural_patterns._02_bridge.remote.BasicRemote;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeTest {

    @Test
    void sut_bridge_tv() {
        Tv tv = new Tv();
        BasicRemote basicRemote = new BasicRemote(tv);
        basicRemote.power();
        tv.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(tv);
        advancedRemote.power();
        advancedRemote.mute();
        tv.printStatus();

        assertThat(tv.isEnabled()).isFalse();
        assertThat(tv.getVolume()).isZero();
    }

    @Test
    void sut_bridge_radio() {
        Radio radio = new Radio();
        BasicRemote basicRemote = new BasicRemote(radio);
        basicRemote.power();
        radio.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(radio);
        advancedRemote.volumeDown();
        advancedRemote.mute();
        radio.printStatus();

        assertThat(radio.isEnabled()).isTrue();
        assertThat(radio.getVolume()).isZero();
    }

}
