import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int POPULATION_SIZE = 10;
    public static final int QUANTITY_PARENTS = 5;
    public static final String REFERENCE_SEQUENCE = "10010";
    public static final double MUTATION_RATE = 0.2;

    public static void main(String[] args) {
        PopulationManager populationManager = new PopulationManager();
        Population bestPopulation = null;

        List<Population> resumeTotalPopulation = new ArrayList<>();

        long timeInit = System.currentTimeMillis();
        for(int i = 1; i <= 100; i++){

            Population population = populationManager.makePopulation(POPULATION_SIZE, REFERENCE_SEQUENCE); //TODO change reference 'sequence' to generic reference
            List<Member> parents = populationManager.getParentsMemberList(population.getPopulationList(), QUANTITY_PARENTS);
            Population newPopulation = populationManager.reproduction(parents, POPULATION_SIZE, MUTATION_RATE, REFERENCE_SEQUENCE);

            if((bestPopulation == null) || bestPopulation.getQuantityBestMembers() < newPopulation.getQuantityBestMembers()){
                bestPopulation = newPopulation;
            }

            resumeTotalPopulation.add(newPopulation);
            System.out.println("index population="+i+" best members="+ newPopulation.getQuantityBestMembers());
        }
        long duration = System.currentTimeMillis() - timeInit;
        System.out.printf("Best population. Total population=%d, best members=%d, time=%d", POPULATION_SIZE,bestPopulation.getQuantityBestMembers(), duration);

        ChartBuilder chartBuilder = new ChartBuilder();

        chartBuilder.showScatterPlotChart(resumeTotalPopulation);
    }
}
