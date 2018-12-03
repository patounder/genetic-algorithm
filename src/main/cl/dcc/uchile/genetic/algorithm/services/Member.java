package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.List;

public class Member implements MemberBehaviour {

    private List<Integer> sequence;
    private int fitness;

    public Member() {
        super();
    }

    public Member(List<Integer> sequence, int fitness) {
        this.sequence = sequence;
        this.fitness = fitness;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "main.cl.dcc.uchile.genetic.algorithm.services.Member{" +
                "sequence='" + sequence + '\'' +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public void mutation(double mutationRate, int referenceLength) {

        int genesQuantityToMutate = (int) (referenceLength * mutationRate);
        int maxIndexAvailable = referenceLength - 1;

        for(int i = 0; i < genesQuantityToMutate; i++){
            int indexSequenceToMutate = PopulationManager.getRandomIntFromRange(0, maxIndexAvailable);
            int newGene = PopulationManager.getRandomIntFromRange(0, maxIndexAvailable);
            sequence.set(indexSequenceToMutate, newGene);
        }
    }
}
