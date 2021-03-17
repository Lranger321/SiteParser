import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Parser {

    private final static String regex = "[/'\\\\«»\\s,.!?\";:)(\n\t]+";

    private final DataBase dataBase;
    private final File file;
    private final Writer writer;

    public Parser(DataBase dataBase, Writer writer) {
        this.dataBase = dataBase;
        this.writer = writer;
        file = new File("site.txt");
    }

    public void parseFile(){
        Document doc;
        try {
            doc = Jsoup.connect("http://az.lib.ru/t/tolstoj_lew_nikolaewich/text_0073.shtml").get();
            parse(doc.children());
            try {
                dataBase.executeInsert();
            } catch (SQLException ex) {
                Main.logger.error(ex);
                ex.printStackTrace();
            }
            writer.createStatistics();
        } catch (IOException ex) {
            Main.logger.error(ex);
            ex.printStackTrace();
        }
    }

    //TODO regex
    private void parse(Elements elements) {
        String text = elements.text();
        if (text.length() > 0) {
            //['()—\-.,!?\s:{}|;"»«©]+
            String[] words = text.split(regex);
            for (String word : words) {
                if (word.replaceAll("\\w|\\d|-|©|—", "").length() > 0) {
                    dataBase.concatMultiInsert(word);
                }
            }
        }
    }


}
