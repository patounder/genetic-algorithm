import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 1000;
    public static final int QUANTITY_PARENTS = 50;
    public static final String REFERENCE_SEQUENCE = "10010";
    public static final double MUTATION_RATE = 0.2;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();
        Population bestPopulation = null;


        long timeInit = System.currentTimeMillis();
        for(int i = 1; i <= 100; i++){

            Population population = populationManager.makePopulation(POPULATION_SIZE, REFERENCE_SEQUENCE);
            List<Member> parents = populationManager.getPopulationParents(population.getPopulationList(), QUANTITY_PARENTS);
            Population newPopulation = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE);
            populationManager.calcPopulationFitness(newPopulation, REFERENCE_SEQUENCE);

            if((bestPopulation == null) || bestPopulation.getQuantityBestMembers() < newPopulation.getQuantityBestMembers() ){
                bestPopulation = newPopulation;
            }

        }
        long duration = System.currentTimeMillis() - timeInit;
        System.out.printf("Cantidad de mejores genes=%d, tiempo=%d", bestPopulation.getQuantityBestMembers(), duration);

    }
}
