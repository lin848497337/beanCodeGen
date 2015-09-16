package code;

import java.io.PrintWriter;

/**
 * Created by bq on 2015/9/16.
 */
public class BeanManagerGen extends CodePrintWriter {

    private String ns;

    public void setNs(String ns){
        this.ns = ns;
    }

    public String getFullName(){
        return ns + "." + getName();
    }

    public static String getName( ){
        return "BeansManager";
    }

    @Override
    public void print(PrintWriter print) {
        print.println(String.format("package %s;",ns));
        print.println("import java.io.ByteArrayInputStream;");
        print.println("import java.io.ObjectInputStream;");
        print.println("import java.io.ByteArrayOutputStream;");
        print.println("import java.io.ObjectOutputStream;");
        print.println();
        print.println();
        print.println(String.format("public class %s{",getName()));

        print.println(String.format("\tprivate static %s instance = new %s();",getName(),getName()));
        print.println(String.format("\tprivate %s face;",DBFaceGen.getName()));

        print.println(String.format("\tprivate %s(){}",getName()));

        print.println(String.format("\tpublic static %s getInstance(){ return instance; }",getName()));

        print.println(String.format("\tpublic void configDBFace(%s face){ this.face = face; }",DBFaceGen.getName()));

        print.println(String.format("\t%s getFace(){return this.face;}",DBFaceGen.getName()));


        print.println("\tpublic static Object unSerialize(byte[] bytes) throws Exception{");
        print.println("\t\tByteArrayInputStream bis = new ByteArrayInputStream(bytes);");
        print.println("\t\tObjectInputStream ois = new ObjectInputStream(bis);");
        print.println("\t\treturn ois.readObject();");
        print.println("\t}");

        print.println("\tpublic static byte[] serialize(Object object) throws Exception{");
        print.println("\t\tByteArrayOutputStream bos = new ByteArrayOutputStream();");
        print.println("\t\tObjectOutputStream oos = new ObjectOutputStream(bos);");
        print.println("\t\toos.writeObject(object);");
        print.println("\t\treturn bos.toByteArray();");
        print.println("\t}");

        print.println("}");
    }

    @Override
    public String getFilePath() {
        return getFullName().replaceAll("\\.","/") + ".java";
    }

}
