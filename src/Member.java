public class Member implements MemberBehaviour{

    public static final char ZERO_VALUE_GENE = '0';
    public static final char ONE_VALUE_GENE = '1';
    public static final int SEQUENCE_SIZE = 5;
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
        return "Member{" +
                "sequence='" + sequence + '\'' +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public void mutation(double mutationRate) {

        int genesQuantityToMutate = (int) (SEQUENCE_SIZE * mutationRate);
        char[] sequenceArray = this.sequence.toCharArray();
        for(int i = 0; i < genesQuantityToMutate; i++){
            int indexSequenceToMutate = PopulationManager.getRandomIntFromRange(0, 4);
            char gene = sequenceArray[indexSequenceToMutate];
            char newGene = geneSwap(gene);
            sequenceArray[indexSequenceToMutate] = newGene;
        }

        this.sequence = new String(sequenceArray);
    }

    private char geneSwap(char gene){

        if(gene == ZERO_VALUE_GENE){
            return ONE_VALUE_GENE;
        }

        return ZERO_VALUE_GENE;
    }
}
