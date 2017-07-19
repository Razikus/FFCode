/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.filetoimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author RAZ
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
