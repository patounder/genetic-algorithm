import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PopulationManager {


    public static final int NUMBER_ATTEMPTS = 30;
    public static final int MOTHER_INDEX = 0;
    public static final int MAX_FITNESS = 5;
    public static final int INITIAL_FITNESS = 0;

    public Population makePopulation(int populationSize, String referenceSequence) {

        List<String> sequenceList = makeSequenceList(populationSize, referenceSequence.length());

        AtomicReference<AtomicInteger> quantityBestMembers = new AtomicReference<>(new AtomicInteger());
        List<Member> populationList = sequenceList.stream().map(sequence -> {
            int fitness = calcMemberFitness(sequence, referenceSequence);

            quantityBestMembers.set(updateBestMembersQuantity(quantityBestMembers.get(), fitness));

            return new Member(sequence, fitness);
        }).collect(Collectors.toList());

        return new Population(populationList, quantityBestMembers.get().get());
    }

    public List<String> makeSequenceList(int populationSize, int memberLength) {

        List<String> population = new ArrayList<>(populationSize);

        for (int index = 0; index < populationSize; index++) {
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

    public int calcMemberFitness(String referenceSequence, String sequenceToEvaluate) {
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

    public List<Member> getParentsMemberList(List<Member> population, int quantityParents){

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

    public Population reproduction(List<Member> parents, int populationQuantity, double mutationRate, String referenceSequence){

        List<Member> childList = new ArrayList<>(populationQuantity);

        AtomicReference<AtomicInteger> quantityBestMembers = new AtomicReference<>(new AtomicInteger());

        while(childList.size() < populationQuantity){
            int motherRandomIndex = getRandomIntFromRange(0, parents.size() - 1);
            Member mother = parents.get(motherRandomIndex);
            int fatherRandomIndex = getRandomIntFromRange(0, parents.size() - 1); //TODO possible error to select the gen like mother and father to same time
            Member father = parents.get(fatherRandomIndex);
            Member child = makeChild(mother, father);
            child.mutation(mutationRate);
            child.setFitness(calcMemberFitness(child.getSequence(), referenceSequence));
            childList.add(child);

            quantityBestMembers.set(updateBestMembersQuantity(quantityBestMembers.get(), child.getFitness()));
        }

        return new Population(childList, quantityBestMembers.get().get());
    }


    private Member makeChild(Member mother, Member father){

        char[] motherGenes = mother.getSequence().toCharArray();//TODO analyze option for strings like generic object
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

        String childSequence = new String(childGenes);
        return new Member(childSequence, INITIAL_FITNESS);
    }

    private char selectGeneBetweenMotherAndFather(char motherGene, char fatherGene){
        int selectedParentIndex = getRandomIntFromRange(0, 1);

        if(selectedParentIndex == MOTHER_INDEX){
            return motherGene;
        }
        return fatherGene;
    }

    private AtomicInteger updateBestMembersQuantity(AtomicInteger bestMembersQuantity, int fitnessMember){

        if(fitnessMember == MAX_FITNESS){ //TODO parametrizar criterio de mejores miembros
            return new AtomicInteger(bestMembersQuantity.get() + 1);
        }

        return bestMembersQuantity;
    }
}
