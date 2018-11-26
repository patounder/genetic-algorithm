package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PopulationManager {


    public static final int NUMBER_ATTEMPTS = 30;
    public static final int MOTHER_INDEX = 0;
    public static final int MAX_FITNESS = 4;
    public static final int INITIAL_FITNESS = 0;
    public static final int INDEX_FIRST_CHAR_ASCII = 97;
    public static final int INDEX_LAST_CHAR_ASCII = 122;
    public static final String INIT_EMPTY_WORD = "";

    public Generation makeFirstGeneration(int populationSize, String referenceSequence) {

        List<String> sequenceList = makeGenesSequenceList(populationSize, referenceSequence.length());

        List<Member> populationList  = new ArrayList<>(sequenceList.size());
        Member bestMember = new Member();
        for(String sequence : sequenceList){
            int fitness = calcMemberFitness(sequence, referenceSequence);
            if(fitness >= bestMember.getFitness()){
                bestMember = new Member(sequence, fitness);
            }
            populationList.add(new Member(sequence, fitness));
        }

        return new Generation(populationList, bestMember);
    }

    public List<String> makeGenesSequenceList(int populationSize, int referenceSequenceLength) {

        List<String> population = new ArrayList<>(populationSize);

        for (int index = 0; index < populationSize; index++) {
            String randomGeneratedSequence = generateRandomWordSequence(referenceSequenceLength, INIT_EMPTY_WORD);
            population.add(index, randomGeneratedSequence);
        }

        return population;
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

    public static char getRandomChar(){
        return (char) getRandomIntFromRange(INDEX_FIRST_CHAR_ASCII, INDEX_LAST_CHAR_ASCII);
    }

    public Generation reproduction(List<Member> parents, int populationQuantity, double mutationRate, String referenceSequence){

        List<Member> childList = new ArrayList<>(populationQuantity);

        Member bestMember = new Member();
        while(childList.size() < populationQuantity){
            int motherRandomIndex = getRandomIntFromRange(0, parents.size() - 1);
            Member mother = parents.get(motherRandomIndex);
            int fatherRandomIndex = getRandomIntFromRange(0, parents.size() - 1); //TODO possible error to select the gen like mother and father to same time
            Member father = parents.get(fatherRandomIndex);
            Member child = makeChild(mother, father);
            child.mutation(mutationRate, referenceSequence);
            child.setFitness(calcMemberFitness(child.getSequence(), referenceSequence));
            childList.add(child);

            if(child.getFitness() > bestMember.getFitness()){
                bestMember = new Member(child.getSequence(), child.getFitness());
            }
        }

        return new Generation(childList, bestMember);
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

    //TODO this method must be abstract for all implementations or solutions using genetics algorithms
    private String generateRandomWordSequence(int maxLength, String initWord){
        //ASCII 97 - 122
        if(maxLength == 0){
            return initWord;
        } else {
            char randomCharacter = getRandomChar();
            return generateRandomWordSequence(maxLength - 1, initWord.concat(Character.toString(randomCharacter)));
        }
    }

}
