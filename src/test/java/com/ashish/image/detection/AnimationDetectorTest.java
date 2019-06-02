package com.ashish.image.detection;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ashish.image.detection.AnimationDetector.ResourceLocation;
import com.google.common.net.MediaType;
import static com.ashish.image.detection.AnimationDetector.ResourceLocation.CLASSPATH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AnimationDetectorTest {


    @DataProvider(name = "imageTestData")
    public Object[][] getImageInput() {
        return new Object[][]{{"animated.gif", CLASSPATH, MediaType.GIF, -1, -1}, {"gif_as_png.png", CLASSPATH, MediaType.GIF, -1, -1}, {"loop_4_length_4s.gif", CLASSPATH, MediaType.GIF, 4, 4}, {"loop_19_length_19s.gif", CLASSPATH, MediaType.GIF, 19, 19}, {"native_screenshot.jpg", CLASSPATH, MediaType.JPEG, 0, 0}, {"static.gif", CLASSPATH, MediaType.GIF, 1, 0}

        };
    }

    @Test(dataProvider = "imageTestData")
    public void getImageMetaData(String filePath, ResourceLocation resourceLocation, MediaType mediaType, long expectedLoopCount, long expectedAnimationTime) throws Exception {
        AnimationDetector animationDetector = new AnimationDetector();
        ImageMetaData imageMetaData = animationDetector.getImageMetaData(filePath, resourceLocation);
        assertEquals(imageMetaData.getMediaType(), mediaType);
        assertTrue(imageMetaData.getLoopCount() == expectedLoopCount);
        assertTrue(imageMetaData.getAnimationTimeInMills() == expectedAnimationTime);
    }

}