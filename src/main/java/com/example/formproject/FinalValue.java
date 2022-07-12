package com.example.formproject;

import org.apache.http.HttpStatus;

import java.time.format.DateTimeFormatter;

public class FinalValue {
    public static final String LOGIN_URL = "/member/login";
    public final static String REDIRECT_URL = "http://localhost:3000/code/auth";

    public final static String FRONT_URL = "http://localhost:3000";

    public final static String BACK_URL= "http://idontcare.shop";

    public final static String APPLICATION_TITLE = "농담 : 농사를 한눈에 담다.";

    public final static DateTimeFormatter DAYTIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public final static DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static String HTTPSTATUS_OK="200";
    public final static String HTTPSTATUS_FORBIDDEN="403";
    public final static String HTTPSTATUS_BADREQUEST="400";
    public final static String HTTPSTATUS_NOTFOUNT="404";
    public final static String HTTPSTATUS_SERVERERROR="500";
}
