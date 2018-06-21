/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbTabbedView")
@SessionScoped
public class MBTabbedView implements Serializable {

    @Inject
    MBTestSetTreeView mBTestSetTreeView;
    @Inject
    MBTreeTable mBTreeTable;
    @Inject
    MBChartView mbChartView;
    /**
     * Creates a new instance of MBTabbedView
     */
    public MBTabbedView() {
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Test Lab")) {
            mBTreeTable.refresh();
        }else if(event.getTab().getTitle().equals("Statistics")){
            mbChartView.refresh();
        }
    }
}
