package com.codecool;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class Main
{
    private final static String USER_PATH = "src/main/userFiles/";
    public static void main( String[] args ) throws InterruptedException,ExecutionException
    {
        PlagiatService plagiatService = null;

        Scanner sc = new Scanner(System.in);
        String[] inputs = sc.nextLine().split(" ");



        if(inputs.length == 2) {
            plagiatService = new PlagiatService(USER_PATH + inputs[0], Integer.valueOf(inputs[1])) ;
        } else if(inputs.length == 1) {
            plagiatService = new PlagiatService(inputs[0]) ;
        }
        if(plagiatService != null) {
            System.out.println(plagiatService.toString());
        }
    }

}
