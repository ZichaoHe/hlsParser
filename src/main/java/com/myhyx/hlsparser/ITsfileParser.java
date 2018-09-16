package com.myhyx.hlsparser;

import com.myhyx.hlsparser.dao.Tsfile;

import java.io.InputStream;

/**
 * Created by miguc on 2018/9/1.
 */
public interface ITsfileParser {
    Tsfile parser(InputStream input) throws Exception;
}
