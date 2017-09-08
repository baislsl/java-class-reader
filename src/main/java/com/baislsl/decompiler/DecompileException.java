package com.baislsl.decompiler;

public class DecompileException extends Exception {
    public DecompileException(){

    }

    public DecompileException(String msg){
        super(msg);
    }

    public DecompileException(Throwable e){
        super(e);
    }

}
