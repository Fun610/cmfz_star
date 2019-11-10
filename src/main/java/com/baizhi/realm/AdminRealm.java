package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.entity.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin selectOne = adminDao.selectOne(admin);
        List<AdminRole> adminRoles = adminRoleDao.select(new AdminRole(null, selectOne.getId(), null));
        List<String> list = new ArrayList<>();
        for (AdminRole adminRole : adminRoles) {
            Role role = roleDao.selectOne(new Role(adminRole.getRoleId(), null));
            list.add(role.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(list);
        return info;
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
