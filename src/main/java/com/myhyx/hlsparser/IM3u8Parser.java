package com.myhyx.hlsparser;

import com.myhyx.hlsparser.dao.PlayList;

import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Created by miguc on 2018/9/1.
 */
public interface IM3u8Parser {
    PlayList parser(BufferedReader input) throws Exception;
}
