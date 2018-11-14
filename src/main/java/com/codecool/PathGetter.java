package com.codecool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathGetter {

    public List<String> getPaths() {
        List<String> paths = new ArrayList<>();
        try {
            Stream<Path> pathsStream = Files.walk(Paths.get("src/main/resources"));
            paths = pathsStream.filter(Files::isRegularFile)
                    .map(x -> x.toString())
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            System.out.println("There is no files in the folder");
        }
        return paths;
    }
}
