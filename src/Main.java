import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 100;
    public static final int QUANTITY_PARENTS = 50;
    public static final String REFERENCE_SEQUENCE = "10010";
    public static final double MUTATION_RATE = 0.2;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();
        List<Member> populationList = populationManager.makePopulation(POPULATION_SIZE, REFERENCE_SEQUENCE);
        List<Member> parents = populationManager.getPopulationParents(populationList, QUANTITY_PARENTS);
        List<Member> newPopulation = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE);
        populationManager.setFitnessPopulation(newPopulation, REFERENCE_SEQUENCE);
    }
}
