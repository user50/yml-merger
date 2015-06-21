import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import play.test.*;
import play.libs.F.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Your new application is ready.");
            }
        });
    }

    @Test
    public void submittingForm() throws Exception {
        //initialize Chrome driver
//        System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-2.35.0\\chromedriver_win32_2.2\\chromedriver.exe");
        WebDriver driver = new HtmlUnitDriver();

        //Open gmail
        driver.get("http://www.apishops.com/login.jsp");

        // Enter userd id
        WebElement login = driver.findElement(By.name("login"));
        login.sendKeys("user50");

        //wait 3 secs for  userid to be entered
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Enter Password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("neuser50");

        //wait 3 secs for  userid to be entered
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement button = driver.findElements(By.tagName("input")).get(4);

        //Submit button
        button.submit();

        CookieStore cookieStore = seleniumCookiesToCookieStore(driver);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);

        String downloadUrl = "http://www.apishops.com/websiteAction?action=getPriceTiu&id=462382&addPictures=2";

        HttpGet httpGet = new HttpGet(downloadUrl);
        System.out.println("Downloding file form: " + downloadUrl);
        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            File outputFile = new File("shop.yml");
            InputStream inputStream = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            fileOutputStream.close();
            System.out.println("Downloded " + outputFile.length() + " bytes. " + entity.getContentType());
        }
        else {
            System.out.println("Download failed!");
        }
    }

    private CookieStore seleniumCookiesToCookieStore(WebDriver driver) {

        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();

        for(Cookie seleniumCookie : seleniumCookies){
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }

        return cookieStore;
    }

}
