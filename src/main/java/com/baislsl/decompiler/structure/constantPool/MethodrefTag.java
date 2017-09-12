package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodrefTag extends FMIBasic {
    public MethodrefTag(int tag){
        super(tag);
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        List<String> ans = new ArrayList<>();
        ans.add("Methodref");
        ans.addAll(Arrays.asList(super.description(result)));
        return ans.toArray(new String[0]);
    }
}
