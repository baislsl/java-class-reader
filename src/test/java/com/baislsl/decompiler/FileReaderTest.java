package com.baislsl.decompiler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;

public class FileReaderTest {
    private static final Logger logger = LoggerFactory.getLogger(FileReaderTest.class);

    private static void runFile(String path){
        FileReader fileReader = new FileReader();
        DataInputStream input = null;
        try{
            // input = new DataInputStream(new FileInputStream("main_class"));
            input = new DataInputStream(new FileInputStream(path));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            assertNotNull(input);
            Result result = fileReader.decompile(input);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test1(){
        runFile("src/test/resources/class_main");
    }

    @Test
    public void test2(){
        runFile("src/test/resources/ClassTag.class");
    }

}
