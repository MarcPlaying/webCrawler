import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class run {

    public static MySQL mysql = null;
    public static Config cfg = null;
    public static ArrayList<Thread> list = new ArrayList<Thread>();

    public static void main(String[] args) throws Exception {
        System.out.println(Prefix.INFO + "webCrawler - By Marc Andre Herpers - MIT");
        cfg = new Config();
        mysql = new MySQL(cfg.getString("mysqlusername"), cfg.getString("mysqlpassword"),
				cfg.getString("mysqldatabase"), cfg.getString("mysqlip"),
				cfg.getInt("mysqlport"));


        for(int i = 0; i < cfg.getInt("threads"); i++) {
            list.add(new CusThread(cfg.getString("file")));
        }

        for(Thread thr : list) {
            thr.start();
            TimeUnit.SECONDS.sleep(500);
        }
    }
}