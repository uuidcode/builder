package com.github.uuidcode.builder.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Tail extends Thread {
    protected static Logger logger = getLogger(Tail.class);

    private int sleepSecond = 3;
    private File file;

    public Tail setFile(String fileName) {
        return this.setFile(new File(fileName));
    }

    public Tail setFile(File file) {
        this.file = file;
        return this;
    }

    public Tail setSleepSecond(int sleepSecond) {
        this.sleepSecond = sleepSecond;
        return this;
    }

    public static Tail of() {
        return new Tail();
    }

    public void run() {
        this.file.delete();

        while (true) {
            this.sleep();

            if (this.file.exists()) {
                this.read();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000 * this.sleepSecond);
        } catch (Throwable t) {
        }
    }

    private void read() {
        try (BufferedReader reader = this.createReader()) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> Tail run error", e);
            }
        }
    }

    private BufferedReader createReader() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }
}
