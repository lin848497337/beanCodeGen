package code;

/**
 * Created by bq on 2015/9/16.
 */
public class main {
    public static void main(String []args) throws Exception {
        if (args.length != 4){
            System.out.println("-dbConfig dbConfig.xml -codeDir codeDir");
            return;
        }
        String dbConfig = "dbConfig.xml";
        String codDir = "codeDir";
        for (int i=0 ; i<args.length ; i+=2){
            if (args[i].equals("-dbConfig")){
                dbConfig = args[i+1];
            }else if(args[i].equals("-codeDir")){
                codDir = args[i+1];
            }
        }
        CodeGenManager.getInstance().load(dbConfig);
        CodeGenManager.getInstance().compile();
        CodeGenManager.getInstance().setCodePath(codDir);
        CodeGenManager.getInstance().genCode();
    }
}
