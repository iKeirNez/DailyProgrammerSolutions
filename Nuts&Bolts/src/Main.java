import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        new Main();
    }

    public final Map<String, AbstractMap.SimpleEntry<Integer, Integer>> values = new HashMap<>();
    public int rows;

    public Main(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            rows = Integer.parseInt(reader.readLine());

            for (int i = 0; i < rows * 2; i++){
                String line = reader.readLine();
                String[] data = line.split(" ");

                if (i < rows){ // read old values
                    values.put(data[0], new AbstractMap.SimpleEntry<>(Integer.parseInt(data[1]), 0));
                } else { // read new values
                    AbstractMap.SimpleEntry<Integer, Integer> existing = values.get(data[0]);
                    existing.setValue(Integer.parseInt(data[1]));
                    values.put(data[0], existing);
                }
            }

            for (String item : values.keySet()){ // print prices that need changed
                AbstractMap.SimpleEntry<Integer, Integer> prices = values.get(item);
                int difference = prices.getValue() - prices.getKey();

                if (difference != 0){
                    System.out.println(item + " " + (difference > 0 ? "+" : "") + difference);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
