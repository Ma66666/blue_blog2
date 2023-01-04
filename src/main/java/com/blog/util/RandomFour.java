package com.blog.util;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomFour {
    public String getFour(){
        Random random = new Random();
        String x = random.nextInt(9999-1000)+1000+"";
        return x;
    }
}
