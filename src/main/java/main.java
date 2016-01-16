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

        int LINK_LIMITATION = 1000;
        int LINK_START = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter link: ");
        String access_link = scanner.next().trim();
        scanner.nextLine();
        System.out.println("Enter limit: ");
        String link_limitation = scanner.nextLine().trim();
        System.out.println("Enter start: ");
        String link_start = scanner.nextLine().trim();
        if (!link_limitation.equals("")) LINK_LIMITATION = Integer.valueOf(link_limitation);
        if (!link_start.equals("")) LINK_START = Integer.valueOf(link_start);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("permissions.default.stylesheet", 2);
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", "false");
        //profile.setPreference("toolkit.telemetry.prompted", 2);
        profile.setPreference("toolkit.telemetry.rejected", true);
        profile.setPreference("toolkit.telemetry.enabled", false);
        profile.setPreference("datareporting.healthreport.service.enabled", false);
        profile.setPreference("datareporting.healthreport.uploadEnabled", false);
        profile.setPreference("datareporting.healthreport.service.firstRun", false);
        profile.setPreference("datareporting.healthreport.logging.consoleEnabled", false);
        profile.setPreference("datareporting.policy.dataSubmissionEnabled", false);
        profile.setPreference("datareporting.policy.dataSubmissionPolicyResponseType", "accepted-info-bar-dismissed");
        profile.setPreference("datareporting.policy.dataSubmissionPolicyAccepted", false);

        WebDriver driver = new FirefoxDriver(profile);

        login(driver);

        driver.get(access_link);

        //if (!isLogined(driver))
        //{
        //login(driver);
            driver.get(access_link);
        //}

        boolean accessFlag = true;

        List<WebElement> elements = new ArrayList<WebElement>();
        do {
            sleep(5000);
            try {
                elements = driver.findElement(By.className("listing")).findElements(By.tagName("tr"));
                accessFlag = false;
            } catch (Exception ex) {
                accessFlag = true;
            }
        }
        while (accessFlag);


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

        for (int ind = size - 1- LINK_START; ind >= Math.max(0, size - LINK_LIMITATION); ind--) {
            String link = new String(links.get(ind));
            boolean flag = true;
            while (flag) {
                try {
                    flag = false;
                    driver.get(link);
                    WebElement element = driver.findElement(By.id("divDownload")).findElement(By.tagName("a"));
                    String t = element.getAttribute("href");
                    sleep(1000);
                    System.out.println(t);
                    allLinks = allLinks + " " + t;
                } catch (Exception error) {
                    flag = true;
                }
            }
        }

        // Copy the links to clipboard
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(allLinks);
        clipboard.setContents(strSel, null);

        //Close the browser
        driver.quit();
    }

    public static void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (Exception ex) {

        }
    }

    public static void login(WebDriver driver){
        String loginLink = "http://kissanime.com/Login";
        String username = "mrdl2010";
        String password = "111111";

        boolean loginflag = true;

        driver.get(loginLink);
        do {
            sleep(5000);
            try {
                WebElement userInput = driver.findElement(By.id("username"));
                userInput.sendKeys(username);
                WebElement passwordInput = driver.findElement(By.id("password"));
                passwordInput.sendKeys(password);
                WebElement submitButton = driver.findElement(By.id("btnSubmit"));
                submitButton.click();
                sleep(2000);
                loginflag = false;
            } catch (Exception ex) {
                loginflag = true;
            }
        }
        while (loginflag);
    }

    public static boolean isLogined(WebDriver driver){
        try {
            WebElement e = driver.findElement(By.tagName("html"));
            if (e.getText().toLowerCase().contains("mrdl2010")) return true;
        }
        catch (Exception ex){
            return false;
        }
        return false;
    }
}