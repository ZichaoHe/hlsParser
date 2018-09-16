package com.myhyx.hlsparser.impl;

import com.myhyx.hlsparser.ITsfileParser;
import com.myhyx.hlsparser.dao.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by miguc on 2018/9/2.
 */
public class DefaultTsfileParser implements ITsfileParser {
    private static final Integer PKGSIZE = 188;
    private Integer pkgNum = 0;
    private Tsfile tsfile = new Tsfile();

    public Tsfile parser(InputStream input) throws Exception{

        while (true){

            byte[] bs = new byte[PKGSIZE];
            int n = input.read(bs, 0, PKGSIZE);
            if (n < 0){
                break;
            }

            TsPkgHeader head = parserTspkt(bs);
            //PAT
            if (head.getPid() == 0){
                continue;
            }

            //CA
            if (head.getPid() == 1){
                continue;
            }

            //传输流描述表
            if (head.getPid() == 2){
               continue;
            }

            //IPMP
            if (head.getPid() == 3){
                continue;
            }

            if (tsfile.getPmts() == null || tsfile.getPats() == null){
                continue;
            }
            Integer pmtId = tsfile.getPats().get(0).getPrograms().get(0).getPid();
            List<Integer> avIds = new ArrayList<Integer>();
            for (PMTinfo.PMTStream stream : tsfile.getPmts().get(0).getPmtStreams()){
                avIds.add(stream.getPid());
            }

            //PMT
            if (head.getPid() == pmtId){
                continue;
            }

            //video or audio
            if (avIds.contains(head.getPid())){

            }

            input.skip(PKGSIZE);
        }

        return  tsfile;
    }

    private TsPkgHeader parserTspkt(byte[] bs){
        TsPkgHeader header = new TsPkgHeader();

        header.setSync(bs[0]&0xFF);
        header.setTransportErrorIndicator((bs[1] >> 7) & 0x01);
        header.setPayloadUnitStartIndicator((bs[1] >> 6) & 0x01);
        header.setTransportPriority((bs[1] >> 5) & 0x01);
        header.setPid((bs[1]&0x1F)<<8 | bs[2]);
        header.setTransportScramblingControl((bs[3] >> 6) & 0x03);
        header.setAdaptationFieldControl((bs[3] >> 4) & 0x03);
        header.setContinueCounter(bs[3] & 0x0F);
        if (header.getAdaptationFieldControl() == 0x02 ||
                header.getAdaptationFieldControl() == 0x03){
            header.setAdaptationFieldLen(bs[4] & 0xFF);
        }

        byte[] newbs = new byte[4 + 1 + header.getAdaptationFieldLen()];
        System.arraycopy(bs, 0, newbs, 0, 4 + 1 + header.getAdaptationFieldLen());
        header.setBs(newbs);

        return header;
    }

    private PATinfo parserPATinfo(byte[] bs){
        PATinfo pat = new PATinfo();
        byte[] newbs = new byte[bs.length];
        System.arraycopy(bs, 0, newbs, 0, bs.length);
        pat.setBs(newbs);

        pat.setTableId((bs[0]&0xFF));
        pat.setSectionSyntaxIndicator((bs[1] >> 7) & 0x01);
        pat.setZero((bs[1] >> 6) & 0x01);
        pat.setReserved1((bs[1] >> 4) & 0x03);
        pat.setSectionLen((bs[1] & 0x0F) << 8 | bs[2]&0xFF);
        pat.setTransportStreamId(bs[3]&0xFF << 8 | bs[4]&0xFF);
        pat.setReserved2(bs[5] >> 6 & 0x03);
        pat.setVersionNum(bs[5] >> 1 & 0x1F);
        pat.setCurrentNextIndicator(bs[5] & 0x01);
        pat.setSectionNum(bs[6] & 0xFF);
        pat.setLastSectionNum(bs[7] & 0xFF);

        for (int i=0; i<pat.getSectionLen()-9; i=i+4){
            PATinfo.PATProgram program = pat.new PATProgram();
            program.setPnum(bs[8+i]&0xFF << 8 | bs[9+i]&0xFF);
            program.setPid((bs[10+i] & 0x1F) << 8 | bs[11+i]&0xFF);
            pat.getPrograms().add(program);
        }

        return pat;
    }

    private PMTinfo parserPMTinfo(byte[] bs){
        PMTinfo pmt = new PMTinfo();
        byte[] newbs = new byte[bs.length];
        System.arraycopy(bs, 0, newbs, 0, bs.length);
        pmt.setBs(newbs);

        pmt.setTableId(bs[0]&0xFF);
        pmt.setSectionSyntaxIndicator((bs[1] >> 7) & 0x01);
        pmt.setZero((bs[1] >> 6) & 0x01);
        pmt.setReserved1((bs[1] >> 4) & 0x03);
        pmt.setSectionLen((bs[1] & 0x0F) << 8 | bs[2]&0xFF);
        pmt.setProgramNum(bs[3]&0xFF << 8 | bs[4]&0xFF);
        pmt.setReserved2(bs[5] >> 6 & 0x03);
        pmt.setVersionNum(bs[5] >> 1 & 0x1F);
        pmt.setCurrentNextIndicator(bs[5] & 0x01);
        pmt.setSectionNum(bs[6] & 0xFF);
        pmt.setLastSectionNum(bs[7] & 0xFF);
        pmt.setReserved3((bs[8]>>5) & 0x07);
        pmt.setPcrPid((bs[8]&0x1F)<<8 | bs[9]&0xFF);
        pmt.setReserved4((bs[10]>>4) & 0x0F);
        pmt.setProgramInfoLen((bs[10]&0x0F) << 8 | bs[11]&0xFF);
        Integer skip = pmt.getProgramInfoLen();

        for (int i = 0; i < pmt.getSectionLen()-16-skip; ){
            PMTinfo.PMTStream stream = pmt.new PMTStream();
            stream.setStreamType(bs[12+skip+i] & 0xFF);
            stream.setPid((bs[13+skip+i]&0x1F)<<8 | bs[14+skip+i]&0xFF);
            stream.setEsInfoLen((bs[15+skip+i]&0x0F)<<8 | bs[16+skip+i]&0xFF);
            pmt.getPmtStreams().add(stream);

            i+=5+stream.getEsInfoLen();
        }

        return pmt;
    }

    private PESinfo parserPESInfo(byte[] bs){
        PESinfo pes = new PESinfo();
        byte[] newbs = new byte[bs.length];
        System.arraycopy(bs, 0, newbs, 0, bs.length);
        pes.setBs(newbs);

        pes.setPktStartCodePrefix(bs[0]&0xFF<<16 | bs[1]&0xFF<<8 | bs[2]&0xFF);
        pes.setStreamId(bs[3]&0xFF);
        if ((pes.getStreamId() & 0x0F0) != 0x0E0 || (pes.getStreamId() & 0x0E0) != 0x0C0){
            // if NOT
            // ISO/IEC 13818-3 或 ISO/IEC 11172-3 或 ISO/IEC 13818-7 或 ISO/IEC 14496-3 音
            // 频流编号
            // OR
            // ITU-T H.262 建议书 | ISO/IEC 13818-2, ISO/IEC 11172-2, ISO/IEC 14496-2
            // 或 ITU-T H.264 建议书 | ISO/IEC 14496-10 视频流编号
            return null;
        }
        Integer ptsDtsFlags = (bs[7] >> 6) &0x03;
        if (ptsDtsFlags == 0x02){
            Long pts = (((bs[9]>>1)&0x07L)<<30) |
                    ((bs[10]&0xFFL)<<22) |
                    (((bs[11]>>1)&0x7FL)<<15) |
                    ((bs[12]&0xFFL)<<7) |
                    ((bs[13]>>1)&0x7FL);
            pes.setPts(pts);
        }else if(ptsDtsFlags == 0x03){
            Long pts = (((bs[9]>>1)&0x07L)<<30) |
                    ((bs[10]&0xFFL)<<22) |
                    (((bs[11]>>1)&0x7FL)<<15) |
                    ((bs[12]&0xFFL)<<7) |
                    ((bs[13]>>1)&0x7FL);
            pes.setPts(pts);
            Long dts = (((bs[14]>>1)&0x07L)<<30) |
                    ((bs[15]&0xFFL)<<22) |
                    (((bs[16]>>1)&0x7FL)<<15) |
                    ((bs[17]&0xFFL)<<7) |
                    ((bs[18]>>1)&0x7FL);
            pes.setPts(dts);
        }else if (ptsDtsFlags == 0x01){

        }else {

        }

        return pes;
    }


    private VideoInfo parserVideoInfo(byte[] bs){
        return null;
    }

    private AudioInfo parserAudioInfo(byte[] bs){
        return null;
    }
}
