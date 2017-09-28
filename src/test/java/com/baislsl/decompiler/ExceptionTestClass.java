package com.baislsl.decompiler;

import org.junit.Test;

import java.io.IOException;

public class ExceptionTestClass {
    public void ex() throws DecompileException {
        int g = 10;
        try {
            if (g == 100)
                g = 10;
            throw new DecompileException();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            g = -10;
        }
        System.out.println(g);
        g = -100;
    }

    @Test(expected = DecompileException.class)
    public void test() throws Exception {
        System.out.println("this class is designed to compile to class file for test");
        new ExceptionTestClass().ex();
    }
}
