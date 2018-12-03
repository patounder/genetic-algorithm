package main.cl.dcc.uchile.genetic.algorithm;

import main.cl.dcc.uchile.genetic.algorithm.plot.ChartBuilder;
import main.cl.dcc.uchile.genetic.algorithm.services.Generation;
import main.cl.dcc.uchile.genetic.algorithm.services.Member;
import main.cl.dcc.uchile.genetic.algorithm.services.PopulationManager;
import main.cl.dcc.uchile.genetic.algorithm.services.Timer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 100;
    public static final int QUANTITY_PARENTS = 60;
    public static final int TOURNAMENT_SELECT_NUMBER_ATTEMPTS = 50;
    public static final int MATRIX_LENGTH = 15;
    public static final double MUTATION_RATE = 0.4;
    public static final int DESIRABLE_FITNESS = 0;
    public static final int MAX_ITERATIONS = 5000;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();

        Timer.start();
        int index = 0;
        Generation firstGeneration = populationManager.makeFirstGeneration(POPULATION_SIZE, MATRIX_LENGTH);
        firstGeneration.setGenerationIndex(index);
        Generation bestGeneration = firstGeneration;
        List<Generation> bestGenerationsList = new ArrayList<>();
        List<Generation> allGenerationList = new ArrayList<>();
        allGenerationList.add(firstGeneration);

        do {
            index++;
            List<Member> parents = populationManager.getParentsMemberList(bestGeneration.getPopulationList(),
                    QUANTITY_PARENTS, TOURNAMENT_SELECT_NUMBER_ATTEMPTS);
            Generation newGeneration = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE,
                    MATRIX_LENGTH);

            newGeneration.setGenerationIndex(index);

            if(newGeneration.getBestMember().getFitness() < bestGeneration.getBestMember().getFitness()){
                bestGeneration = newGeneration;
                bestGenerationsList.add(newGeneration);
            }

            allGenerationList.add(newGeneration);
        } while(bestGeneration.getBestMember().getFitness() > DESIRABLE_FITNESS && index < MAX_ITERATIONS);

        long duration = Timer.end();
        System.out.println("generations: "+ allGenerationList.size() + ", best member: "
                + bestGeneration.getBestMember() +", duration (ms): " + duration);

        renderPlot(allGenerationList);
    }

    public static void renderPlot(List<Generation> generationsList){
        ChartBuilder chartBuilder = new ChartBuilder();
        chartBuilder.showScatterPlotChart(generationsList);
    }
}