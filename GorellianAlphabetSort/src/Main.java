import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by iKeirNez on 23/03/2014.
 */
public class Main {

    public static List<Character> alphabet = Collections.unmodifiableList(new ArrayList<Character>(){{
        for (char c = 'A'; c < 'Z'; c++){
            add(c);
        }
    }});

    public static void main(String[] args){
        new Main();
    }

    public Main(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            String[] data = bufferedReader.readLine().split(" ");
            int amount = Integer.parseInt(data[0]);
            List<Character> order = new ArrayList<>();

            for (char c : data[1].toUpperCase().toCharArray()){
                order.add(c);
            }

            List<Character> missing = new ArrayList<>(alphabet);
            missing.removeAll(order);

            if (missing.size() > 0){
                String missingChars = "";

                for (Character c : missing){
                    missingChars += c + " ";
                }

                System.out.println("Error: Missing letters: " + missingChars);
                return;
            }

            Set<Character> duplicates = new HashSet<>();
            Set<Character> set = new HashSet<>();
            duplicates.addAll(order.stream().filter(c -> !set.add(c)).map(c -> c).collect(Collectors.toList()));

            if (duplicates.size() > 0){
                String duplicateChars = "";

                for (Character c : duplicates){
                    duplicateChars += c + " ";
                }

                System.out.println("Error! Duplicate letters found in alphabet: " + duplicateChars);
                return;
            }

            List<String> inputs = new ArrayList<>();

            for (int i = 0; i < amount; i++){
                inputs.add(bufferedReader.readLine());
            }

            Collections.sort(inputs, (s1, s2) -> {
                int smallestInput = s1.length() < s2.length() ? s1.length() : s2.length();

                for (int i = 0; i < smallestInput; i++){
                    int difference = order.indexOf(s1.toUpperCase().charAt(i)) - order.indexOf(s2.toUpperCase().charAt(i));

                    if (difference != 0){
                        return difference;
                    }
                }

                return 0;
            });

            inputs.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
