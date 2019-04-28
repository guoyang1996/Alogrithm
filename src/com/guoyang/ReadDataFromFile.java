package com.guoyang;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: exercise
 * @description: 从文件读取固定格式的数据
 * @author: guoyang
 * @create: 2019-03-15 21:19
 **/
public class ReadDataFromFile {
    public static int[][] readIntArrays(String filePath) {
        List<String> strs = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String str = null;
            while ((str = br.readLine()) != null) {
                strs.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] res = new int[strs.size()][];
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            String[] numStrs = str.split("\t");
            res[i] = new int[numStrs.length];
            for (int j =0;j<numStrs.length;j++) {
                res[i][j] = Integer.valueOf(numStrs[j]);
            }
        }

        return res;
    }

}
