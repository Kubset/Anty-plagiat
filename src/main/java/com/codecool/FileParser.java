package com.codecool;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {

    private Charset charset = StandardCharsets.UTF_8;

    private String convert(String path) throws IOException {

        InputStream inputStream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
        String fileToString = br.lines().collect(Collectors.joining(System.lineSeparator()));
        inputStream.close();
        return fileToString;
    }

    public List<String> parse(String path) {

        String fileToString = "";
        try {
            fileToString = convert(path);
        }
        catch (IOException e) {
            System.out.println("There is no such file");
        }

        return Arrays.stream(fileToString.split("\\.\n?"))
                .collect(Collectors.toList());
    }
}
