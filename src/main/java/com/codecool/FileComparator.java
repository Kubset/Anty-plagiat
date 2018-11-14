package com.codecool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileComparator {


    public List<String> compareData(List<String> o1, List<String> o2) {
        o1 = o1.stream().map(x -> x.replaceAll("[^a-zA-Z0-9]+", "")).collect(Collectors.toList());
        o2 = o2.stream().map(x -> x.replaceAll("[^a-zA-Z0-9]+", "")).collect(Collectors.toList());
        List<String> common = new ArrayList<>(o1);
        common.retainAll(o2);
        return common;
    }

}
