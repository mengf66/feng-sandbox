package com.feng.fengsandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.feng.fengsandbox.model.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;
import sun.awt.image.InputStreamImageSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 进程工具类
 */
public class ProcessUtils {

    /**
     * 执行进程并获取
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runProcess(Process runProcess, String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            if(exitValue == 0) {
                System.out.println(opName + "成功");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
            } else {
                System.out.println(opName + "失败，错误码：" + exitValue);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> errorOutputStrList = new ArrayList<>();
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = bufferedReader.readLine()) != null) {
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String opName, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
//            int exitValue = runProcess.waitFor();
//            executeMessage.setExitValue(exitValue);
//            if(exitValue == 0) {
//                System.out.println(opName + "成功");
//            } else {
//                System.out.println(opName + "失败，错误码：" + exitValue);
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
//                List<String> outputStrList = new ArrayList<>();
//                String compileOutputLine;
//                while ((compileOutputLine = bufferedReader.readLine()) != null) {
//                    outputStrList.add(compileOutputLine);
//                }
//                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
//                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
//                List<String> errorOutputStrList = new ArrayList<>();
//                String errorCompileOutputLine;
//                while ((errorCompileOutputLine = bufferedReader.readLine()) != null) {
//                    errorOutputStrList.add(errorCompileOutputLine);
//                }
//                executeMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
//                return executeMessage;
//            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s = args.split(" ");
            outputStreamWriter.write(StrUtil.join("\n", s) + "\n");
            outputStreamWriter.flush();
            InputStream inputStream = runProcess.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            outputStream.close();
            outputStreamWriter.close();
            inputStream.close();
            runProcess.destroy();
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}
