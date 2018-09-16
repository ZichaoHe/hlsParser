package com.myhyx.hlsparser.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class PATinfo {
    private byte[] bs;

    private Integer tableId;
    private Integer sectionSyntaxIndicator;
    private Integer zero;
    private Integer reserved1;
    private Integer sectionLen;
    private Integer transportStreamId;
    private Integer reserved2;
    private Integer versionNum;
    private Integer currentNextIndicator;
    private Integer sectionNum;
    private Integer lastSectionNum;
    private List<PATProgram> programs = new ArrayList<PATProgram>();
    private Integer networkPid;
    private Integer crc32;

    @Data
    public class PATProgram{
        private Integer pnum;
        private Integer pid;
    }
}
