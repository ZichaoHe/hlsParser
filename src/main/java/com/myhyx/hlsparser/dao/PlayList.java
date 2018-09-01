package com.myhyx.hlsparser.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguc on 2018/9/1.
 */
@Data
public class PlayList {
    private Head head = new Head();

    private List<TracInfo> tracInfos = new ArrayList<TracInfo>();

    private List<StreamInfo> streamInfos = new ArrayList<StreamInfo>();

    private String tail;

    public boolean isMasterPlaylist(){
        return false;
    }
}
