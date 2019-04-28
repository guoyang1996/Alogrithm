package com.guoyang;

import java.util.Arrays;
import java.util.Random;

/**
 * @program: Alogrithm
 * @description: 快排实验
 * @author: guoyang
 * @create: 2019-04-23 18:44
 **/
public class QuickSortExper {
    boolean isSwap = false;

    public void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    public void quickSort(int[] a, int p, int r) {
        if (p < r) {
            isSwap = false;
            int q = randPartition(a, p, r);
            if (!isSwap) {
                return;
            }
            quickSort(a, p, q - 1);
            quickSort(a, q + 1, r);
        }
    }

    private int randPartition(int[] a, int p, int r) {
        int i = new Random(System.currentTimeMillis()).nextInt(r - p) + p;//产生一个p到r的随机数
        swap(a, r, i);
        int x = a[r];
        i = p - 1;
        for (int j = p; j < r; j++) {
            if (a[j] < x) {
                i++;
                isSwap = true;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    private void swap(int[] a, int r, int i) {

        int temp = a[r];
        a[r] = a[i];
        a[i] = temp;

    }

    public static void main(String[] args) {
        QuickSortExper q = new QuickSortExper();

//        int[] a = new int[]{3, 5, 6, 1, 8, 9};
//        System.out.println("排序前：");
//        System.out.println(Arrays.toString(a));
//        long startTime = System.currentTimeMillis();
//        q.quickSort(a);
//        long endTime = System.currentTimeMillis();
//        System.out.println("排序后：");
//        System.out.println(Arrays.toString(a));
//        System.out.println("总耗时：" + (endTime - startTime) + "ms");

        q.test1();

    }

    private void test2(){

        long[] res = new long[7];
        for (int i = 0; i < 7; i++) {
            res[i]=printRes((int)Math.pow(10,i+2),0);
        }
        System.out.println(Arrays.toString(res));
    }
    private void test1() {
        //printRes(1000000, 0);
        printRes(1000000, 100);
//        printRes(10000, 50);
//        printRes(10000, 60);
//        printRes(10000, 70);
//        printRes(10000, 80);
//        printRes(10000, 90);
//        printRes(10000, 100);
//        printRes(1000, 0);
//        printRes(5000, 0);
    }

    private long printRes(int n, int ratio) {
        long startTime = System.currentTimeMillis();
        quickSort(TestArrayGenerator.generateArray(n, ratio));
        long endTime = System.currentTimeMillis();
        System.out.println("输入规模" + n + ",1的比例为" + ratio + "%，总耗时：" + (endTime - startTime) + "ms");
        return endTime - startTime;
    }

}
