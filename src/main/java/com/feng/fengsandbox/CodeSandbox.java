package com.feng.fengsandbox;


import com.feng.fengsandbox.model.ExecuteCodeRequest;
import com.feng.fengsandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
