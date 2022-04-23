package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class AudioMixer {
    public String fix(VideoFile result) {
        System.out.println("AudioMixer: fixing audio...");
        return result.getCodecType();
    }
}
