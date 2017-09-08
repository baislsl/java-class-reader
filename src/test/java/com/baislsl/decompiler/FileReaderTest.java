package com.baislsl.decompiler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;

public class FileReaderTest {
    private static final Logger logger = LoggerFactory.getLogger(FileReaderTest.class);

    @Test
    public void test1(){
        FileReader fileReader = new FileReader();
        DataInputStream input = null;
        try{
            // input = new DataInputStream(new FileInputStream("main_class"));
            input = new DataInputStream(new FileInputStream("src/test/resources/main_class"));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            assertNotNull(input);
            fileReader.decompile(input);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
