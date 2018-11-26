package main.cl.dcc.uchile.genetic.algorithm;

import main.cl.dcc.uchile.genetic.algorithm.services.Member;
import main.cl.dcc.uchile.genetic.algorithm.services.Population;
import main.cl.dcc.uchile.genetic.algorithm.services.PopulationManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 1000;
    public static final int QUANTITY_PARENTS = 500;
    public static final String REFERENCE_SEQUENCE = "hola";
    public static final double MUTATION_RATE = 0.4;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();

        Population initPopulation = populationManager.makePopulation(POPULATION_SIZE, REFERENCE_SEQUENCE); //TODO change reference 'sequence' to generic reference
        Population chosenPopulation = initPopulation;
        List<Population> generations = new ArrayList<>();
        generations.add(initPopulation);

        while(chosenPopulation.getQuantityBestMembers() < 1){
            List<Member> parents = populationManager.getParentsMemberList(chosenPopulation.getPopulationList(), QUANTITY_PARENTS);
            Population newPopulation = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE, REFERENCE_SEQUENCE);

            if(newPopulation.getMaxFitness() > chosenPopulation.getMaxFitness()){
                chosenPopulation = newPopulation;
            }
            generations.add(newPopulation);
        }

        System.out.println(chosenPopulation);

    }
}