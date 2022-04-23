package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class CodecFactory {
    public static Codec extract(VideoFile file) {
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            System.out.println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        }

        if (type.equals("ogg")) {
            System.out.println("CodecFactory: extracting ogg audio...");
            return new OggCompressionCodec();
        }
        return null;
    }
}
