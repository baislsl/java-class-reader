package com.baislsl.decompiler.utils.descriptor;

import com.baislsl.decompiler.DecompileException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodDescriptor {
    private Descriptor[] paramDescriptors;
    private Descriptor returnDescriptor;

    public MethodDescriptor(String str) throws DecompileException{
        Pattern p = Pattern.compile("\\(([\\[\\w;/]*)\\)([\\[\\w;/]+)");

        Matcher matcher = p.matcher(str);

        if(!matcher.matches() || matcher.groupCount() != 2)
            throw new DecompileException("Error format method descriptor :" + str);
        String parameterDescriptorStr = matcher.group(1);
        String returnDescriptorStr = matcher.group(2);

        paramDescriptors = FieldDescriptor.getFieldDescriptors(parameterDescriptorStr);
        if(returnDescriptorStr.equals("V")){
            returnDescriptor = new VoidDescriptor();
        }else{
            returnDescriptor = FieldDescriptor.getFieldDescriptors(returnDescriptorStr)[0];
        }
    }

    public Descriptor[] getParamDescriptors() {
        return paramDescriptors;
    }

    public Descriptor getReturnDescriptor() {
        return returnDescriptor;
    }

    public int getParamSize(){
        return paramDescriptors.length;
    }
}
