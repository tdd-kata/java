package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class MPEG4CompressionCodec implements Codec {
    public String type = "mp4";

    @Override
    public String getType() {
        return type;
    }
}
