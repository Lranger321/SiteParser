import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import persistance.DataBase;
import persistance.Logs;

import java.io.File;
import java.io.IOException;

public class Parser {

    //['()—\-.,!?\s:{}|;"»«©]+
    private final static String regex = "[/'\\\\«»\\s,.!?\";:)(\n\t]+";

    private final DataBase dataBase;
    private final String url;
    private final Writer writer;

    public Parser(DataBase dataBase, String url, Writer writer) {
        this.dataBase = dataBase;
        this.url = url;
        this.writer = writer;
    }

    public void parseFile() {
        try {
            Document doc = Jsoup.connect(url).get();
            parse(doc.children());
            dataBase.executeMultiInsert();
            writer.createStatistics();
        } catch (IOException ex) {
            Logs.error(ex);
            ex.printStackTrace();
        }
    }

    private void parse(Elements elements) {
        String text = elements.text();
        if (text.length() > 0) {
            String[] words = text.split(regex);
            for (String word : words) {
                if (word.replaceAll("\\W", "").length() > 0) {
                    dataBase.concatMultiInsert(word);
                }
            }
        }
    }


}
