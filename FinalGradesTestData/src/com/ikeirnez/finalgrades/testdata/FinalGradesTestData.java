package com.ikeirnez.finalgrades.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by iKeirNez on 25/06/2014.
 */
public class FinalGradesTestData {

    public static void main(String[] args){
        new FinalGradesTestData();
    }

    private int storedNamesToUse = 5000;

    public FinalGradesTestData() {
        int amount = -1;

        System.out.println("Please enter the amount of records you wish to be generated");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            while (amount == -1){
                try {
                    int entered = Integer.parseInt(bufferedReader.readLine());

                    if (entered > storedNamesToUse){
                        System.out.println("We cannot generate that many names, we can only generate " + storedNamesToUse);
                    } else {
                        amount = entered;
                    }
                } catch (NumberFormatException e){
                    System.out.println("That was not an integer.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Random random = new Random();
        List<String> generatedNamesScores = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("names.txt")))){
            List<String> names = bufferedReader.lines().parallel().filter(s -> !s.equals("")).map(s -> s.replaceAll("\\|", ", ")).collect(Collectors.toList());

            for (int i = 0; i < amount; i++){
                StringBuilder stringBuilder = new StringBuilder();
                String name = names.get(random.nextInt(names.size()));
                names.remove(name);
                stringBuilder.append(name);
                stringBuilder.append(" - ");

                for (int x = 0; x < 5; x++){
                    stringBuilder.append(random.nextInt(101));

                    if (x != 4){
                        stringBuilder.append(", ");
                    }
                }

                generatedNamesScores.add(stringBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        generatedNamesScores.stream().forEach(System.out::println);
    }
}
