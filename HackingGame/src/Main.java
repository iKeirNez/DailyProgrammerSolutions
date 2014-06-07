import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iKeirNez on 07/06/2014.
 */
public class Main {

    public static int WORD_AMOUNT_MIN = 5, WORD_LENGTH_MIN = 4, GUESSES = 4;
    public static double WORD_AMOUNT_MULTIPLIER = 2, WORD_LENGTH_MULTIPLIER = 2.2;

    public static void main(String[] args){
        new Main();
    }

    public Main(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.print("Difficulty (1-5)? ");
            int difficulty = -1;

            while (difficulty == -1){
                difficulty = Integer.parseInt(bufferedReader.readLine()) - 1;

                if (difficulty < 0 || difficulty > 4){
                    System.out.print("Incorrect difficulty value, please try again: ");
                    difficulty = -1;
                }
            }

            int wordAmount = WORD_AMOUNT_MIN + ((int) WORD_AMOUNT_MULTIPLIER * difficulty);
            int wordLength = WORD_LENGTH_MIN + ((int) WORD_LENGTH_MULTIPLIER * difficulty);
            List<String> matchingWords = new ArrayList<>();

            try (BufferedReader wordFileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("enable1.txt")))){
                wordFileReader.lines().filter(s -> s.length() == wordLength).forEach(matchingWords::add);
            }

            List<String> chosenWords = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < wordAmount; i++){
                chosenWords.add(matchingWords.get(random.nextInt(matchingWords.size())).toUpperCase());
            }

            String answer = chosenWords.get(random.nextInt(chosenWords.size()));
            char[] answerCharArray = answer.toCharArray();

            chosenWords.forEach(System.out::println);
            int guessesLeft = GUESSES;

            while (true){
                if (guessesLeft == 0){
                    System.out.println("Sorry, you ran out of guesses, you lose!");
                    return;
                }

                System.out.print("Guess (" + guessesLeft + " left)? ");
                String s = bufferedReader.readLine().toUpperCase();

                if (s.length() != wordLength){
                    System.out.println("That word is not " + wordLength + " characters long");
                    continue;
                }

                int amountCorrect = 0;
                char[] charArray = s.toCharArray();

                for (int i = 0; i < charArray.length; i++){
                    if (charArray[i] == answerCharArray[i]){
                        amountCorrect++;
                    }
                }

                System.out.println(amountCorrect + "/" + wordLength + " correct");

                if (s.equals(answer)){
                    System.out.println("You win!");
                    System.exit(0);
                    return;
                }

                guessesLeft--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
