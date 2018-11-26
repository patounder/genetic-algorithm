package main.cl.dcc.uchile.genetic.algorithm.services;

import java.util.List;

public class Generation {

    private List<Member> populationList;
    private Member bestMember;


    public Generation() {

    }

    public Generation(List<Member> populationList, Member bestMember) {
        this.populationList = populationList;
        this.bestMember = bestMember;
    }

    public List<Member> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Member> populationList) {
        this.populationList = populationList;
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
                ", bestMember=" + bestMember +
                '}';
    }
}
