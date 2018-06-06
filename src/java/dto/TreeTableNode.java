/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import constants.Constants;
import entities.CompletionStatus;

/**
 *
 * @author ZXNIKIC
 */
public class TreeTableNode {

    private Object object;

    private CompletionStatus statusDropDown;
    private boolean showDropDown;

    public TreeTableNode(Object object) {
        this.object = object;
        if (object instanceof TestSet) {
            showDropDown = false;
//            ((TestSet) object).getName();
        } else if (object instanceof Step) {
            showDropDown = true;
            statusDropDown = populateStatusDropDown(((Step) object).getStatusId());
        } else if (object instanceof Folder) {
            showDropDown = false;
//            return ((Folder) object).getName();
        } else {
            showDropDown = false;
        }
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
            return "Test";
        }
        return "-";
    }

    public String getTestSetStatus() {
        if (object instanceof TestSet) {
            return ((TestSet) object).getStatus() + "";
        }
        return Constants.NO_RUN + "";
    }

    public String getStatus() {
        if (object instanceof TestSet) {
            switch (((TestSet) object).getStatus()) {
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
            switch (((Step) object).getStatusId()) {
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
            switch (((Folder) object).getStatus()) {
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

    public String getRowStyle() {
        switch (getStatus()) {
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public CompletionStatus getStatusDropDown() {
        return statusDropDown;
    }

    public void setStatusDropDown(CompletionStatus statusDropDown) {
        this.statusDropDown = statusDropDown;
        if (object instanceof Step) {
            ((Step) object).setStatusId(statusDropDown.getId());
        }
    }

    public boolean isShowDropDown() {
        return showDropDown;
    }

    public void setShowDropDown(boolean showDropDown) {
        this.showDropDown = showDropDown;
    }

    private CompletionStatus populateStatusDropDown(int statusId) {
        CompletionStatus cs = new CompletionStatus(statusId, "");
        switch (statusId) {
            case 1:
                return new CompletionStatus(statusId, "Passed");
            case 2:
                return new CompletionStatus(statusId, "Failed");
            case 3:
                return new CompletionStatus(statusId, "No run");
            case 4:
                return new CompletionStatus(statusId, "Not completed");
            default:
                return new CompletionStatus(3, "No run");
        }
    }

}
