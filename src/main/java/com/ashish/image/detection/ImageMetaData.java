package com.ashish.image.detection;

import com.google.common.net.MediaType;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by bhardwaj on 6/1/19
 */

@Builder
public class ImageMetaData {


    @Getter
    private MediaType mediaType;

    /* -1 means infinite*/
    @Getter
    private long animationTimeInMills;

    /* -1 means infinite*/
    @Getter
    private long loopCount;

}
