package com.ikeirnez.novelcompression.compress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by iKeirNez on 28/06/2014.
 */
public class Compress {

    private static final Pattern symbolPattern = Pattern.compile(".*[?.,!;:]+$");

    public static void main(String[] args){
        new Compress();
    }

    private Set<String> dictionary = new LinkedHashSet<>();
    private String compressed = "", output = "";

    public Compress(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter text:");

            while (true){
                String line = bufferedReader.readLine();

                if (line.equals("")){
                    break;
                }

                handleLine(line);
            }

            compressed = compressed.substring(0, compressed.length() - 2) + "E";
        } catch (IOException e) {
            e.printStackTrace();
        }

        output = dictionary.size() + "\n";

        for (String dWord : dictionary){
            output += dWord + "\n";
        }

        output += compressed;

        System.out.println("Compressed data is:\n" + output);
    }

    public void handleLine(String line){
        if (line.contains("\n")){
            for (String l : line.split("\n")){
                handleLine(l);
            }
        } else {
            String[] words = line.split(" ");

            for (String word : words){
                handleWord(word);
            }

            compressed += "R ";
        }
    }

    public void handleWord(String word){
        if (word.contains("-")){
            String[] split = word.split("-");

            for (int i = 0; i < split.length; i++){
                String s = split[i];
                handleWord(s);

                if (i != split.length - 1){
                    compressed += "- ";
                }
            }
        } else {
            String cleansedWord = word.toLowerCase();
            String append = "";

            if (symbolPattern.matcher(cleansedWord).matches()){
                append += " " + cleansedWord.charAt(cleansedWord.length() - 1);
                cleansedWord = cleansedWord.substring(0, cleansedWord.length() - 1);
            }

            dictionary.add(cleansedWord);
            int index = getIndex(dictionary, cleansedWord);
            WordType wordType = WordType.match(word);
            compressed += index + wordType.getSuffix() + append + " ";
        }
    }

    public int getIndex(Collection<String> collection, String string){
        List<String> list = new ArrayList<>(collection);

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(string)){
                return i;
            }
        }

        return -1;
    }

}
