package com.feng.fengsandbox.controller;

import com.feng.fengsandbox.JavaNativeCodeSandbox;
import com.feng.fengsandbox.model.ExecuteCodeRequest;
import com.feng.fengsandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/haha")
public class MainController {

    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;

    @PostMapping("/executeCode")
    public ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                                           HttpServletResponse response) {
        String authHeader = request.getHeader(AUTH_REQUEST_SECRET);
        if(!AUTH_REQUEST_HEADER.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }
        if(executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }
}
