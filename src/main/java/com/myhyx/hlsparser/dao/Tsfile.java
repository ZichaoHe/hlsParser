package com.myhyx.hlsparser.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class Tsfile {
    private String filename;

    private List<PATinfo> pats = new ArrayList<PATinfo>();

    private List<PMTinfo> pmts = new ArrayList<PMTinfo>();

    private List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();

    private List<AudioInfo> audioInfos = new ArrayList<AudioInfo>();
}
