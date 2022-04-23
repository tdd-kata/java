package org.xpdojo.designpatterns._02_structural_patterns._05_facade.video;

import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.AudioMixer;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.BitrateReader;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.Codec;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.CodecFactory;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.MPEG4CompressionCodec;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.OggCompressionCodec;
import org.xpdojo.designpatterns._02_structural_patterns._05_facade.video.some_complex_media_library.VideoFile;

import javax.validation.constraints.NotNull;

public class VideoConversionFacade {

    public String convertVideo(String fileName, @NotNull String format) {
        System.out.println("VideoConversionFacade: conversion started.");
        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec;

        if (format.equals("mp4")) {
            destinationCodec = new MPEG4CompressionCodec();
        } else {
            destinationCodec = new OggCompressionCodec();
        }

        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        String result = (new AudioMixer()).fix(intermediateResult);
        System.out.println("VideoConversionFacade: conversion completed.");
        return result;
    }
}
