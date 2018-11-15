public class Member {

    private String secuence;
    private int fitness;

    public Member() {
        super();
    }

    public Member(String secuence, int fitness) {
        this.secuence = secuence;
        this.fitness = fitness;
    }

    public String getSecuence() {
        return secuence;
    }

    public void setSecuence(String secuence) {
        this.secuence = secuence;
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
                "secuence='" + secuence + '\'' +
                ", fitness=" + fitness +
                '}';
    }
}
