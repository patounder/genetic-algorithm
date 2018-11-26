package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.List;

public class Generation {

    private List<Member> populationList;
    private int quantityBestMembers;
    private Member bestMember;


    public Generation() {

    }

    public Generation(List<Member> populationList, int quantityBestMembers, Member bestMember) {
        this.populationList = populationList;
        this.quantityBestMembers = quantityBestMembers;
        this.bestMember = bestMember;
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

    public Member getBestMember() {
        return bestMember;
    }

    public void setBestMember(Member bestMember) {
        this.bestMember = bestMember;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "populationList=" + populationList +
                ", quantityBestMembers=" + quantityBestMembers +
                ", bestMember=" + bestMember +
                '}';
    }
}
