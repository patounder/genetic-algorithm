package main.cl.dcc.uchile.genetic.algorithm;

import main.cl.dcc.uchile.genetic.algorithm.services.Generation;
import main.cl.dcc.uchile.genetic.algorithm.services.Member;
import main.cl.dcc.uchile.genetic.algorithm.services.PopulationManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 100;
    public static final int QUANTITY_PARENTS = 50;
    public static final String REFERENCE_SEQUENCE = "valparaiso";
    public static final double MUTATION_RATE = 0.4;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();

        Generation firstGeneration = populationManager.makeFirstGeneration(POPULATION_SIZE, REFERENCE_SEQUENCE); //TODO change reference 'sequence' to generic reference
        Generation chosenGenration = firstGeneration;
        List<Generation> generationList = new ArrayList<>();
        generationList.add(firstGeneration);


        int index = 0;
        do {
            List<Member> parents = populationManager.getParentsMemberList(chosenGenration.getPopulationList(), QUANTITY_PARENTS);
            Generation newGeneration = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE, REFERENCE_SEQUENCE);

            if(newGeneration.getBestMember().getFitness() > chosenGenration.getBestMember().getFitness()){
                chosenGenration = newGeneration;
            }

            generationList.add(newGeneration);
            index++;
        } while(chosenGenration.getBestMember().getFitness() < REFERENCE_SEQUENCE.length() && index < 1000);

        System.out.println("generations: "+ generationList.size()+", best member: "+chosenGenration.getBestMember());
    }
}