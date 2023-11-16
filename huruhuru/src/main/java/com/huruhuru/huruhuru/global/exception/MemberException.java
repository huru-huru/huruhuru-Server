package com.huruhuru.huruhuru.global.exception;

public class MemberException {

    public static class MemberNotFoundException extends RuntimeException {
        public MemberNotFoundException(String message) {
            super(message);
        }
    }
}
