package code;

import org.w3c.dom.Element;

import java.io.PrintWriter;

/**
 * Created by bq on 2015/9/15.
 */
public class Variable {
    private String name;
    private String type;
    private String key;
    private String value;
    private String defaultValue;
    public Variable(Element element){
        this.name = element.getAttribute("name");
        this.type = element.getAttribute("type");
        this.key = element.getAttribute("key");
        this.value = element.getAttribute("value");
        this.defaultValue = element.getAttribute("default");
        if (this.key.trim().equals("")){
            this.key = null;
        }
        if (this.value.trim().equals("")){
            this.value = null;
        }
        if (this.defaultValue.equals("")){
            this.defaultValue = null;
        }
    }

    public void compile(){
        Type o = Type.typeForName(type);
        if (key != null){
            Type.typeForName(key).compile(null,null);
        }
        if (value != null){
            Type.typeForName(value).compile(null,null);
        }
        o.compile(key,value);
    }

    public void gen(PrintWriter print){
        Type o = Type.typeForName(type);
        o.compile(key,value);
        print.println(String.format("private %s %s %s;",o.getTypeValue(),name,o.genDefaultValue(defaultValue)));
    }

    public Type getType(){
        return Type.typeForName(type);
    }

    public String getName() {
        return name;
    }
}
