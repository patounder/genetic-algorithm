import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PopulationManager {


    public static final int NUMBER_ATTEMPTS = 30;

    public List<Member> makePopulation(int populationQuantity, String referenceSequence) {

        List<String> sequenceList = initializePopulation(populationQuantity, referenceSequence.length());
        List<Member> population = new ArrayList<>(populationQuantity);
        Member auxMember;
        int fitness = 0;

        for (int i = 0; i < sequenceList.size(); i++) {
            fitness = setFitness(sequenceList.get(i), referenceSequence);
            auxMember = new Member(sequenceList.get(i), fitness);
            population.add(auxMember);
        }

        return population;
    }

    public List<String> initializePopulation(int populationQuantity, int memberLength) {

        List<String> population = new ArrayList<>(populationQuantity);

        for (int index = 0; index < populationQuantity; index++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 31 + 1);
            String randomNumLikeString = Integer.toBinaryString(randomNum);
            String finalSequence = normalizeSequence(randomNumLikeString, memberLength);
            population.add(index, finalSequence);
        }

        return population;
    }

    private String normalizeSequence(String sequence, int referenceLength) {
        String finalSequence = sequence;

        while (finalSequence.length() < referenceLength) {
            finalSequence = 0 + finalSequence;
        }

        return finalSequence;
    }

    public int setFitness(String referenceSequence, String sequenceToEvaluate) {
        int fitness = 0;

        char[] referenceSequenceArray = referenceSequence.toCharArray();
        char[] sequenceToEvaluateArray = sequenceToEvaluate.toCharArray();

        for (int i = 0; i < referenceSequence.length(); i++) {
            if (referenceSequenceArray[i] == sequenceToEvaluateArray[i]) {
                fitness = fitness + 1;
            }
        }
        return fitness;
    }

    public List<Member> getPopulationParents(List<Member> population, int quantityParents){

        List<Member> selectedParentList = new ArrayList<>(quantityParents);

        for(int i = 0; i < quantityParents; i++){
            Member selectedParent = tournamentSelection(population);
            selectedParentList.add(selectedParent);
        }

        return selectedParentList;
    }

    public Member tournamentSelection(List<Member> population) {

        Member best = null;
        for(int i = 0; i <= NUMBER_ATTEMPTS; i++){
            Member member = population.get(getIntRandomRange(population.size() - 1, 0));

            if(null == best || member.getFitness() > best.getFitness()){
                best = member;
            }
        }
        return best;
    }


    private int getIntRandomRange(int from, int to){
        return (int) (Math.random()*((to - from) + 1)) + from;
    }

    public List<Member> reproduction(List<Member> parents){
        return null;
    }
}
