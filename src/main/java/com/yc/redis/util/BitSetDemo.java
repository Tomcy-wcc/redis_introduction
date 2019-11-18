package com.yc.redis.util;

import java.util.BitSet;

/**
 * @Description BitSet的使用
 * @auther wcc
 * @create 2019-10-01 9:33
 */
public class BitSetDemo {

    /**
     * 求一个字符串包含的char
     * @param str
     */
    public static void containChars(String str){
        BitSet bitSet = new BitSet();
        for(int i = 0; i < str.length(); i++){
            bitSet.set(str.charAt(i));
        }

        StringBuilder builder = new StringBuilder();
        int size = bitSet.size();
        for(int i = 0; i < size; i++){
            if(bitSet.get(i)){
                builder.append((char) i);
            }
        }
        System.out.println(builder);
    }

    /**
     * 使用BitSet求1024以内的素数
     */
    public static void computePrime(){
        BitSet bitSet = new BitSet(1024);
        int size = bitSet.size();
        //设置值
        for(int i = 2; i < size; i++){
            bitSet.set(i);
        }
        //筛选素数
        for (int i = 2; i < Math.sqrt(size); i++){
            if(bitSet.get(i)){
                //筛掉素数的倍数（一个素数的倍数是合数）
                for (int j = 2 * i; j < size; j+=i){
                    bitSet.clear(j);
                }
            }
        }
        System.out.println(bitSet.size());

        int count = 0;
        for (int i = 2; i < size; i++){
            if (bitSet.get(i)){
                System.out.printf("%5d", i);
                if(++count % 15 == 0){
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        //containChars("11255488sdasdas");
        computePrime();
    }
}
