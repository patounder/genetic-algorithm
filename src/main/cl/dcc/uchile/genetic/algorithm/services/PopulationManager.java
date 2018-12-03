package main.cl.dcc.uchile.genetic.algorithm.services;

import main.cl.dcc.uchile.genetic.algorithm.dto.Pair;

import java.util.ArrayList;
import java.util.List;

public class PopulationManager {

    public static final int MOTHER_INDEX = 0;
    public static final int INDEX_FIRST_CHAR_ASCII = 97;
    public static final int INDEX_LAST_CHAR_ASCII = 122;
    public static final int INITIAL_FOO_FITNESS = 10000;

    //TODO crear una generacion con la lista de los posibles soluciones (lista diagonales). Random. Tamaño es parametro
    public Generation makeFirstGeneration(int populationSize, int matrixLength) {

        List<List<Integer>> sequenceList = makeGenesSequenceList(populationSize, matrixLength);

        List<Member> populationList  = new ArrayList<>(sequenceList.size());
        Member bestMember = new Member(null, INITIAL_FOO_FITNESS);
        for(List<Integer> sequence : sequenceList){
            int fitness = calcMemberFitness(sequence);
            if(fitness < bestMember.getFitness()){
                bestMember = new Member(sequence, fitness);
            }
            populationList.add(new Member(sequence, fitness));
        }

        return new Generation(populationList, bestMember);
    }

    public List<List<Integer>> makeGenesSequenceList(int populationSize, int matrixLength) {

        List<List<Integer>> population = new ArrayList<>(populationSize);
        int matrixMaxIndex = matrixLength - 1;
        for (int index = 0; index < populationSize; index++) {
            List<Integer> randomGeneratedSequence = generateRandomSequence(matrixLength, matrixMaxIndex);
            population.add(index, randomGeneratedSequence);
        }
        return population;
    }


    public int calcMemberFitness(List<Integer> referenceSequence) {

        List<Pair> queensPositionsList = getQueensPositionsList(referenceSequence);

        int fitness = calcDiagonalSum(queensPositionsList) + calcHorizontalSum(queensPositionsList)
                + calcVerticalSum(queensPositionsList);
        return fitness;
    }

    private List<Pair> getQueensPositionsList(List<Integer> referenceSequenceArray){

        List<Pair> queensPositionsLists = new ArrayList<>();
        for(int index = 0; index < referenceSequenceArray.size(); index++){
            queensPositionsLists.add(index, new Pair(index, referenceSequenceArray.get(index)));
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

        int motherSequenceSize = mother.getSequence().size();
        int fatherSequenceSize = father.getSequence().size();
        if( motherSequenceSize != fatherSequenceSize){
            System.out.println("mother's genes and father's genes has not same length");
            return null;
        }

        List<Integer> childGenes = new ArrayList<>(motherSequenceSize);
        for(int index = 0 ; index < motherSequenceSize; index++){
            int createdGene = selectGeneBetweenMotherAndFather(mother.getSequence().get(index), father.getSequence().get(index));
            childGenes.add(index, createdGene);
        }
        return new Member(childGenes, INITIAL_FOO_FITNESS);
    }

    private int selectGeneBetweenMotherAndFather(int motherGene, int fatherGene){
        int selectedParentIndex = getRandomIntFromRange(0, 1);

        if(selectedParentIndex == MOTHER_INDEX){
            return motherGene;
        }
        return fatherGene;
    }

    //TODO this method must be abstract for all implementations or solutions using genetics algorithms
    private List<Integer> generateRandomSequence(int maxLength, int matrixMaxIndex){

        List<Integer> generatedSequence = new ArrayList<>(matrixMaxIndex);

        for(int index = 0; index < maxLength; index++){
            int randomQueenPosition = getRandomIntFromRange(0, matrixMaxIndex);
            generatedSequence.add(index, randomQueenPosition);
        }

        return generatedSequence;
    }
}
