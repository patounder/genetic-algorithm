import cl.dcc.uchile.genetic.algorithm.services.Member;
import cl.dcc.uchile.genetic.algorithm.services.Population;
import cl.dcc.uchile.genetic.algorithm.services.PopulationManager;
import cl.dcc.uchile.genetic.algorithm.services.plot.ChartBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 1000;
    public static final int QUANTITY_PARENTS = 500;
    public static final String REFERENCE_SEQUENCE = "10010";
    public static final double MUTATION_RATE = 0.2;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();

        List<Population> resumeTotalPopulation = new ArrayList<>();

        Population initPopulation = populationManager.makePopulation(POPULATION_SIZE, REFERENCE_SEQUENCE); //TODO change reference 'sequence' to generic reference
        Population bestPopulation = initPopulation;

        long timeInit = System.currentTimeMillis();
        int i = 1;
        while(i <= 1000 || bestPopulation.getQuantityBestMembers() > 500){

            List<Member> parents = populationManager.getParentsMemberList(bestPopulation.getPopulationList(), QUANTITY_PARENTS);
            Population newPopulation = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE, REFERENCE_SEQUENCE);

            if(bestPopulation.getQuantityBestMembers() < newPopulation.getQuantityBestMembers()){
                bestPopulation = newPopulation;
            }
            i++;
            resumeTotalPopulation.add(newPopulation);
        }
        long duration = System.currentTimeMillis() - timeInit;
        System.out.printf("Best population. Total population=%d, best members=%d, time=%d", POPULATION_SIZE,bestPopulation.getQuantityBestMembers(), duration);

        ChartBuilder chartBuilder = new ChartBuilder();

        chartBuilder.showScatterPlotChart(resumeTotalPopulation);
    }
}
