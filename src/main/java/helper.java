/*
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;

*/
/**
 * Created with IntelliJ IDEA.
 * User: NguyenTranQuan
 * Date: 3/30/14
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 *//*

public class Helper {
    OutputStream stdin = null;
    InputStream stderr = null;
    InputStream stdout = null;
    Process process = null;+

    public Helper(){
        try {
            process = Runtime.getRuntime ().exec ("src/main/resources/ai/pbrain-RenjuSolver.exe");
            stdin = process.getOutputStream ();
            stderr = process.getErrorStream ();
            stdout = process.getInputStream ();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendCommand(String command){
        try {
            System.out.println("command " + command);
            stdin.write((command + "\n").getBytes());
            stdin.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAnswer(){
        String answer = "";
        BufferedReader brCleanUp =  new BufferedReader (new InputStreamReader(stdout));

        try {
            answer = brCleanUp.readLine();
            String[] co = answer.split(",");
            String test = co[0].trim();
            if (!test.equals("OK") && !NumberUtils.isNumber(test)){
                answer = brCleanUp.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

}
*/
