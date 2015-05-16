/*
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

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
 * This is a cheated version
 *//*

public class gomoku2 {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.

        int SAFE_ZONE = 200;

        int[][] a = new int[26][22];


        FirefoxProfile profile = new FirefoxProfile();

        WebDriver driver = new FirefoxDriver(profile);

        driver.get("http://caro.haivl.com/play");

        Scanner scanner = new Scanner(System.in);
        Integer flag = scanner.nextInt();
        WebElement canvas;
        Helper helper = new Helper();
        int numofrefreshtime = 0;

        while (true) {
            // Start the game
            helper.sendCommand("RECTSTART 24,20");
            helper.getAnswer();
            helper.sendCommand("INFO timeout_turn 15000");
            helper.sendCommand("INFO timeout_match 0");
            a = new int[26][22];
            boolean reload = false;
            numofrefreshtime++;

            // waiting for other to join
            boolean waitForJoin = true;
            while (waitForJoin) {
                try {
                    Thread.sleep(200);
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
                List<WebElement> statuses = driver.findElements(By.className("status"));
                String time1 = statuses.get(0).getText().trim();
                if (!time1.equals("")) break;
                List<WebElement> scores = driver.findElements(By.className("score"));
                String score2 = scores.get(1).getText().trim().split(" ")[0];
                Integer intscore;
                try {
                    intscore = Integer.parseInt(score2);
                    System.out.println("score of opponent:" + intscore);
                    if (intscore < SAFE_ZONE) {
                        System.out.println("Score is under safe zone:" + intscore);
                        break;
                    }
                } catch (Exception e) {
                    intscore = 0;
                }

                String time2 = statuses.get(1).getText().trim();
                if (!time2.equals("")) {
                    WebElement leaveButton = driver.findElement(By.id("leave"));
                    Actions action = new Actions(driver);
                    action.moveToElement(leaveButton);
                    action.clickAndHold();
                    action.release();
                    action.perform();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    try {
                        WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                        action = new Actions(driver);
                        action.moveToElement(playButton);
                        action.clickAndHold();
                        action.release();
                        action.perform();
                    } catch (Exception e) {
                        System.out.println("Sleep for 25 secs");
                        try {
                            Thread.sleep(25000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("Unable to go first, refresh all page");
                        driver.get("http://caro.haivl.com/play");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                        action = new Actions(driver);
                        action.moveToElement(playButton);
                        action.clickAndHold();
                        action.release();
                        action.perform();
                    }

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    reload = true;
                    break;
                }
            }

            if (reload) continue;

            System.out.println("Number of refresh time before being successful: " + numofrefreshtime);
            numofrefreshtime = 0;

            boolean flagfirstmove = false;


            canvas = driver.findElement(By.className("kineticjs-content"));
            // if noone went any move, check who has the first move
            if (!flagfirstmove) {
                System.out.println("No one went any move. Now check who is the first to play");
                WebElement time = driver.findElement(By.className("status")).findElement(By.className("time"));
                String ttime = time.getText().trim();

                if (!ttime.equals("")) {
                    System.out.println("Check if the opponent already made one move");
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
                }
                if (!ttime.equals("") && !flagfirstmove) {
                    System.out.println("We are the first to play");
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
                    String js = "" +
                            " content = document.getElementsByClassName('kineticjs-content')[0]; " +
                            " canvas  = content.getElementsByTagName('canvas')[1]; " +
                            " context = canvas.getContext('2d'); " +
                            " c = context.getImageData(" + (int) Math.round((x + 0.5) * 20.04) + "," + (int) Math.round((y + 0.5) * 20.047) + ", 1, 1).data;" +
                            " return c[0] + ' ' + c[1] + ' '+ c[2];";
                    String jsanswer = "";
                    if (driver instanceof JavascriptExecutor) {
                        jsanswer = (String) ((JavascriptExecutor) driver).executeScript(js);
                    }
                    String[] c = jsanswer.split(" ");
                    if (c[0].equals("0") && (c[1].equals("0")) && (c[2].equals("0"))) {
                        System.out.println("Player left");
                        reload = true;
                    }
                } else {
                    */
/*System.out.println("The opponent is the first to play");
                    System.out.println("Sleep for 60 sec");
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    driver.get("http://caro.haivl.com/play");

                    WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                    Actions action = new Actions(driver);
                    action.moveToElement(playButton);
                    action.clickAndHold();
                    action.release();
                    action.perform();
                    reload = true;*//*

                }
            }

            if (reload) continue;

            // loop through steps
            boolean flagloop = true;

            while (flagloop) {
                int count = 0;
                // check if other already finish the move
                boolean waitingFlag = true;
                while (waitingFlag) {

                    //check if already win (opponent lost without making any move)
                    WebElement result = driver.findElement(By.id("result"));
                    String res = result.getText().trim();
                    if (!res.equals("")) {
                        System.out.println("res = " + res);
                        System.out.println("Opponent lost due to inactivity");
                        driver.get("http://caro.haivl.com/play");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                        Actions action = new Actions(driver);
                        action.moveToElement(playButton);
                        action.clickAndHold();
                        action.release();
                        action.perform();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        reload = true;
                        break;
                    }

                    WebElement time = driver.findElement(By.className("status")).findElement(By.className("time"));
                    String ttime = time.getText().trim();
                    if (!ttime.equals("")) break;
                    try {
                        Thread.sleep(2000);
                        count++;
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    if (count > 31) {
                        System.out.println("Wait so long, reload the page");
                        driver.get("http://caro.haivl.com/play");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                        Actions action = new Actions(driver);
                        action.moveToElement(playButton);
                        action.clickAndHold();
                        action.release();
                        action.perform();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        reload = true;
                        break;
                    }
                }
                if (reload) break;

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
                    driver.get("http://caro.haivl.com/play");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    WebElement playButton = driver.findElement(By.className("join")).findElement(By.tagName("button"));
                    Actions action = new Actions(driver);
                    action.moveToElement(playButton);
                    action.clickAndHold();
                    action.release();
                    action.perform();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                }
            }
            if (reload) continue;
        }
    }
}
*/
