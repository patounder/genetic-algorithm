package main.cl.dcc.uchile.genetic.algorithm.plot;

import main.cl.dcc.uchile.genetic.algorithm.services.Population;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class ScatterPlotChart extends JFrame {

    public ScatterPlotChart(String title, List<Population> populationList) {
        super(title);

        // Create dataset
        DefaultCategoryDataset dataset = createDataset(populationList);

        // Create chart
        JFreeChart chart = ChartFactory.createLineChart("Numero de Poblacion v/s Cantidad de Mejores Miembros","Numero de Poblacion","Cantidad de Mejores Miembros", dataset);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(List<Population> populationList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i = 0; i < populationList.size(); i++) {
            dataset.addValue(populationList.get(i).getQuantityBestMembers(), "population", Integer.toString(i));
        }

        return dataset;
    }
}
