import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.

        //int t = 100;

        System.out.println("Enter link: ");
        Scanner scanner = new Scanner(System.in);
        String access_link = scanner.next().trim();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("permissions.default.stylesheet", 2);
        profile.setPreference("permissions.default.image", 2);

        WebDriver driver = new FirefoxDriver(profile);

        driver.get(access_link);
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
        String allLinks = new String();

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
                    allLinks = allLinks + " " + t;
                } catch (Exception error) {
                    flag = true;
                }
            }
        }

        // Copy the link to clipboard
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(allLinks);
        clipboard.setContents(strSel, null);

        //Close the browser
        driver.quit();
    }
}