package com.myhyx.hlsparser.dao;

import lombok.Data;

/**
 * Created by miguc on 2018/9/8.
 * a simple PES package
 */
@Data
public class PESinfo {
    private byte[] bs;

    private Integer pktStartCodePrefix;
    private Integer streamId;
    private Integer pesPkgLen;
    private Long pts = 0L;
    private Long dts = 0L;
}
