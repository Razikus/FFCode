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
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam Raźniewski
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
