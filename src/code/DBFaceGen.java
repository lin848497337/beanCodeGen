package code;

import java.io.PrintWriter;

/**
 * Created by bq on 2015/9/16.
 */
public class DBFaceGen extends CodePrintWriter{

    private String ns;

    public DBFaceGen(String ns) {
        this.ns = ns;
    }

    @Override
    public void print(PrintWriter print) {
        print.println(String.format("package %s;",ns));
        print.println(String.format("public interface %s{",getName()));
        print.println(String.format("\tpublic void set(byte[] key, byte[] value);"));
        print.println(String.format("\tpublic byte[] get(byte[] key);"));
        print.println(String.format("\tpublic void delete(byte[] key);"));
        print.println(String.format("\tpublic long autoKey(byte[] key);"));
        print.println(String.format("\tpublic Boolean existKey(byte[] key);"));

        print.println("}");
    }

    public static String getName(){
        return "IDBDriverFace";
    }

    @Override
    public String getFilePath() {
        return ns.replaceAll("\\.","/")+"/"+getName()+".java";
    }
}
