package com.myhyx.hlsparser.dao;

import lombok.Data;

/**
 * Created by miguc on 2018/9/2.
 */
@Data
public class AudioInfo {
//    110x xxxx ISO/IEC 13818-3 或 ISO/IEC 11172-3 或 ISO/IEC 13818-7 或 ISO/IEC 14496-3 音
//    频流编号 x xxxx
//    1110 xxxx ITU-T H.262 建议书 | ISO/IEC 13818-2, ISO/IEC 11172-2, ISO/IEC 14496-2
//    或 ITU-T H.264 建议书 | ISO/IEC 14496-10 视频流编号 xxxx
    private Integer type;
    private Long dts;
    private Long pts;
    private Long offset;
}
