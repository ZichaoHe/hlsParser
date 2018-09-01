package com.myhyx.hlsparser.util;

import java.io.BufferedReader;

/**
 * Created by miguc on 2018/9/1.
 */
public class M3u8Scanner {
    public static String getNextline(BufferedReader br) throws Exception{
        String result = "";

        while (true){
            String line = br.readLine();
            if (line == null){
                return null;
            }

            result += line;

            if (line.endsWith("\\")){
                result = result.substring(0, result.length()-1);
                continue;
            }

            break;
        }

        return result;
    }
}
