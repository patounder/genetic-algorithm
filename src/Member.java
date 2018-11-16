public class Member implements MemberBehaviour{

    public static final char ZERO_VALUE_GENE = '0';
    public static final char ONE_VALUE_GENE = '1';
    private String sequence;
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

    @Override()
    public void mutation(double mutationRate) {
        int genesQuantityToMutate = (int) (5 * mutationRate);
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
