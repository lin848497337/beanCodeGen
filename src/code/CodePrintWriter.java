package code;

import code.util.FileUtil;

import java.io.*;

/**
 * Created by bq on 2015/9/15.
 */
public abstract class CodePrintWriter {

    public void gen(String dir) throws IOException {
        String file = getFilePath();
        File f = new File(dir + File.separator + file);
        File parent = f.getParentFile();
        checkParent(parent);
        if (f.exists()){
            f.delete();
        }
        f.createNewFile();
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f)));
        print(printWriter);
        printWriter.close();
    }

    protected void checkParent(File parent){
        if (parent == null){
            return;
        }
        if (!parent.exists()){
            checkParent(parent.getParentFile());
        }
        parent.mkdir();
    }

    public abstract void print(PrintWriter print);

    public abstract String getFilePath();

    public void writePackage(PrintWriter print, String pkg){
        print.println(String.format("package %s",pkg));
    }

    public void writeClass(PrintWriter print, String className){
        print.println(String.format("public class %s",className));
    }

    public void writeBegin(PrintWriter print){
        print.println("{");
    }

    public void writeEnd(PrintWriter print){
        print.println("}");
    }

}
