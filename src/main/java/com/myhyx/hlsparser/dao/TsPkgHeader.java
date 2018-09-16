package com.myhyx.hlsparser.dao;

import lombok.Data;

/**
 * Created by miguc on 2018/9/16.
 */
@Data
public class TsPkgHeader {
    private byte[] bs;

    private Integer sync;
    private Integer transportErrorIndicator;
    private Integer payloadUnitStartIndicator;
    private Integer transportPriority;
    private Integer pid;
    private Integer transportScramblingControl;
    private Integer adaptationFieldControl;
    private Integer continueCounter;

    private Integer adaptationFieldLen;
    //TODO
}
