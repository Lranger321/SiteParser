import java.util.List;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

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
            URL url = new URL(address);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            in.lines().forEach(line->{
                try {
                    writer.write(line);
                    writer.write("\n");
                } catch (IOException e) {
                    Main.logger.error(e);
                }
            });
            writer.flush();
            in.close();
        }catch (IOException e){
            Main.logger.error(e);
        }

    }

    public void createStatistics(){
        File file = new File("statistics.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            List<String> statistic = dataBase.getStatistic();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            statistic.forEach(line->{
                try {
                    writer.write(line);
                    writer.write("\n");
                } catch (IOException ex) {
                    Main.logger.error(ex);
                    ex.printStackTrace();
                }
            });
            writer.flush();
        } catch (SQLException | IOException ex) {
            Main.logger.error(ex);
            ex.printStackTrace();
        }

    }



}
