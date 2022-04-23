package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library;

public class VideoFile {
    private String name;
    private String codecType;

    public VideoFile(String name) {
        this.name = name;
        this.codecType = name.substring(name.indexOf(".") + 1);
    }

    public String getCodecType() {
        return codecType;
    }

    public String getName() {
        return name;
    }

    public void setCodecType(Codec codec) {
        this.codecType = codec.getType();
    }
}
