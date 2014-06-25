package com.ikeirnez.finalgrades.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iKeirNez on 25/06/2014.
 */
public class FinalGradesTestData {

    public static void main(String[] args){
        new FinalGradesTestData();
    }

    private List<String> names = new ArrayList<>(5000);

    public FinalGradesTestData() {
        System.out.println("Please wait, loading names...");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("names.txt")))){
            bufferedReader.readLine(); // skip first line
            bufferedReader.lines().parallel().filter(s -> !s.equals("")).map(s -> s.replaceAll("\\|", ", ")).forEach(names::add);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int amount = -1;

        System.out.println("Please enter the amount of records you wish to be generated");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            while (amount == -1){
                try {
                    int entered = Integer.parseInt(bufferedReader.readLine());

                    if (entered > names.size()){
                        System.out.println("We cannot generate that many names, we can only generate " + names.size());
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

        generatedNamesScores.stream().forEach(System.out::println);
    }
}
