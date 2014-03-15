import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class main {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new FirefoxDriver();
        //int t = 100;

        driver.get("http://kissanime.com/Anime/Samurai-Champloo-Sub");
        List<WebElement> elements = driver.findElement(By.className("listing")).findElements(By.tagName("tr"));

        int index = 0;
        List<String> links = new ArrayList<String>();

        for (WebElement element : elements) {
            if (index >= 2) {
                WebElement link = element.findElement(By.tagName("td")).findElement(By.tagName("a"));
                String hrefLink = link.getAttribute("href");
                links.add(hrefLink);
            }
            index++;
        }

        System.out.println(links.size());

        int size = links.size();

        for (int ind = size-1; ind >=0; ind --) {
            String link = new String(links.get(ind));
            boolean flag = true;
            while (flag) {
                try {
                    flag = false;
                    driver.get(link);
                    WebElement element = driver.findElement(By.id("divDownload")).findElement(By.tagName("a"));
                    String t = element.getAttribute("href");
                    System.out.println(t);
                } catch (Exception error) {
                    flag = true;
                }
            }
        }

        //Close the browser
        driver.quit();
    }
}