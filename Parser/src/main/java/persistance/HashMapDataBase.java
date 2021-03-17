package persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapDataBase implements DataBase {

    Map<String,Integer> words;

    public HashMapDataBase() {
        this.words = new HashMap<>();
    }

    @Override
    public void concatMultiInsert(String word) {
        words.put(
                word,
                words.getOrDefault(word,0)+1);
    }

    @Override
    public void executeMultiInsert() {
    }

    @Override
    public List<String> getStatistics() {
        List<String> statistics = new ArrayList<>();
        words.forEach((word,count)->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(word)
                    .append("-")
                    .append(count);
            statistics.add(stringBuilder.toString());
        });
        return statistics;
    }
}
