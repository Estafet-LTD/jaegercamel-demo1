package com.estafet.camelboot;

import java.util.Random;

public class MyRandomizer{
    public int nextRandom() {
        Random rnd = new Random();
        return rnd.nextInt(1000);
    }
}
