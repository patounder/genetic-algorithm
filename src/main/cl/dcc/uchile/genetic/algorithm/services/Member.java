package main.cl.dcc.uchile.genetic.algorithm.services;

public class Member implements MemberBehaviour {

    private String sequence; //TODO change name to generic value o purpose
    private int fitness;

    public Member() {
        super();
    }

    public Member(String sequence, int fitness) {
        this.sequence = sequence;
        this.fitness = fitness;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
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
    public void mutation(double mutationRate, String referenceSequence) {

        int genesQuantityToMutate = (int) (referenceSequence.length() * mutationRate);
        char[] sequenceArray = this.sequence.toCharArray();

        for(int i = 0; i < genesQuantityToMutate; i++){
            int indexSequenceToMutate = PopulationManager.getRandomIntFromRange(0, referenceSequence.length() - 1);
            char newGene = PopulationManager.getRandomChar();
            sequenceArray[indexSequenceToMutate] = newGene;
        }

        this.sequence = new String(sequenceArray);
    }
}
