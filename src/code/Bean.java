package code;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bq on 2015/9/14.
 */
public class Bean extends Type{
    private List<Variable> variableList = new ArrayList<Variable>();
    private String ns;

    public Bean(Element element, String ns){
        this.name = element.getAttribute("name");
        this.ns = ns;
        NodeList nodeList = element.getElementsByTagName("variable");
        for (int i=0 ; i<nodeList.getLength() ; i++){
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            Element el = (Element) node;
            Variable variable = new Variable(el);
            this.variableList.add(variable);
        }
    }

    @Override
    public String getFullName() {
        if (ns != null){
            return ns + "." +name;
        }
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void compile(String key,String value){
        for (Variable variable : variableList){
            variable.compile();
        }
    }

    @Override
    public void print(PrintWriter print) {
        print.println(String.format("package %s;",ns));
        print.println(String.format("public class %s implements java.io.Serializable{",name));

        for (Variable v : variableList){
            print.print("\t");v.gen(print);
        }

        for (Variable v : variableList){
            v.compile();
            print.print("\t");
            print.println(String.format("public %s get%s(){",v.getType().getTypeValue(),v.getName()));
            print.print("\t\t");
            print.println(String.format("return this.%s;",v.getName()));
            print.println("\t}");

            print.print("\t");
            print.println(String.format("public void set%s(%s %s){",v.getName(),v.getType().getTypeValue(),v.getName()));
            print.print("\t\t");
            print.println(String.format("this.%s = %s;",v.getName(),v.getName()));
            print.println("\t}");
        }

        print.println("}");
    }

    @Override
    public String getFilePath() {
        return getFullName().replaceAll("\\.", "/")+".java";
    }
}
