package code.util;

import java.io.File;

/**
 * Created by bq on 2015/9/15.
 */
public class FileUtil {
    public static String checkDir(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    public static String changePackage2Path(String ns){
        String path = ns.replaceAll(".",File.separator);
        return path;
    }

}
