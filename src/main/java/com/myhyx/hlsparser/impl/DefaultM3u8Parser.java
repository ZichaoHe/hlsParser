package com.myhyx.hlsparser.impl;

import com.myhyx.hlsparser.Define.Tag;
import com.myhyx.hlsparser.IM3u8Parser;
import com.myhyx.hlsparser.dao.Head;
import com.myhyx.hlsparser.dao.PlayList;
import com.myhyx.hlsparser.dao.StreamInfo;
import com.myhyx.hlsparser.dao.TracInfo;
import com.myhyx.hlsparser.util.M3u8Scanner;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by miguc on 2018/9/1.
 */
public class DefaultM3u8Parser implements IM3u8Parser {

    //0-write head
    //1-buffer body(tracinfos)
    //2-flush body
    //11-buffer body(streaminfos)
    //12-flush body
    //-1-error!
    private Integer status = 0;
    private Queue<String> buf = new LinkedList<String>();
    private ILineSpliter lineSpliter = new LineSpliterImpl();

    public PlayList parser(BufferedReader input) throws Exception{
        PlayList playList = new PlayList();

        String str = null;
        while ((str = M3u8Scanner.getNextline(input)) != null){
            status = lineSpliter.lineSplit(str, status);

            switch (status){
                case 0:
                    headSetter(str, playList.getHead());
                    break;

                case 1:
                case 11:
                    buf.add(str);
                    break;

                case 2:
                    TracInfo tracInfo = new TracInfo();
                    Integer tlen = buf.size();
                    for (int i=0; i<tlen; i++){
                        tracSetter(buf.remove(), tracInfo);
                    }
                    tracSetter(str, tracInfo);
                    playList.getTracInfos().add(tracInfo);
                    break;

                case 12:
                    StreamInfo streamInfo = new StreamInfo();
                    Integer slen = buf.size();
                    for (int i=0; i<slen; i++){
                        streamSetter(buf.remove(), streamInfo);
                    }
                    streamSetter(str, streamInfo);
                    playList.getStreamInfos().add(streamInfo);
                    break;

                default:
                    break;
            }
        }


        return playList;
    }

    private void headSetter(String str, Head head){
        if (str.startsWith(Tag.EXTM3U)){
            head.setType(str);
            return;
        }
        if (str.startsWith(Tag.VERSION)){
            head.setVersion(str);
            return;
        }
        if (str.startsWith(Tag.ALLOW_CACHE)){
            head.setAllowCache(str);
            return;
        }
        if (str.startsWith(Tag.MEDIA_SQEUENCE)){
            head.setMediaSequence(str);
            return;
        }
        if (str.startsWith(Tag.TARGETDURATION)){
            head.setTargetDuration(str);
        }
    }

    private void tracSetter(String str, TracInfo trac){
        if (str.startsWith(Tag.EXTINF)){
            trac.setDuration(str);
            return;
        }
        if (str.startsWith(Tag.PROGRAM_DATE_TIME)){
            trac.setProgramDate(str);
            return;
        }
        if (str.startsWith(Tag.KEY)){
            trac.setKey(str);
            return;
        }
        if (str.startsWith(Tag.DISCONTINUITY)){
            trac.setDiscontinue(str);
            return;
        }
        trac.setUri(str);
    }

    private void streamSetter(String str, StreamInfo stream){
        if (str.startsWith(Tag.STREAM_INF)){
            stream.setStreamInfo(str);
            return;
        }
        if (str.startsWith(Tag.I_FRAME_INF)){
            stream.setIFrameInfo(str);
            return;
        }
        stream.setUri(str);
    }
}
