/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ZXNIKIC
 */
public class TreeTableNode {
    private String name;
    private String type;
    private String status;
    private Object object;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public TreeTableNode() {
    }

    public TreeTableNode(Object object) {
        this.object = object;
        setProperties();
    }

    private void setProperties() {
//        if(object instanceof TestSet){
//            TestSet ts = (TestSet) object;
//            this.name = ts.getName();
//            this.type = "Test set";
//            this.status = "-";
//        }else if(object instanceof Folder){
//            Folder f = (Folder) object;
//            this.name = f.getName();
//            this.
//        }else if(object instanceof SpecificStep){
//            SpecificStep ss = (SpecificStep) object;
//        }
    }
    
    
}
