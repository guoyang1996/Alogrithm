package com.guoyang;

import java.util.Random;

/**
 * @program: Alogrithm
 * @description: 测试数组生成器
 * @author: guoyang
 * @create: 2019-04-23 20:05
 **/
public class TestArrayGenerator {
    /**
     * @param n 数组大小
     * @param ratio 1的比例
     * @return 生成的数组
     */
    public static int[] generateArray(int n,int ratio){
        int[] res = new int[n];
        Random r = new Random(System.currentTimeMillis());//用于生成数组中的数
        Random ran = new Random(System.currentTimeMillis());//用于决定是否为1
        for(int i=0;i<n;i++){
            if(ran.nextInt(100)<ratio) res[i] = 1;
            else res[i] = r.nextInt();
        }
        return res;
    }
}
