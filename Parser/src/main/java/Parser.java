import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Parser {

    private final static String regex = "{' ', ',', '.', '!', '?','\"', ';', ':', '[', ']', '(', ')',\n" +
            "'\\n', '\\r', '\\t'}";

    private final DataBase dataBase;
    private final File file;
    private final Writer writer;

    public Parser(DataBase dataBase, Writer writer) {
        this.dataBase = dataBase;
        this.writer = writer;
        file = new File("site.txt");
    }

    public void parseFile() throws IOException {
        Document document = Jsoup.parse(file,"UTF-8");
        parse(document.getAllElements());
        try {
            dataBase.executeInsert();
        } catch (SQLException ex) {
            Main.logger.error(ex);
            ex.printStackTrace();
        }
        writer.createStatistics();
    }

    private void parse(Elements elements){
        for(Element element: elements){
            parse(element.children());
            String text = element.text();
            if(text.length() > 0) {
                String[] words = text.split(" ");
                for(String word:words){
                    addWord(word);
                }
            }
        }
    }


    public void addWord(String word){
        try {
            dataBase.concatMultiInsert(word);
        } catch (SQLException ex) {
            Main.logger.error(ex);
            ex.printStackTrace();
        }
    }


}
