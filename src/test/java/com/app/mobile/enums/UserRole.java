package com.app.mobile.enums;

import java.util.HashMap;

public enum UserRole {
    STANDARD_USER {
        public HashMap<String, String> getCredential() {
            HashMap<String, String> credentials = new HashMap<>();
            credentials.put("username", "standard_user");
            credentials.put("password", "secret_sauce");

            return credentials;
        }
    },
    LOCKED_OUT_USER {
        public HashMap<String, String> getCredential() {
            HashMap<String, String> credentials = new HashMap<>();
            credentials.put("username", "locked_out_user");
            credentials.put("password", "secret_sauce");

            return credentials;
        }
    },
    PROBLEM_USER {
        public HashMap<String, String> getCredential() {
            HashMap<String, String> credentials = new HashMap<>();
            credentials.put("username", "problem_user");
            credentials.put("password", "secret_sauce");

            return credentials;
        }
    };
    
    public abstract HashMap<String, String> getCredential();
}
