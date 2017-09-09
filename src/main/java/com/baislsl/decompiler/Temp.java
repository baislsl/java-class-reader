package com.baislsl.decompiler;

public class Temp {

    public static void main(String[] args){
        String s = new String ("No");
        s = null;
        s.intern();
        System.gc();
    }
}
