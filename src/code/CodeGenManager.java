package code;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by bq on 2015/9/15.
 */
public class CodeGenManager {

    private static CodeGenManager instance = new CodeGenManager();

    private Map<String,DataAccessObject> daoMap = new HashMap<String,DataAccessObject>();
    private List<Bean> beanList = new ArrayList<Bean>();

    private String namespace;

    private String codePath = "";

    private CodeGenManager(){}

    public static CodeGenManager getInstance(){
        return instance;
    }

    public void load(String file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(file));
        Element root = document.getDocumentElement();
        NodeList nl = root.getChildNodes();
        for (int i=0 ; i<nl.getLength(); i++){
            Node node = nl.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            parseNameSpace((Element) node);
        }
    }

    public void compile(){
        for (DataAccessObject dao : daoMap.values()){
            dao.compile();
        }
    }

    public void setCodePath(String path){
        this.codePath = path;
    }

    public void genCode() throws IOException {
        for (Bean bean : beanList){
            bean.gen(codePath);
        }
        for (DataAccessObject dao : daoMap.values()){
            dao.gen(codePath);
        }
        BeanManagerGen managerGen = new BeanManagerGen();
        managerGen.setNs(getNameSpace());
        managerGen.gen(codePath);

        DBFaceGen dbFaceGen = new DBFaceGen(getNameSpace());
        dbFaceGen.gen(codePath);
    }

    void parseNameSpace(Element element){
        if (element.getTagName().equals("namespace")){
            namespace = element.getAttribute("name");
            return;
        }
        if (element.getTagName().equals("bean")){
            Bean bean = new Bean(element, getNameSpace());
            bean.addType(bean);
            beanList.add(bean);
            return;
        }
        if (element.getTagName().equals("dao")){
            DataAccessObject dao = new DataAccessObject(element, getNameSpace());
            if (daoMap.put(dao.getName(), dao)!=null){
                throw new RuntimeException("duplicate dao config name = "+dao.getName());
            }
            return;
        }
    }

    String getNameSpace(){
        return namespace;
    }
}
