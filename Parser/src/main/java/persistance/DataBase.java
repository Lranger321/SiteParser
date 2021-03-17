package persistance;

import java.util.List;

public interface DataBase {

    void concatMultiInsert(String word);

    void executeMultiInsert();

    List<String> getStatistics();

}
