package com.baislsl.decompiler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;

public class ClassReaderTest {
    private static final Logger logger = LoggerFactory.getLogger(ClassReaderTest.class);

    public static Result runFile(String path) {
        ClassReader classReader = new ClassReader();
        DataInputStream input = null;
        try {
            // input = new DataInputStream(new FileInputStream("main.class"));
            input = new DataInputStream(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(input);
        try {
            return ClassReader.decompile(input);
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

    @Test
    public void test4(){
        runFile("src/test/resources/ConstantPoolBuilder.class");
    }

}
