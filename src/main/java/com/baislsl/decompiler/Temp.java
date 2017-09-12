package com.baislsl.decompiler;

import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class Temp {

    public static void main(String[] args){
        String s = new String ("No");
        s = null;
        s.intern();
        System.gc();

        JavaClass javaClass;


    }
}
