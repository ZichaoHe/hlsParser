package com.myhyx.hlsparser.dao;

import com.myhyx.hlsparser.Define.Tag;
import lombok.Data;

/**
 * Created by miguc on 2018/9/1.
 */
@Data
public class Head {
    private String type;
    private String version;
    private String mediaSequence;
    private String allowCache;
    private String targetDuration;

    public Head(){
        this.type  = Tag.EXTM3U;
        this.version = Tag.VERSION+Tag.COLON+"3";
        this.targetDuration = Tag.TARGETDURATION+Tag.COLON+"10";
    }

}
