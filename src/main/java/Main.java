import redis.clients.jedis.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    private static final String HOST = "localhost";
    private static final String PORT = "6379";
    private static final String KEY_TO_SET = "value to increment";
    private static final String NUMBER = "2";
    private static final String KEY_LPUSH = "comments";
    private static final String FILE_PATH = "src/main/resources/result.txt";
    private static final String ENTER = "\n";

    public static void main(String[] args) throws InterruptedException, IOException {
        //1 task
        Jedis jedis = new Jedis(HOST, Integer.parseInt(PORT));
        jedis.set(KEY_TO_SET, NUMBER);
        long response = jedis.incr(KEY_TO_SET);
        System.out.println(response);
        //2.1 task
        jedis.lpush(KEY_LPUSH, "bad", "good", "normal", "i will never buy it");
        File file = new File(FILE_PATH);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            List<String> stringList = jedis.brpop(30, KEY_LPUSH);
            System.out.println(stringList);
            //2.2 task
            FileWriter fileWriter = new FileWriter(file, true);
            if(stringList != null) {
                fileWriter.write(stringList.toString());
                fileWriter.write(ENTER);
            }
            fileWriter.close();
        }
    }
}
