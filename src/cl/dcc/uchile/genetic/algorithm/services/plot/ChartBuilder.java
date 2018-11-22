package cl.dcc.uchile.genetic.algorithm.services.plot;

import cl.dcc.uchile.genetic.algorithm.services.Population;

import javax.swing.*;
import java.util.List;

public class ChartBuilder {

    public void showScatterPlotChart(List<Population> populationList){
        SwingUtilities.invokeLater(() -> {
            ScatterPlotChart scatterPlotChart = new ScatterPlotChart("", populationList);
            scatterPlotChart .setSize(800, 400);
            scatterPlotChart .setLocationRelativeTo(null);
            scatterPlotChart .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            scatterPlotChart .setVisible(true);
        });
    }

}
