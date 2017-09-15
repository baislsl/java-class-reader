# java-class-decompiler
Decompiler intended to decompile *.class format files

### class file structure
```
ClassFile {
    u4             magic;
    u2             minor_version;
    u2             major_version;
    u2             constant_pool_count;
    cp_info        constant_pool[constant_pool_count-1];
    u2             access_flags;
    u2             this_class;
    u2             super_class;
    u2             interfaces_count;
    u2             interfaces[interfaces_count];
    u2             fields_count;
    field_info     fields[fields_count];
    u2             methods_count;
    method_info    methods[methods_count];
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
```

## 当前进度
现在完成了class文件读取， 大体上完成了javap -v指令的功能，
但是在很多细节上可能还有待改进， 特别是部分较少用的attribute解析
还没写好， 注解类、泛型的解析也还没处理