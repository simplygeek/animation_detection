package com.ashish.image.detection;

import javax.activation.MimeTypeParseException;

import com.google.common.net.MediaType;
import com.madgag.gif.fmsware.GifDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by bhardwaj on 5/31/19
 * //https://ezgif.com/loop-count for generating sample image with varying loop count
 */
public class AnimationDetector {

    private MediaType getMediaType(InputStream inputStream) throws IOException, MimeTypeParseException {
        String contentType = URLConnection.guessContentTypeFromStream(inputStream);
        return MediaType.parse(contentType);
    }

    public InputStream readFileFromClassPath(String fileName) {
        return AnimationDetector.class.getResourceAsStream("/" + fileName);
    }

    public ImageMetaData getImageMetaData(InputStream inputStream) throws IOException, MimeTypeParseException {
        MediaType mediaType = getMediaType(inputStream);
        ImageMetaData.ImageMetaDataBuilder imageMetaDataBuilder = ImageMetaData.builder();
        imageMetaDataBuilder.mediaType(mediaType);//setting to infinite by default

        if (MediaType.GIF.equals(mediaType)) {
            GifDecoder decoder = new GifDecoder();
            decoder.read(inputStream);
            int totalTimeMills = 0;

            for (int i = 0; i < decoder.getFrameCount(); i++) {
                totalTimeMills += decoder.getDelay(i);
            }
            if (decoder.getLoopCount() >= 1) {
                imageMetaDataBuilder.loopCount(decoder.getLoopCount());
                imageMetaDataBuilder.animationTimeInMills(totalTimeMills * decoder.getLoopCount() / 1000);
            } else {
                imageMetaDataBuilder.loopCount(-1).animationTimeInMills(-1);
            }
        }
        return imageMetaDataBuilder.build();
    }

    public ImageMetaData getImageMetaData(String filePath, ResourceLocation resourceLocation) throws IOException, MimeTypeParseException {

        InputStream inputStream = null;
        if (ResourceLocation.CLASSPATH.equals(resourceLocation)) {
            inputStream = readFileFromClassPath(filePath);
        } else if (ResourceLocation.FILE.equals(resourceLocation)) {
            File initialFile = new File(filePath);
            inputStream = new FileInputStream(initialFile);
        }
        return getImageMetaData(inputStream);
    }

    public enum ResourceLocation {
        CLASSPATH,
        FILE
    }

}
