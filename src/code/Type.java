package code;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bq on 2015/9/15.
 */
public class Type extends CodePrintWriter{

    static Map<String,Type> typeMap = new HashMap<String,Type>();
    static {
        typeMap.put("int", new IntType());
        typeMap.put("string", new StringType());
        typeMap.put("long", new LongType());
        typeMap.put("short",new ShortType());
        typeMap.put("bool", new BooleanType());
        typeMap.put("list", new ListType());
        typeMap.put("map", new MapType());
    }

    public static Type typeForName(String name){
        Type type = typeMap.get(name);
        if (type == null){
            throw new RuntimeException("can not find type "+name);
        }
        return type;
    }

    protected String name;

    public String getFullName(){
        return name;
    }

    public String getName(){
        return name;
    }

    public static void addType(Type type){
        typeMap.put(type.getName(), type);
    }

    public void compile(String key, String value){

    }

    @Override
    public void print(PrintWriter print) {

    }

    @Override
    public String getFilePath() {
        return null;
    }

    public String getTypeValue(){
        return name;
    }

    public String genDefaultValue(String defaultValue){
        if (defaultValue == null){
            return "";
        }
        return String.format("= new %s(%s)",name,defaultValue);
    }
}
