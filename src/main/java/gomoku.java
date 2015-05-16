/*
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.util.List;
import java.util.Scanner;

*/
/**
 * Created with IntelliJ IDEA.
 * User: NguyenTranQuan
 * Date: 3/30/14
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 * The AI will stop when losing the first game to avoid losing a lot of points
 *//*

public class gomoku {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.

        int[][] a = new int[26][22];


        FirefoxProfile profile = new FirefoxProfile();

        WebDriver driver = new FirefoxDriver(profile);

        driver.get("http://caro.haivl.com/play");

        Scanner scanner = new Scanner(System.in);
        Integer flag = scanner.nextInt();
        WebElement canvas = driver.findElement(By.className("kineticjs-content"));
        Helper helper = new Helper();

        while (true) {
            // Start the game
            helper.sendCommand("RECTSTART 24,20");
            helper.getAnswer();
            helper.sendCommand("INFO timeout_turn 15000");
            helper.sendCommand("INFO timeout_match 0");
            a = new int[26][22];

            // waiting for other to join
            boolean waitForJoin = true;
            while (waitForJoin) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                try {
                    WebElement waiting = driver.findElement(By.className("waiting")).findElement(By.className("visible"));
                } catch (Exception e) {
                    waitForJoin = false;
                }
            }

            // waiting for both to be ready
            boolean waitForReady = true;
            while (waitForReady) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                List<WebElement> statuses = driver.findElements(By.className("status"));
                for (WebElement status : statuses) {
                    String ttime = status.findElement(By.className("time")).getText().trim();
                    System.out.println(ttime);
                    if (!ttime.equals("")) {
                        waitForReady = false;
                        break;
                    }
                }
            }

            // Check to see who go first

            // first have to check if the opponent already went a move. This might be an unneccessary step since we have to check again later.
            // Will look at it when have free time.
*/
/*            while(true){
                int i = scanner.nextInt();
                int j = scanner.nextInt();
                String js = "" +
                        " content = document.getElementsByClassName('kineticjs-content')[0]; " +
                        " canvas  = content.getElementsByTagName('canvas')[1]; " +
                        " context = canvas.getContext('2d'); " +
                        " c = context.getImageData(" + (int) Math.round((i + 0.5) * 20.04) + "," + (int) Math.round((j + 0.5) * 20.047) + ", 1, 1).data;" +
                        " return c[0] + ' ' + c[1] + ' '+ c[2];";
                String jsanswer = "";
                if (driver instanceof JavascriptExecutor) {
                    jsanswer = (String) ((JavascriptExecutor) driver).executeScript(js);
                }
                System.out.println(jsanswer);
                if (1 ==2) break;
            }*//*


            boolean flagfirstmove = false;

            for (int i = 1; i < 25; i++) {
                for (int j = 1; j < 21; j++)
                    if (a[i][j] == 0) {
                        String js = "" +
                                " content = document.getElementsByClassName('kineticjs-content')[0]; " +
                                " canvas  = content.getElementsByTagName('canvas')[1]; " +
                                " context = canvas.getContext('2d'); " +
                                " c = context.getImageData(" + (int) Math.round((i + 0.5) * 20.04) + "," + (int) Math.round((j + 0.5) * 20.047) + ", 1, 1).data;" +
                                " return c[0] + ' ' + c[1] + ' '+ c[2];";
                        String jsanswer = "";
                        if (driver instanceof JavascriptExecutor) {
                            jsanswer = (String) ((JavascriptExecutor) driver).executeScript(js);
                        }
                        String[] c = jsanswer.split(" ");
                        if (!c[0].equals("0") || !(c[1].equals("0")) || !(c[2].equals("0"))) {
                            flagfirstmove = true;
                            break;
                        }
                    }
                if (flagfirstmove) break;
            }

            // if noone went any move, check who has the first move
            if (!flagfirstmove) {
                WebElement time = driver.findElement(By.className("status")).findElement(By.className("time"));
                String ttime = time.getText().trim();
                if (!ttime.equals("")) {
                    // make sure that the opponent did not make any move while we check before
                    for (int i = 1; i < 25; i++) {
                        for (int j = 1; j < 21; j++)
                            if (a[i][j] == 0) {
                                String js = "" +
                                        " content = document.getElementsByClassName('kineticjs-content')[0]; " +
                                        " canvas  = content.getElementsByTagName('canvas')[1]; " +
                                        " context = canvas.getContext('2d'); " +
                                        " c = context.getImageData(" + (int) Math.round((i + 0.5) * 20.04) + "," + (int) Math.round((j + 0.5) * 20.047) + ", 1, 1).data;" +
                                        " return c[0] + ' ' + c[1] + ' '+ c[2];";
                                String jsanswer = "";
                                if (driver instanceof JavascriptExecutor) {
                                    jsanswer = (String) ((JavascriptExecutor) driver).executeScript(js);
                                }
                                String[] c = jsanswer.split(" ");
                                if (!c[0].equals("0") || !(c[1].equals("0")) || !(c[2].equals("0"))) {
                                    flagfirstmove = true;
                                    break;
                                }
                            }
                        if (flagfirstmove) break;
                    }
                    if (!flagfirstmove) {
                        helper.sendCommand("BEGIN");
                        String answer = helper.getAnswer();
                        String[] co = answer.split(",");
                        int x = Integer.parseInt(co[0]);
                        int y = Integer.parseInt(co[1]);
                        a[x][y] = 1;
                        Actions action = new Actions(driver);
                        action.moveToElement(canvas, (int) Math.round((x + 0.5) * 20.04), (int) Math.round((y + 0.5) * 20.047));
                        action.clickAndHold();
                        action.release();
                        action.perform();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }

            // loop through steps
            boolean flagloop = true;
            while (flagloop) {
                // check if other already finish the move
                boolean waitingFlag = true;
                while (waitingFlag) {
                    WebElement time = driver.findElement(By.className("status")).findElement(By.className("time"));
                    String ttime = time.getText().trim();
                    if (!ttime.equals("")) break;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    WebElement result = driver.findElement(By.id("result"));
                    String res = result.getText().trim();
                    if (!res.equals("")) {
                        WebElement continueButton = driver.findElement(By.className("commands")).findElement(By.tagName("button"));
                        continueButton.click();
                        flagloop = false;
                        break;
                    }
                }

                // find their move
                int u = 0, v = 0;
                boolean flagcontrol = false;

                for (int i = 1; i < 25; i++) {
                    for (int j = 1; j < 21; j++)
                        if (a[i][j] == 0) {
                            String js = "" +
                                    " content = document.getElementsByClassName('kineticjs-content')[0]; " +
                                    " canvas  = content.getElementsByTagName('canvas')[1]; " +
                                    " context = canvas.getContext('2d'); " +
                                    " c = context.getImageData(" + (int) Math.round((i + 0.5) * 20.04) + "," + (int) Math.round((j + 0.5) * 20.047) + ", 1, 1).data;" +
                                    " return c[0] + ' ' + c[1] + ' '+ c[2];";
                            String jsanswer = "";
                            if (driver instanceof JavascriptExecutor) {
                                jsanswer = (String) ((JavascriptExecutor) driver).executeScript(js);
                            }
                            String[] c = jsanswer.split(" ");
                            if (!c[0].equals("0") || !(c[1].equals("0")) || !(c[2].equals("0"))) {
                                u = i;
                                v = j;
                                a[u][v] = 1;
                                flagcontrol = true;
                                break;
                            }
                        }
                    if (flagcontrol) break;
                }

                // Add in the move
                if ((u != 0) && (v != 0)) {
                    helper.sendCommand("TURN " + u + "," + v);
                    String answer = helper.getAnswer();
                    System.out.println(answer);
                    String[] co = answer.split(",");

                    int x = Integer.parseInt(co[0]);
                    int y = Integer.parseInt(co[1]);
                    a[x][y] = 1;
                    Actions action = new Actions(driver);
                    action.moveToElement(canvas, (int) Math.round((x + 0.5) * 20.04), (int) Math.round((y + 0.5) * 20.047));
                    action.clickAndHold();
                    action.release();
                    action.perform();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }

                //check if already win
                WebElement result = driver.findElement(By.id("result"));
                String res = result.getText().trim();
                if (!res.equals("")) {
                    WebElement continueButton = driver.findElement(By.className("commands")).findElement(By.tagName("button"));
                    continueButton.click();
                    break;
                }
            }
        }
    }
}
*/
