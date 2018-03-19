/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;
import constants.Constants;
/**
 *
 * @author ZXNIKIC
 */
public class TreeTableNode {

    private Object object;

    public TreeTableNode(Object object) {
        this.object = object;
    }

    public String getName() {
        if (object instanceof TestSet) {
            return ((TestSet) object).getName();
        } else if (object instanceof Step) {
            return ((Step) object).getName();
        } else if (object instanceof Folder) {
            return ((Folder) object).getName();
        }
        return "-";
    }

    public String getType() {
        if (object instanceof TestSet) {
            return "Test set";
        } else if (object instanceof Step) {
            return "Step";
        } else if (object instanceof Folder) {
            return "Folder";
        }
        return "-";
    }

    public String getStatus() {
        if (object instanceof TestSet) {
            switch(((TestSet) object).getStatus()){
                case Constants.FAILED:
                    return "Failed";
                case Constants.NOT_COMPLETED:
                    return "Not completed";
                case Constants.NO_RUN:
                    return "No run";
                case Constants.PASSED:
                    return "Passed";
                default: 
                    return "-";
            }
        } else if (object instanceof Step) {
            switch(((Step) object).getStatusId()){
                case Constants.FAILED:
                    return "Failed";
                case Constants.NOT_COMPLETED:
                    return "Not completed";
                case Constants.NO_RUN:
                    return "No run";
                case Constants.PASSED:
                    return "Passed";
                default: 
                    return "-";
            }
        } else if (object instanceof Folder) {
             switch(((Folder) object).getStatus()){
                case Constants.FAILED:
                    return "Failed";
                case Constants.NOT_COMPLETED:
                    return "Not completed";
                case Constants.NO_RUN:
                    return "No run";
                case Constants.PASSED:
                    return "Passed";
                default: 
                    return "-";
            }
        }
        return "-";
    }
    
    public String getRowStyle(){
        switch(getStatus()){
            case "Passed":
                return "green";
            case "Failed":
                return "red";
            case "Not completed":
                return "orange";
            case "No run":
                return "yellow";
            default:
                return "";
        }
    }
}
