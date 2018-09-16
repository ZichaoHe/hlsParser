package com.myhyx.hlsparser.dao;

import lombok.Data;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class AudioInfo {
    private String type; // aac...
    private Long dts;
    private Long pts;
    private Long offset;
}
