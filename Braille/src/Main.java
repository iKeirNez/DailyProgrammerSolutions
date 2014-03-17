import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final Map<String, Character> dictionary = Collections.unmodifiableMap(new HashMap<String, Character>(){{
        put("O.....", 'a');
        put("O.O...", 'b');
        put("OO....", 'c');
        put("OO.O..", 'd');
        put("O..O..", 'e');
        put("OOO...", 'f');
        put("OOOO..", 'g');
        put("O.OO..", 'h');
        put(".OO...", 'i');
        put(".OOO..", 'j');
        put("O...O.", 'k');
        put("O.O.O.", 'l');
        put("OO..O.", 'm');
        put("OO.OO.", 'n');
        put("O..OO.", 'o');
        put("OOO.O.", 'p');
        put("OOOOO.", 'q');
        put("O.OOO.", 'r');
        put(".OO.O.", 's');
        put(".OOOO.", 't');
        put("O...OO", 'u');
        put("O.O.OO", 'v');
        put(".OOO.O", 'w');
        put("OO..OO", 'x');
        put("OO.OOO", 'y');
        put("O..OOO", 'z');
    }});

    public static final String[] rows = new String[3];

    public static void main(String[] args){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            for (int i = 0; i < 3; i++){
                rows[i] = bufferedReader.readLine();
            }

            if (!(rows[0].length() == rows[1].length() || rows[1].length() == rows[2].length())){
                throw new RuntimeException("Inconsistent character count");
            }

            String[][] rowsData = {rows[0].split(" "), rows[1].split(" "), rows[2].split(" ")};
            int charCount = rowsData[0].length;

            String output = "";

            for (int i = 0; i < charCount; i++){
                String braille = rowsData[0][i] + rowsData[1][i] + rowsData[2][i];

                if (braille.chars().parallel().filter(c -> c != ' ' && c != 'O' && c != '.').sum() > 0){ // lambda coolness to check string doesn't contain incorrect chars
                    throw new RuntimeException("Input contains invalid characters, input must only consist of Os, .s & spaces");
                }

                if (braille.length() != 6){
                    throw new RuntimeException("Input " + braille + " is not the correct length (!= 6)");
                }

                char englishChar = dictionary.get(braille);
                output += englishChar;
            }

            System.out.println(output);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
