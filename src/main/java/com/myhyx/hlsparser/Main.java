package com.myhyx.hlsparser;

import com.myhyx.hlsparser.dao.PlayList;
import com.myhyx.hlsparser.dao.TracInfo;
import com.myhyx.hlsparser.impl.DefaultM3u8Parser;

import java.io.*;

/**
 * Created by miguc on 2018/9/1.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        IM3u8Parser m3u8 = new DefaultM3u8Parser();

        try{
            FileReader rd = new FileReader("D:\\hzc\\vod\\2018\\01.m3u8");
            BufferedReader br = new BufferedReader(rd);
            PlayList playList = m3u8.parser(br);
            br.close();
            rd.close();

            System.out.println(playList.getHead().toString());
            for (TracInfo trac : playList.getTracInfos()){
                System.out.println(trac);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        Integer a = 10;
        a <<= 2;
        System.out.println("a:"+a);
    }
}
