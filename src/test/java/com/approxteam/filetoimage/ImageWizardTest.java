/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.filetoimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RAZ
 */
public class ImageWizardTest {
    
    private static Random random;
    
    public ImageWizardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        random = new Random();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getImageFromBytes method, of class ImageWizard.
     */
    @Test
    public void testGetImageFromBytes() {
        System.out.println("getImageFromBytes");
        byte[] array = new byte[100];
        random.nextBytes(array);
        BufferedImage result = ImageWizard.getImageFromBytes(array);
        byte[] loaded = ImageWizard.getBytesFromImage(result);
        assertArrayEquals(array, loaded);
    }
    @Test(expected = RuntimeException.class)
    public void testEndSequnceRuntimeException() {
        System.out.println("endSequnceRuntimeException");
        Color endSequence = new Color(0, 0, 255);
        byte[] array = new byte[100];
        random.nextBytes(array);
        int hash = 0;
        while(true && hash <= 100000) {
            BufferedImage result = ImageWizard.getImageFromBytes(array, endSequence, hash);
            hash++;
        }
    }
    
    
}
