package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class OggCompressionCodec implements Codec {
    public String type = "ogg";

    @Override
    public String getType() {
        return type;
    }
}
