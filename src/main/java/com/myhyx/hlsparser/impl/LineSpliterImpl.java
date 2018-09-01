package com.myhyx.hlsparser.impl;

import com.myhyx.hlsparser.Define.Tag;

/**
 * Created by miguc on 2018/9/1.
 */
public class LineSpliterImpl implements ILineSpliter {
    public Integer lineSplit(String str, Integer status){

        if (str.startsWith(Tag.EXTINF) ||
                str.startsWith(Tag.PROGRAM_DATE_TIME) ||
                str.startsWith(Tag.DISCONTINUITY) ||
                str.startsWith(Tag.KEY)){
            return 1;
        }

        if (str.startsWith(Tag.STREAM_INF) ||
                str.startsWith(Tag.I_FRAME_INF)){
            return 11;
        }

        if (!str.startsWith("#EXT") && status == 1){
            return 2;
        }
        if (!str.startsWith("#EXT") && status == 11){
            return 12;
        }

        if (str.startsWith(Tag.EXTM3U) ||
                str.startsWith(Tag.VERSION) ||
                str.startsWith(Tag.ALLOW_CACHE) ||
                str.startsWith(Tag.MEDIA_SQEUENCE) ||
                str.startsWith(Tag.TARGETDURATION)){
            return 0;
        }

        return -1;
    }
}
