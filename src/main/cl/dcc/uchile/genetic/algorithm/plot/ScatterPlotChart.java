package main.cl.dcc.uchile.genetic.algorithm.plot;

import main.cl.dcc.uchile.genetic.algorithm.services.Generation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class ScatterPlotChart extends JFrame {

    public ScatterPlotChart(String title, List<Generation> generationsList) {
        super(title);

        DefaultCategoryDataset dataset = createDataset(generationsList);

        String categoryAxisLabel = "generation number";
        String valueAxisLabel = "generation's best fitness";
        JFreeChart chart = ChartFactory.createLineChart(title,
                categoryAxisLabel, valueAxisLabel, dataset);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(List<Generation> generationsList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String bestGenerationKey = "generations";


        for(int index = 0; index < generationsList.size(); index++) {
            dataset.addValue(generationsList.get(index).getBestMember().getFitness(), bestGenerationKey,
                    Integer.toString(index));
        }
        return dataset;
    }
}
