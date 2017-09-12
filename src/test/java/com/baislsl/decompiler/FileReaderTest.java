package com.baislsl.decompiler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;

public class FileReaderTest {
    private static final Logger logger = LoggerFactory.getLogger(FileReaderTest.class);

    public static Result runFile(String path) {
        FileReader fileReader = new FileReader();
        DataInputStream input = null;
        try {
            // input = new DataInputStream(new FileInputStream("main.class"));
            input = new DataInputStream(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(input);
        try {
            return fileReader.decompile(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test1() {
        runFile("src/test/resources/main.class");
    }

    @Test
    public void test2() {
        runFile("src/test/resources/ClassTag.class");
    }

    @Test
    public void test3(){
        runFile("src/test/resources/CodeAttr.class");
    }

}
