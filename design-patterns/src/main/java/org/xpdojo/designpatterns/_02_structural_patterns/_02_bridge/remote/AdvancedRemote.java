package org.xpdojo.designpatterns._02_structural_patterns._02_bridge.remote;

import org.xpdojo.designpatterns._02_structural_patterns._02_bridge.device.Device;

public class AdvancedRemote extends BasicRemote {

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
