package com.mylife.test;

import com.mylife.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static String toString(LocalDateTime ldt){
        return ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt1 = ldt.plusDays(2);
        System.out.println(DateTimeUtil.getDiffDays(ldt1,ldt));


    }
}
