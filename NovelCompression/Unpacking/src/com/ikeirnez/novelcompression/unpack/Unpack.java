package com.ikeirnez.novelcompression.unpack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by iKeirNez on 27/06/2014.
 */
public class Unpack {

    public static void main(String[] args){
        new Unpack();
    }

    private String output = "";

    public Unpack(){
        File inputFile = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            while (inputFile == null){
                System.out.print("Enter input file: ");
                inputFile = new File(bufferedReader.readLine());

                if (!inputFile.exists()){
                    System.out.println("ERROR: That file doesn't exist");
                } else if (!inputFile.canRead()){
                    System.out.println("ERROR: That file is unreadable");
                } else {
                    continue;
                }

                inputFile = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))){
            System.out.println("Decompressing...");
            int dictionarySize = Integer.parseInt(bufferedReader.readLine());
            String[] dictionary = new String[dictionarySize];

            for (int i = 0; i < dictionarySize; i++){
                dictionary[i] = bufferedReader.readLine();
            }

            bufferedReader.lines().parallel().forEach(s -> {
                String[] parts = s.split(" ");
                String lineProcessed = "";

                for (String part : parts){
                    switch (part) {
                        case ".":
                        case ",":
                        case "?":
                        case "!":
                        case ";":
                        case ":":
                            lineProcessed += part;
                            break;
                        case "R":
                        case "r":
                            lineProcessed += "\n";
                            break;
                        case "E": // we can safely discard these
                        case "e":
                            break;
                        case "-":
                            lineProcessed += "-";
                            break;
                        default:
                            if (!lineProcessed.equals("") && !lineProcessed.endsWith("\n") && !lineProcessed.endsWith("-")){ // don't add a space if we are at the start of the output, start of a new line or the character before was a "-"
                                lineProcessed += " ";
                            }

                            int i = -1;
                            String symbol = "";

                            try {
                                i = Integer.parseInt(part);
                            } catch (NumberFormatException e){
                                try {
                                    i = Integer.parseInt(part.substring(0, part.length() - 1));
                                    symbol = part.substring(part.length() - 1, part.length());
                                } catch (NumberFormatException e1){
                                    lineProcessed += part;
                                    continue;
                                }
                            }

                            switch (symbol) {
                                default: // if we find an un-recognised symbol, warn and discard
                                    System.out.println("WARNING: Unrecognized symbol \"" + symbol + "\"");
                                    break;
                                case "":
                                    lineProcessed += dictionary[i].toLowerCase();
                                    break;
                                case "^":
                                    String dWord = dictionary[i];
                                    char firstChar = Character.toUpperCase(dWord.charAt(0));
                                    lineProcessed += firstChar + dWord.substring(1);
                                    break;
                                case "!":
                                    lineProcessed += dictionary[i].toUpperCase();
                                    break;
                            }

                            break;
                    }
                }

                output += lineProcessed;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Output:\n\n" + output);
    }

}
