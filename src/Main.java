import java.util.List;

public class Main {

    public static final int POPULATION_QUANTITY = 100;
    public static final int QUANTITY_PARENTS = 50;
    public static final String REFERENCE_SEQUENCE = "10010";

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();
        List<Member> populationList = populationManager.makePopulation(POPULATION_QUANTITY, REFERENCE_SEQUENCE);
        List<Member> parents = populationManager.getPopulationParents(populationList, QUANTITY_PARENTS);
    }
}
