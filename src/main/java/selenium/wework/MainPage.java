package selenium.wework;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class MainPage extends BasePage{

    public MainPage() throws Exception{
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        driver.manage().deleteAllCookies();
        loadCookie(driver);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
    }

    public ContactPage toContactPage(){
        click(By.id("menu_contacts"));
        return new ContactPage(driver);
    }

    private void loadCookie(WebDriver webDriver) throws IOException {
        FileReader fileReader = new FileReader("src/test/selenium/wework/cookie.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine() )!= null) {
            StringTokenizer tokenizer = new StringTokenizer(line,";");//以";"作为分词器取值
            String name = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            String domain = tokenizer.nextToken();
            String path = tokenizer.nextToken();
            Date expiry = null;
            String dt = tokenizer.nextToken();
            if (!dt.equals("null")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyy", Locale.ENGLISH);// EEE = 星期几, MMM月份, dd日期, HHH 小时,mm分钟,ss秒, z时区, yyy年
                try {
                    expiry = sdf.parse(dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Boolean isSecure = Boolean.parseBoolean(tokenizer.nextToken());
            Cookie cookie = new Cookie(name, value, domain, path, expiry, isSecure);
            webDriver.manage().addCookie(cookie);
        }
        bufferedReader.close();
        fileReader.close();
    }

}
