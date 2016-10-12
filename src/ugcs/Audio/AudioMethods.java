/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author sgahe
 */
public class AudioMethods {

    private final int BUFFER_SIZE = 128000;
    private File sFile;
    private AudioInputStream audStream;
    private AudioFormat audFormat;
    private SourceDataLine sLine;

    public AudioMethods() {
    }

    public void playAudio(String file) {
        try {
            new Thread() {
                public void run() {
                    String audFile = "src/ugcs/Audio/" + file;

                    try {
                        sFile = new File(audFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }

                    try {
                        audStream = AudioSystem.getAudioInputStream(sFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }

                    audFormat = audStream.getFormat();

                    DataLine.Info inf = new DataLine.Info(SourceDataLine.class, audFormat);

                    try {
                        sLine = (SourceDataLine) AudioSystem.getLine(inf);
                        sLine.open(audFormat);
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                        System.exit(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }

                    sLine.start();

                    int i = 0;
                    byte[] abData = new byte[BUFFER_SIZE];
                    while (i
                            != -1) {
                        try {
                            i = audStream.read(abData, 0, abData.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (i >= 0) {
                            @SuppressWarnings("unused")
                            int ii = sLine.write(abData, 0, i);
                        }
                    }

                    sLine.drain();

                    sLine.close();
                }
            }.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
