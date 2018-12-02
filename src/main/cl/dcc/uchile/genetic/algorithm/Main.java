package main.cl.dcc.uchile.genetic.algorithm;

import main.cl.dcc.uchile.genetic.algorithm.services.Generation;
import main.cl.dcc.uchile.genetic.algorithm.services.Member;
import main.cl.dcc.uchile.genetic.algorithm.services.PopulationManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 100;
    public static final int QUANTITY_PARENTS = 50;
    public static final int NUMBER_ATTEMPTS = 75;
    public static final int MATRIX_LENGTH = 4;
    public static final double MUTATION_RATE = 0.4;
    public static final int DESIRABLE_FITNESS = 0;
    public static final int MAX_ITERATIONS = 1000;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();

        Generation firstGeneration = populationManager.makeFirstGeneration(POPULATION_SIZE, MATRIX_LENGTH);
        Generation chosenGeneration = firstGeneration;
        List<Generation> generationList = new ArrayList<>();
        generationList.add(firstGeneration);

        int index = 0;
        do {
            List<Member> parents = populationManager.getParentsMemberList(chosenGeneration.getPopulationList(), QUANTITY_PARENTS, NUMBER_ATTEMPTS);
            Generation newGeneration = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE, MATRIX_LENGTH);

            if(newGeneration.getBestMember().getFitness() < chosenGeneration.getBestMember().getFitness()){
                chosenGeneration = newGeneration;
            }

            generationList.add(newGeneration);
            index++;
        } while(chosenGeneration.getBestMember().getFitness() > DESIRABLE_FITNESS && index < MAX_ITERATIONS);

        System.out.println("generations: "+ generationList.size() + ", best member: " + chosenGeneration.getBestMember());
    }
}