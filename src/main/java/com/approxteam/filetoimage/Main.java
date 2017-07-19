/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.filetoimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author RAZ
 */
public class Main {
    public static void main(String[] args) {
        if(args.length < 3) {
            System.out.println("java -jar jarName {encode/decode} {fileName} {targetName} [red-green-black] [hash]");
            System.out.println("decode - from png to file");
            System.out.println("encode - from file to png");
            System.out.println("red;green;black - default 0;255;0");
            System.out.println("hash - default 0");
            System.exit(1);
        }
        Color defaultColor = ImageWizard.defaultEndSequence;
        int defaultHash = ImageWizard.defaultHash;
        if(args.length >= 4) {
            int[] colors = stringArrayToIntArray(args[3].split("-"));
            defaultColor = new Color(colors[0], colors[1], colors[2]);
        }
        
        if(args.length >= 5) {
            defaultHash = Integer.valueOf(args[4]);
        }
        
        Path filePath = Paths.get(args[1]);
        Path encodePath = Paths.get(args[2]);
        if(args[0].equals("encode")) {
            File outputFile = new File(encodePath.toString());
            byte[] file = new byte[0];
            try {
                file = Files.readAllBytes(filePath);
            } catch (IOException ex) {
                System.out.println("Can't access: " + filePath.toAbsolutePath());
                System.exit(1);
            }
            
            BufferedImage encoded = ImageWizard.getImageFromBytes(file, defaultColor, defaultHash);
            try {
                ImageIO.write(encoded, "png", outputFile);
            } catch (IOException ex) {
                System.out.println("Can't access: " + encodePath.toAbsolutePath());
                System.exit(1);
            }
        }
        else if(args[0].equals("decode")) {
            BufferedImage loaded = null;
            try {
                loaded = ImageIO.read(new File(filePath.toAbsolutePath().toString()));
            } catch (IOException ex) {
                System.out.println("Can't access: " + filePath.toAbsolutePath());
                System.exit(1);
            }
            byte[] decoded = ImageWizard.getBytesFromImage(loaded, defaultColor, defaultHash);
            try {
                FileOutputStream fos = new FileOutputStream(encodePath.toAbsolutePath().toString());
                fos.write(decoded);
                fos.close();
            } catch (IOException ex) {
                System.out.println("Can't access: " + encodePath.toAbsolutePath());
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }
    
    private static int[] stringArrayToIntArray(String[] array) {
        int[] integerArray = new int[array.length];
        for(int j = 0; j < array.length; j++){
            integerArray[j] = Integer.valueOf(array[j]);
        }
        return integerArray;
    }
}
