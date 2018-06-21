/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import constants.Constants;
import controller.Controller;
import dto.TestSet;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author ZXNIKIC
 */
@Named(value = "mbChartView")
@SessionScoped
public class MBChartView implements Serializable {

    @Inject
    Controller controller;

    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        createAnimatedModel();
        if (controller == null) {
            controller = new Controller();
        }
    }

    /**
     * Creates a new instance of MBChartView
     */
    public MBChartView() {
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createAnimatedModel() {
        barModel = initBarModel();
        barModel.setTitle("Test set status");
        barModel.setAnimate(true);
        barModel.setLegendPosition("ne");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(20);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setTickAngle(-30);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries passed = new ChartSeries();
        passed.setLabel("Passed");
        ChartSeries failed = new ChartSeries();
        failed.setLabel("Failed");
        ChartSeries noRun = new ChartSeries();
        noRun.setLabel("No Run");
        ChartSeries notCompleted = new ChartSeries();
        notCompleted.setLabel("Not completed");

        List<TestSet> testSets = controller.getSets();
        testSets.sort(Comparator.comparing(ts -> ts.getDateCreated()));
        for (TestSet testSet : testSets) {
//            String name = createTestSetName(testSet.getName());
            String name = testSet.getName();
            passed.set(name, testSet.getNumberOfStepsWithStatus(Constants.PASSED));
            failed.set(name, testSet.getNumberOfStepsWithStatus(Constants.FAILED));
            noRun.set(name, testSet.getNumberOfStepsWithStatus(Constants.NO_RUN));
            notCompleted.set(name, testSet.getNumberOfStepsWithStatus(Constants.NOT_COMPLETED));
        }
        model.addSeries(passed);
        model.addSeries(failed);
        model.addSeries(noRun);
        model.addSeries(notCompleted);
        return model;
    }
    
    public void refresh(){
        createAnimatedModel();
    }

}
