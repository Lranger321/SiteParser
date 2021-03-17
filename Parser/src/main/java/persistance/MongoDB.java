package persistance;

import java.util.List;

public class MongoDB implements DataBase {

    private StringBuilder multiInsert;

    public MongoDB() throws Exception {
        throw new Exception("some troubles");
    }

    @Override
    public void concatMultiInsert(String word) {

    }

    @Override
    public void executeMultiInsert() {

    }

    @Override
    public List<String> getStatistics() {
        return null;
    }
}
