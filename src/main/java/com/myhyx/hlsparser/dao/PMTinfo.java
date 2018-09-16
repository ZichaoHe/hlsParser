package com.myhyx.hlsparser.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class PMTinfo {
    private byte[] bs;

    private Integer tableId;
    private Integer sectionSyntaxIndicator;
    private Integer zero;
    private Integer reserved1;
    private Integer sectionLen;
    private Integer programNum;
    private Integer reserved2;
    private Integer versionNum;
    private Integer currentNextIndicator;
    private Integer sectionNum;
    private Integer lastSectionNum;
    private Integer reserved3;
    private Integer pcrPid;
    private Integer reserved4;
    private Integer programInfoLen;
    private List<PMTStream> pmtStreams = new ArrayList<PMTStream>();
    private Integer reserved5;
    private Integer reserved6;
    private Integer crc32;

    @Data
    public class PMTStream{
        //0x0f-aac; 0x1b-264
        private Integer streamType;
        private Integer pid;
        private Integer esInfoLen;
        private Integer descriptor;
    }


}
