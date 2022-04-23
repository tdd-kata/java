package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoConversionFacadeTest {

    @Test
    void sut_video_conversion_facade() {
        VideoConversionFacade converter = new VideoConversionFacade();
        String actual = converter.convertVideo("youtubevideo.ogg", "mp4");
        assertThat(actual).isEqualTo("mp4");
    }

}
