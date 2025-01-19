package org.zayarnyuk.football_manager.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPwdCoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("UserPwd: test_user= " + encoder.encode("test_user"));
        System.out.println("ManagerPwd: test_manager= " + encoder.encode("test_manager"));
        System.out.println("AdminPwd: test_admin= " + encoder.encode("test_admin"));
    }
}
