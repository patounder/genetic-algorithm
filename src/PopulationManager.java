import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PopulationManager {


    public static final int NUMBER_ATTEMPTS = 30;
    public static final int MOTHER_INDEX = 0;

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
            Member member = population.get(getRandomIntFromRange(population.size() - 1, 0));

            if(null == best || member.getFitness() > best.getFitness()){
                best = member;
            }
        }
        return best;
    }


    public static int getRandomIntFromRange(int from, int to){
        return (int) (Math.random()*((to - from) + 1)) + from;
    }

    public List<Member> reproduction(List<Member> parents, int populationQuantity, double mutationRate){

        List<Member> childList = new ArrayList<>(populationQuantity);

        while(childList.size() < populationQuantity){

            Member mother = parents.get(getRandomIntFromRange(0, parents.size() - 1));
            Member father = parents.get(getRandomIntFromRange(0, parents.size() - 1));
            Member child = makeChild(mother, father);

            child.mutation(mutationRate);
            childList.add(child);
        }

        return childList;
    }


    private Member makeChild(Member mother, Member father){

        char[] motherGenes = mother.getSequence().toCharArray();
        char[] fatherGenes = father.getSequence().toCharArray();

        if(motherGenes.length != fatherGenes.length){
            System.out.println("mother's genes and father's genes has not same length");
            return null;
        }

        char[] childGenes = new char[motherGenes.length];
        for(int i = 0 ; i < motherGenes.length; i++){
            char createdGene = selectGeneBetweenMotherAndFather(motherGenes[i], fatherGenes[i]);
            childGenes[i] = createdGene;
        }

        String sequence = new String(childGenes);
        return new Member(sequence,0);
    }

    private char selectGeneBetweenMotherAndFather(char motherGene, char fatherGene){
        int selectedParentIndex = getRandomIntFromRange(0, 1);

        if(selectedParentIndex == MOTHER_INDEX){
            return motherGene;
        }
        return fatherGene;
    }
}
