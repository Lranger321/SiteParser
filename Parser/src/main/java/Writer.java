import org.jsoup.Jsoup;
import persistance.DataBase;
import persistance.Logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    private final DataBase dataBase;

    public Writer(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void writeToFile(String address) {
        File file = new File("site.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            String html = Jsoup.connect(address).get().html();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                try {
                    writer.write(html);
                } catch (IOException e) {
                    Logs.error(e);
                }
            writer.flush();
            writer.close();
        }catch (IOException e){
            Logs.error(e);
        }

    }

    public void createStatistics(){
        File file = new File("statistics.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            List<String> statistic = dataBase.getStatistics();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            statistic.forEach(line->{
                try {
                    writer.write(line);
                    writer.write("\n");
                } catch (IOException ex) {
                    Logs.error(ex);
                    ex.printStackTrace();
                }
            });
            writer.flush();
        } catch (IOException ex) {
            Logs.error(ex);
            ex.printStackTrace();
        }

    }



}
