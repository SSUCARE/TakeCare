package com.ssu.takecare.AssistClass;

import java.util.ArrayList;
import java.util.List;

public class SugarLevelsGraph {
    List<Integer> SugarLevels_list=new ArrayList<>();

    public SugarLevelsGraph(List<Integer> SugarLevels_list){
        this.SugarLevels_list=SugarLevels_list;

    }
    public List<Integer> getSugarLevels_list() {
        return SugarLevels_list;
    }

    public void setSugarLevels_list(List<Integer> sugarLevels_list) {
        SugarLevels_list = sugarLevels_list;
    }
}
