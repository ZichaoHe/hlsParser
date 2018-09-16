package com.myhyx.hlsparser.dao;

import lombok.Data;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class VideoInfo {
    private Integer type; // I,P,B
    private Long dts;
    private Long pts;
    private Long pcr;
    private Long offset;
}
