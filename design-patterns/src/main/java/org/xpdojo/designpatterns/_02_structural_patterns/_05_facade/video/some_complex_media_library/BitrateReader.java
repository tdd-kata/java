package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class BitrateReader {
    public static VideoFile read(VideoFile file, Codec codec) {
        System.out.println("BitrateReader: reading file...");
        return file;
    }

    public static VideoFile convert(VideoFile buffer, Codec codec) {
        System.out.println("BitrateReader: writing file...");
        buffer.setCodecType(codec);
        return buffer;
    }
}
