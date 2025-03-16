package com.feng.fengsandbox.security;

import java.security.Permission;

public class DefaultSecurityManager extends SecurityManager {

    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限限制" + perm);
    }
}