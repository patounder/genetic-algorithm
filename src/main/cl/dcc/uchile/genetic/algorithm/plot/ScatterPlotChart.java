package main.cl.dcc.uchile.genetic.algorithm.plot;

import main.cl.dcc.uchile.genetic.algorithm.services.Generation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class ScatterPlotChart extends JFrame {

    public ScatterPlotChart(String title, List<Generation> generationList) {
        super(title);

        // Create dataset
        DefaultCategoryDataset dataset = createDataset(generationList);

        // Create chart
        JFreeChart chart = ChartFactory.createLineChart("Numero de Poblacion v/s Cantidad de Mejores Miembros","Numero de Poblacion","Cantidad de Mejores Miembros", dataset);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(List<Generation> generationList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i = 0; i < generationList.size(); i++) {
            dataset.addValue(generationList.get(i).getQuantityBestMembers(), "population", Integer.toString(i));
        }

        return dataset;
    }
}
