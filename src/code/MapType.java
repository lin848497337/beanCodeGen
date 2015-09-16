package code;

import java.io.PrintWriter;

/**
 * Created by bq on 2015/9/16.
 */
public class MapType extends Type {

    private String key;
    private String value;

    public MapType() {
        name = "java.util.HashMap";
    }

    @Override
    public void compile(String key, String value) {
        this.key = key;
        this.value = value;
        Type.typeForName(key);
        Type.typeForName(value);
    }

    @Override
    public String getTypeValue() {
        Type keyType = Type.typeForName(key);
        Type valueType = Type.typeForName(value);
        return (String.format("%s<%s,%s>", name, keyType.getTypeValue(), valueType.getTypeValue()));
    }


    @Override
    public String genDefaultValue(String defaultValue) {
        Type keyType = Type.typeForName(key);
        Type valueType = Type.typeForName(value);
        return String.format("= new %s<%s,%s>()",name,keyType.getTypeValue(), valueType.getTypeValue());
    }
}
