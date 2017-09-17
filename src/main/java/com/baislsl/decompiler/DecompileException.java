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

    public DecompileException(Throwable a, String msg){
        super(msg, a);
    }

}
