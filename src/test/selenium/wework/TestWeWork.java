package selenium.wework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TestWeWork {

    static WebDriver webDriver;

    @BeforeAll
    public static void setup() {
//        System.setProperty("webdriver.chrome.driver", "/Users/xhou/WebDriver/bin/chromedriver");
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
//        webDriver = new ChromeDriver(options);
//        try {
//            webDriver = new RemoteWebDriver(
//                    new URL("http://127.0.0.1:9515"),
//                    new ChromeOptions());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        webDriver = new ChromeDriver();
    }

    @Test
    public void loadTest() throws IOException {
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame");
        webDriver.manage().deleteAllCookies();
        loadCookie(webDriver);
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame");
        System.out.println(webDriver.manage().getCookies());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveTest() throws IOException {
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveCookie();
    }

    private void saveCookie() throws IOException {
        FileWriter fileWriter = new FileWriter("src/test/selenium/wework/cookie.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(Cookie cookie : webDriver.manage().getCookies()) {
            bufferedWriter.write(
                    cookie.getName()+";"
                            + cookie.getValue()+";"
                            +cookie.getDomain()+";"
                            +cookie.getPath()+";"
                            +cookie.getExpiry()+";"
                            +cookie.isSecure());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    private void loadCookie(WebDriver webDriver) throws IOException {
//        FileReader fileReader = new FileReader("src/test/selenium/wework/cookie.txt");
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

    @AfterAll
    public static void tearDown() {
        webDriver.close();
    }

}

