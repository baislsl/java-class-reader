package com.baislsl.decompiler.utils.descriptor;

public class VoidDescriptor implements Descriptor {

    @Override
    public Class<?> getType() {
        return void.class;
    }

    @Override
    public String toString(){
        return "void";
    }
}
