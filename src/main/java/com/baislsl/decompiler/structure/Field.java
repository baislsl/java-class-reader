package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Javap;
import com.baislsl.decompiler.utils.descriptor.Descriptor;
import com.baislsl.decompiler.utils.descriptor.FieldDescriptor;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.attribute.ConstantValueAttr;
import com.baislsl.decompiler.structure.attribute.DeprecatedAttr;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.StringTag;

import java.io.DataInputStream;

public class Field extends FieldMethodBasic {
    private Field(int accessFlag, int nameIndex, int descriptorIndex,
                  Attribute[] attributes, ConstantPool[] constantPools) {
        super(accessFlag, nameIndex, descriptorIndex, attributes, constantPools);
    }

    public static Field build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        FieldMethodBasic basic = FieldMethodBasic.build(file, constantPools);
        return new Field(basic.getAccessFlag(), basic.getNameIndex(),
                basic.getDescriptorIndex(), basic.getAttributes(), constantPools);
    }

    @Override
    public String name() throws DecompileException {
        String[] accFlags = Javap.getAccessFlagDescription(accessFlag, Field.class);
        String descriptorInfo = constantPools[descriptorIndex].name();
        Descriptor descriptor = FieldDescriptor.getFieldDescriptors(descriptorInfo)[0];
        String name = constantPools[nameIndex].name();
        StringBuilder ans = new StringBuilder();

        for (String accFlag : accFlags) {
            ans.append(accFlag);
            ans.append(" ");
        }

        ans.append(descriptor.toString());
        ans.append(" ");
        ans.append(name);

        /**
         * each attributes of field must be one of following attributes:
         *      ConstantValue
         *      Synthetic
         *      Deprecated
         *      RuntimeVisibleAnnotations
         *      RuntimeInvisibleAnnotations
         *      RuntimeVisibleTypeAnnotations
         *      RuntimeInvisibleTypeAnnotations
         *  for decompile, we not just solve ConstantValue and Deprecated attributes
         */
        for (Attribute attribute : attributes) {
            if (attribute instanceof ConstantValueAttr) {
                int index = ((ConstantValueAttr) attribute).getConstantValueIndex();
                ConstantPool cp = constantPools[index];
                ans.append(" = ");
                if (cp instanceof StringTag) {
                    ans.append(constantPools[((StringTag) cp).getStringIndex()].name());
                } else {
                    // cp must be in DoubleTag, LongTag, FloatTag, IntegerTag
                    ans.append(cp.name());
                }
            } else if (attribute instanceof DeprecatedAttr) {
                ans.insert(0, "@Deprecated ");
            }
        }

        for(Attribute attribute : attributes){
            ans.append(attribute.name()).append("\n");
        }

        return ans.toString();
    }
}
