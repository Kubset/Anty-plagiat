package com.codecool;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PlagiatService {

    private FileParser fileParser;
    private FileComparator fileComparator;
    private PathGetter pathGetter;
    private String userPath;
    private ExecutorService executorService;
    private double plagiatPercent;


    public PlagiatService(String userPath, int threads) {
        this(userPath);
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    public PlagiatService(String userPath) {
        this.fileParser = new FileParser();
        this.fileComparator = new FileComparator();
        this.pathGetter = new PathGetter();
        this.userPath = userPath;
        this.plagiatPercent = calculatePlagiatPercent();
    }

    public double getPlagiatPercent() {
       return plagiatPercent*100;
    }

    public double calculatePlagiatPercent() {
        List<String> paths = pathGetter.getPaths();

        if(this.executorService == null) {
            this.executorService = Executors.newFixedThreadPool(paths.size());
        }

        List<String> userData = fileParser.parse(userPath);

        List<List<String>> filesData = paths
                                        .stream()
                                        .map(x -> executorService.submit(() -> fileParser.parse(x)))
                                        .map(x-> {
                                            try {
                                                return x.get();
                                            } catch (InterruptedException | ExecutionException e) {
                                                return null;
                                            }
                                        })
                                        .collect(Collectors.toList());

        Set<String> duplicates = filesData
                                    .stream()
                                    .map(x -> fileComparator.compareData(userData, x))
                                    .flatMap(x -> x.stream())
                                    .collect(Collectors.toSet());


        double duplicatesLetterCount = duplicates.stream().reduce((x,y) -> x+y).orElse("").length();
        double fileLettersCount = userData.stream().reduce((x,y) -> x+y).orElse("").length();


        return duplicatesLetterCount/fileLettersCount;

    }

    @Override
    public String toString() {
        return String.format("plagiat percent: %.2f thread used %s",
                            getPlagiatPercent(), Thread.activeCount()-2);
    }



}
