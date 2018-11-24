package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.List;

public class Population {

    private List<Member> populationList;
    private int quantityBestMembers;

    public Population() {

    }

    public Population(List<Member> populationList, int quantityBestMembers) {
        this.populationList = populationList;
        this.quantityBestMembers = quantityBestMembers;
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

    @Override
    public String toString() {
        return "main.cl.dcc.uchile.genetic.algorithm.services.Population{" +
                "populationList=" + populationList +
                ", quantityBestMembers=" + quantityBestMembers +
                '}';
    }
}
