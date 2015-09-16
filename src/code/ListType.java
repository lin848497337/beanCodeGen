package code;

import java.io.PrintWriter;

/**
 * Created by bq on 2015/9/15.
 */
public class ListType extends Type {

    private String valueType;

    public ListType() {
        this.name = "java.util.ArrayList";
    }

    @Override
    public void compile(String key, String value) {
        Type.typeForName(value);
        valueType = value;
    }

    @Override
    public String getTypeValue() {
        Type type = Type.typeForName(valueType);
        return String.format("%s<%s>",name,type.getTypeValue());
    }

    @Override
    public String genDefaultValue(String defaultValue) {
        Type type = Type.typeForName(valueType);
        return String.format("= new %s<%s>()",name,type.getTypeValue());
    }
}
