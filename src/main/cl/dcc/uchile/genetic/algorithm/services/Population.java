package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.List;

public class Population {

    private List<Member> populationList;
    private int quantityBestMembers;
    private int maxFitness;


    public Population() {

    }

    public Population(List<Member> populationList, int quantityBestMembers, int maxFitness) {
        this.populationList = populationList;
        this.quantityBestMembers = quantityBestMembers;
        this.maxFitness = maxFitness;
    }

    public List<Member> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Member> populationList) {
        this.populationList = populationList;
    }

    public int getQuantityBestMembers() {
        return quantityBestMembers;
    }

    public void setQuantityBestMembers(int quantityBestMembers) {
        this.quantityBestMembers = quantityBestMembers;
    }

    public int getMaxFitness() {
        return maxFitness;
    }

    public void setMaxFitness(int maxFitness) {
        this.maxFitness = maxFitness;
    }

    @Override
    public String toString() {
        return "Population{" +
                "populationList=" + populationList +
                ", quantityBestMembers=" + quantityBestMembers +
                ", maxFitness=" + maxFitness +
                '}';
    }
}
