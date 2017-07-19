/*
    MIT License

    Copyright (c) 2017 Adam Raźniewski

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

*/

package com.approxteam.filetoimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Adam Raźniewski
 */
public class ImageWizard {
    public static final Color defaultEndSequence = new Color(0, 255, 0);
    public static final int defaultHash = 0;
    
    public static BufferedImage getImageFromBytes(byte[] array, Color endSequence, int hash) {
        int ceil = (int) Math.ceil(Math.sqrt(array.length));
        BufferedImage off_Image = new BufferedImage(ceil, ceil, BufferedImage.TYPE_INT_RGB);
        int ind = 0;
        for(int j = 0; j < ceil; j++) {
            for(int i = 0; i < ceil; i++) {
                if(ind >= array.length) {
                    off_Image.setRGB(i, j, endSequence.getRGB());
                }
                else {
                    Color rgb = new Color(getRGBInt(array[ind]) + hash);
                    if(rgb.getRGB() == endSequence.getRGB()) {
                        throw new RuntimeException("EndSequence in saved image. Choose other EndSequence or hash");
                    }
                    off_Image.setRGB(i, j, rgb.getRGB());
                    ind++;
                }
            }
        }
        return off_Image;
    }
    
    public static BufferedImage getImageFromBytes(byte[] array) {
        return getImageFromBytes(array, defaultEndSequence, defaultHash);
    }
    
    public static byte[] getBytesFromImage(BufferedImage image, Color endSequence, int hash) {
        byte[] loadedBytes = new byte[image.getWidth() * image.getWidth()];
        int index = 0;
        for(int j = 0; j < image.getWidth(); j++) {
            for(int i = 0; i < image.getHeight(); i++) {
                
                int a = image.getRGB(i, j);
                if(a == endSequence.getRGB()) {
                    break;
                } else {
                    loadedBytes[index] = getRGBByte(image.getRGB(i, j) - hash);
                    index++;
                }
            }
        }
        loadedBytes = Arrays.copyOfRange(loadedBytes, 0, index);
        return loadedBytes;
    }
    
    public static byte[] getBytesFromImage(BufferedImage image) {
        return getBytesFromImage(image, defaultEndSequence, defaultHash);
    }
    
    private static int getRGBInt(byte b) {
        int s = b + 256;
        return s;
    }
    
    private static byte getRGBByte(int b) {
        byte s = (byte) (b - 256);
        return s;
    }
    
}
