import java.io.FileNotFoundException;
import java.io.IOException;

public class CusThread extends Thread {
    public String file;
    public CusThread(String file) {
        this.file = file;
    }
    
    @Override
    public void run() {
        System.out.println(Prefix.INFO + "Started Thread");
        try {
            Crawler crawler = new Crawler(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}