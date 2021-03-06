package code;

import org.w3c.dom.Element;

import java.io.*;

/**
 * dao生成器
 * Created by bq on 2015/9/15.
 */
public class DataAccessObject extends CodePrintWriter{
    private String name;
    private String key;
    private String value;
    private String ns;
    private boolean autoKey;

    public DataAccessObject(Element element, String ns) {
        this.ns = ns;
        name = element.getAttribute("name");
        key = element.getAttribute("key");
        value = element.getAttribute("value");
        autoKey = Boolean.parseBoolean(element.getAttribute("autoKey"));
    }

    public String getName(){
        if (ns != null){
            return ns + "." +name;
        }
        return name;
    }

    public void compile(){
        Type typeKey = Type.typeForName(this.key);
        Type typeValue = Type.typeForName(this.value);
        typeKey.compile(null,null);
        typeValue.compile(null,null);
    }

    @Override
    public void print(PrintWriter print) {
        Type keyType = Type.typeForName(key);
        Type valueType = Type.typeForName(value);
        print.println(String.format("package %s;",ns));
        print.println();
        print.println();
        print.println(String.format("public class %s{",name));

        print.println(String.format("\tpublic static %s get(%s key) throws Exception{", valueType.getFullName(), keyType.getFullName()));
        print.println(String.format("\t\tbyte[] bKey = %s.serialize(%s.class.getName()+\"_\"+key);", BeanManagerGen.getName(),getName()));
        print.println(String.format("\t\tbyte[] bValue = %s.getInstance().getFace().get(bKey);", BeanManagerGen.getName()));
        print.println(String.format("\t\tif(bValue == null) return null;"));
        print.println(String.format("\t\t%s value = (%s)%s.unSerialize(bValue);", valueType.getFullName(), valueType.getFullName(), BeanManagerGen.getName()));
        print.println(String.format("\t\treturn value;"));
        print.println("\t}");

        print.println(String.format("\tpublic static void delete(%s key) throws Exception{",keyType.getFullName()));

        print.println(String.format("\t\tbyte[] bKey = %s.serialize(%s.class.getName()+\"_\"+key);", BeanManagerGen.getName(), getName()));
        print.println(String.format("\t\t%s.getInstance().getFace().delete(bKey);", BeanManagerGen.getName()));

        print.println("\t}");

        print.println(String.format("\tpublic static void watch(%s... keys) throws Exception{",keyType.getFullName()));

        print.println(String.format("\t\tjava.util.List<byte[]> bKeyList = new java.util.ArrayList<byte[]>();"));
        print.println(String.format("\t\tfor(%s key : keys){",keyType.getFullName()));
        print.println(String.format("\t\t\tbyte[] bKey = %s.serialize(%s.class.getName()+\"_\"+key);", BeanManagerGen.getName(), getName()));
        print.println(String.format("\t\t\tbKeyList.add(bKey);"));
        print.println(String.format("\t\t}"));
        print.println(String.format("\t\t%s.getInstance().getFace().watch((byte[][]) bKeyList.toArray());", BeanManagerGen.getName()));

        print.println("\t}");

        print.println(String.format("\tpublic static void update(%s key, %s value) throws Exception{",keyType.getFullName(), valueType.getFullName()));

        print.println(String.format("\t\tbyte[] bKey = %s.serialize(%s.class.getName()+\"_\"+key);", BeanManagerGen.getName(),getName()));
        print.println(String.format("\t\tBoolean isExistKey = %s.getInstance().getFace().existKey(bKey);", BeanManagerGen.getName()));
        print.println(String.format("\t\tif(!isExistKey) throw new RuntimeException(\"no exist key \"+key);"));
        print.println(String.format("\t\tbyte[] bValue = %s.serialize(value);", BeanManagerGen.getName()));
        print.println(String.format("\t\t%s.getInstance().getFace().set(bKey, bValue);", BeanManagerGen.getName()));

        print.println("\t}");

        if (autoKey){
            print.println(String.format("\tpublic static Long insert(%s value) throws Exception{",valueType.getFullName()));
            print.println(String.format("\t\tLong key = null;"));
            print.println(String.format("\t\tbyte[] bKeyWorld = %s.serialize(%s.class.getName());", BeanManagerGen.getName(), getName()));
            print.println(String.format("\t\tkey = %s.getInstance().getFace().autoKey(bKeyWorld);", BeanManagerGen.getName()));
            print.println(String.format("\t\tbyte[] bKey = %s.serialize(key);", BeanManagerGen.getName()));
            print.println(String.format("\t\tbyte[] bValue = %s.serialize(value);", BeanManagerGen.getName()));
            print.println(String.format("\t\t%s.getInstance().getFace().set(bKey, bValue);", BeanManagerGen.getName()));
            print.print("\t");
            print.println(String.format("return key;"));
        }else {
            print.println(String.format("\tpublic static void insert(%s key, %s value) throws Exception{",keyType.getFullName(), valueType.getFullName()));
            print.println(String.format("\t\tbyte[] bKey = %s.serialize(%s.class.getName()+\"_\"+key);", BeanManagerGen.getName(),this.getName()));
            print.println(String.format("\t\tBoolean isExistKey = %s.getInstance().getFace().existKey(bKey);", BeanManagerGen.getName()));
            print.println(String.format("\t\tif(isExistKey) throw new RuntimeException(\"duplicate key \"+key);"));
            print.println(String.format("\t\tbyte[] bValue = %s.serialize(value);", BeanManagerGen.getName()));
            print.println(String.format("\t\t%s.getInstance().getFace().set(bKey, bValue);", BeanManagerGen.getName()));
        }
        print.println("\t}");

        print.println("}");
    }

    @Override
    public String getFilePath() {
        return getName().replaceAll("\\.","/")+".java";
    }
}
