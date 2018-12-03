package main.cl.dcc.uchile.genetic.algorithm.plot;

import main.cl.dcc.uchile.genetic.algorithm.services.Generation;

import javax.swing.*;
import java.util.List;

public class ChartBuilder {

    public void showScatterPlotChart(List<Generation> generationsList){

        String title = "generation v/s best member fitness";

        SwingUtilities.invokeLater(() -> {
            ScatterPlotChart scatterPlotChart = new ScatterPlotChart(title, generationsList);
            scatterPlotChart .setSize(800, 400);
            scatterPlotChart .setLocationRelativeTo(null);
            scatterPlotChart .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            scatterPlotChart .setVisible(true);
        });
    }

}
