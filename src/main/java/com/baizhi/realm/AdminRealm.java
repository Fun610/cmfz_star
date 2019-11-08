package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private AdminDao adminDao;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        Admin admin = new Admin();
        admin.setUsername(token.getUsername());
        Admin login = adminDao.selectOne(admin);
        if (login==null){
            return null;
        }else {
            SimpleAccount account = new SimpleAccount(login.getUsername(), login.getPassword(), this.getName());
            return account;
        }
    }
}
