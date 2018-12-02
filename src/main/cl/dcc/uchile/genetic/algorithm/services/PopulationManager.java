package main.cl.dcc.uchile.genetic.algorithm.services;

import main.cl.dcc.uchile.genetic.algorithm.dto.Pair;

import java.util.ArrayList;
import java.util.List;

public class PopulationManager {

    public static final int MOTHER_INDEX = 0;
    public static final int INDEX_FIRST_CHAR_ASCII = 97;
    public static final int INDEX_LAST_CHAR_ASCII = 122;
    public static final String INIT_EMPTY_WORD = "";
    public static final int REDIX = 10;
    public static final int INITIAL_FOO_FITNESS = 10000;

    //TODO crear una generacion con la lista de los posibles soluciones (lista diagonales). Random. Tamaño es parametro
    public Generation makeFirstGeneration(int populationSize, int matrixLength) {

        List<String> sequenceList = makeGenesSequenceList(populationSize, matrixLength);

        List<Member> populationList  = new ArrayList<>(sequenceList.size());
        Member bestMember = new Member(null, INITIAL_FOO_FITNESS);
        for(String sequence : sequenceList){
            int fitness = calcMemberFitness(sequence);
            if(fitness < bestMember.getFitness()){
                bestMember = new Member(sequence, fitness);
            }
            populationList.add(new Member(sequence, fitness));
        }

        return new Generation(populationList, bestMember);
    }

    public List<String> makeGenesSequenceList(int populationSize, int matrixLength) {

        List<String> population = new ArrayList<>(populationSize);
        int matrixMaxIndex = matrixLength - 1;
        for (int index = 0; index < populationSize; index++) {
            String randomGeneratedSequence = generateRandomWordSequence(matrixLength, INIT_EMPTY_WORD, matrixMaxIndex);
            population.add(index, randomGeneratedSequence);
        }

        return population;
    }


    public int calcMemberFitness(String referenceSequence) {


        char[] referenceSequenceArray = referenceSequence.toCharArray();
        List<Pair> queensPositionsList = getQueensPositionsList(referenceSequenceArray);

        int fitness = calcDiagonalSum(queensPositionsList) + calcHorizontalSum(queensPositionsList)
                + calcVerticalSum(queensPositionsList);
        return fitness;
    }

    private List<Pair> getQueensPositionsList(char[] referenceSequenceArray){

        List<Pair> queensPositionsLists = new ArrayList<>();
        for(int i = 0; i < referenceSequenceArray.length; i++){
            queensPositionsLists.add(i, new Pair(i, referenceSequenceArray[i]));
        }
        return queensPositionsLists;
    }

    private int calcDiagonalSum(List<Pair> queensPositionsList){
        int queensQuantity = 0;

        for(int pivotIndex = 0; pivotIndex < queensPositionsList.size(); pivotIndex++){
            for(int iterativeIndex = pivotIndex + 1; iterativeIndex < queensPositionsList.size(); iterativeIndex++){
                Pair pivotQueenPosition = queensPositionsList.get(pivotIndex);
                Pair iterativeQueenPosition = queensPositionsList.get(iterativeIndex);

                int distanceInList = iterativeIndex - pivotIndex;
                int differenceAmongThem = pivotQueenPosition.getSecond() - iterativeQueenPosition.getSecond();

                if(distanceInList == Math.abs(differenceAmongThem)){
                    queensQuantity = queensQuantity + 1;
                }
            }
        }
        return queensQuantity;
    }

    private int calcVerticalSum(List<Pair> queensPositionsList){
        int queensQuantity = 0;

        for(int pivotIndex = 0; pivotIndex < queensPositionsList.size(); pivotIndex++){
            for(int iterativeIndex = pivotIndex + 1; iterativeIndex < queensPositionsList.size(); iterativeIndex++){
                Pair pivotQueenPosition = queensPositionsList.get(pivotIndex);
                Pair iterativeQueenPosition = queensPositionsList.get(iterativeIndex);

                if(pivotQueenPosition.getFirst() == iterativeQueenPosition.getFirst()){
                    queensQuantity = queensQuantity + 1;
                }
            }
        }
        return queensQuantity;
    }

    private int calcHorizontalSum(List<Pair> queensPositionsList){
        int queensQuantity = 0;

        for(int pivotIndex = 0; pivotIndex < queensPositionsList.size(); pivotIndex++){
            for(int iterativeIndex = pivotIndex + 1; iterativeIndex < queensPositionsList.size(); iterativeIndex++){
                Pair pivotQueenPosition = queensPositionsList.get(pivotIndex);
                Pair iterativeQueenPosition = queensPositionsList.get(iterativeIndex);

                if(pivotQueenPosition.getSecond() == iterativeQueenPosition.getSecond()){
                    queensQuantity = queensQuantity + 1;
                }
            }
        }
        return queensQuantity;
    }

    public List<Member> getParentsMemberList(List<Member> population, int quantityParents, int tournamentSelectionNumberAttempts){

        List<Member> selectedParentList = new ArrayList<>(quantityParents);

        for(int i = 0; i < quantityParents; i++){
            Member selectedParent = tournamentSelection(population, tournamentSelectionNumberAttempts);
            selectedParentList.add(selectedParent);
        }

        return selectedParentList;
    }

    public Member tournamentSelection(List<Member> population, int tournamentSelectionNumberAttempts) {

        Member best = null;
        for(int i = 0; i < tournamentSelectionNumberAttempts; i++){
            Member member = population.get(getRandomIntFromRange(population.size() - 1, 0));

            if(null == best || member.getFitness() < best.getFitness()){
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

    public Generation reproduction(List<Member> parents, int populationQuantity, double mutationRate, int referenceLength){

        List<Member> childList = new ArrayList<>(populationQuantity);

        Member bestMember = new Member(null, INITIAL_FOO_FITNESS);
        while(childList.size() < populationQuantity){

            int motherRandomIndex = getRandomIntFromRange(0, parents.size() - 1);
            Member mother = parents.get(motherRandomIndex);

            int fatherRandomIndex = getRandomIntFromRange(0, parents.size() - 1); //TODO change possible error to select the gen like mother and father to same time
            Member father = parents.get(fatherRandomIndex);

            Member child = makeChild(mother, father);
            child.mutation(mutationRate, referenceLength);
            child.setFitness(calcMemberFitness(child.getSequence()));
            childList.add(child);

            if(child.getFitness() < bestMember.getFitness()){
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
        return new Member(childSequence, INITIAL_FOO_FITNESS);
    }

    private char selectGeneBetweenMotherAndFather(char motherGene, char fatherGene){
        int selectedParentIndex = getRandomIntFromRange(0, 1);

        if(selectedParentIndex == MOTHER_INDEX){
            return motherGene;
        }
        return fatherGene;
    }

    //TODO this method must be abstract for all implementations or solutions using genetics algorithms
    private String generateRandomWordSequence(int maxLength, String initWord, int matrixMaxIndex){

        if(maxLength == 0){
            return initWord;
        } else {
            char randomCharacter = getRandomIndex(0, matrixMaxIndex);
            return generateRandomWordSequence(maxLength - 1, initWord.concat(Character.toString(randomCharacter)), matrixMaxIndex);
        }
    }

    public static char getRandomIndex(int from, int to){

        int randomNum = getRandomIntFromRange(from, to);
        char randomNumLikeChar = Character.forDigit(randomNum, REDIX);
        return randomNumLikeChar;
    }

}
