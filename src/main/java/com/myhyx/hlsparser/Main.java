package com.myhyx.hlsparser;

import com.myhyx.hlsparser.dao.*;
import com.myhyx.hlsparser.impl.DefaultM3u8Parser;
import com.myhyx.hlsparser.impl.DefaultTsfileParser;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * Created by miguc on 2018/9/1.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

//        IM3u8Parser m3u8 = new DefaultM3u8Parser();
//
//        try{
//            FileReader rd = new FileReader("D:\\hzc\\vod\\2018\\01.m3u8");
//            BufferedReader br = new BufferedReader(rd);
//            PlayList playList = m3u8.parser(br);
//            br.close();
//            rd.close();
//
//            System.out.println(playList.getHead().toString());
//            for (TracInfo trac : playList.getTracInfos()){
//                System.out.println(trac);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
        try{
            InputStream in = new FileInputStream("C:\\Users\\miguc\\Desktop\\bug\\s\\s\\1-1534244149857.ts");
            ITsfileParser parser = new DefaultTsfileParser();
            Tsfile tsfile = parser.parser(in);
            in.close();

            System.out.println(tsfile.getPats().get(0));
            System.out.println(tsfile.getPmts().get(0));
            System.out.println("vlen="+tsfile.getVideoInfos().size());
            System.out.println(tsfile.getVideoInfos());
            System.out.println("alen="+tsfile.getAudioInfos().size());
            System.out.println(tsfile.getAudioInfos());

//            byte[] bs = new byte[188];
//
//            in.skip(188);
//            in.read(bs,0,188);
//            PATinfo pat = new PATinfo();
//            pat.setTableId((bs[5]&0xFF));
//            pat.setSectionSyntaxIndicator((bs[6] >> 7) & 0x01);
//            pat.setZero((bs[6] >> 6) & 0x01);
//            pat.setReserved1((bs[6] >> 4) & 0x03);
//            pat.setSectionLen((bs[6] & 0x0F) << 8 | bs[7]&0xFF);
//            pat.setTransportStreamId(bs[8]&0xFF << 8 | bs[9]&0xFF);
//            pat.setReserved2(bs[10] >> 6 & 0x03);
//            pat.setVersionNum(bs[10] >> 1 & 0x1F);
//            pat.setCurrentNextIndicator(bs[10] & 0x01);
//            pat.setSectionNum(bs[11] & 0xFF);
//            pat.setLastSectionNum(bs[12] & 0xFF);
//
//            for (int i=0; i<pat.getSectionLen()-9; i=i+4){
//                PATinfo.PATProgram program = pat.new PATProgram();
//                program.setPnum(bs[13+i] << 8 | bs[14+i]&0xFF);
//                program.setPid((bs[15+i] & 0x1F) << 8 | bs[16+i]&0xFF);
//                pat.getPrograms().add(program);
//            }
//
//            System.out.println(pat);
//
//            in.read(bs,0,188);
//            System.out.println(DatatypeConverter.printHexBinary(bs));
//            PMTinfo pmt = new PMTinfo();
//            pmt.setTableId(bs[5]&0xFF);
//            pmt.setSectionSyntaxIndicator((bs[6] >> 7) & 0x01);
//            pmt.setZero((bs[6] >> 6) & 0x01);
//            pmt.setReserved1((bs[6] >> 4) & 0x03);
//            pmt.setSectionLen((bs[6] & 0x0F) << 8 | bs[7]&0xFF);
//            pmt.setProgramNum(bs[8]&0xFF << 8 | bs[9]&0xFF);
//            pmt.setReserved2(bs[10] >> 6 & 0x03);
//            pmt.setVersionNum(bs[10] >> 1 & 0x1F);
//            pmt.setCurrentNextIndicator(bs[10] & 0x01);
//            pmt.setSectionNum(bs[11] & 0xFF);
//            pmt.setLastSectionNum(bs[12] & 0xFF);
//            pmt.setReserved3((bs[13]>>5) & 0x07);
//            pmt.setPcrPid((bs[13]&0x1F)<<8 | bs[14]&0xFF);
//            pmt.setReserved4((bs[15]>>4) & 0x0F);
//            pmt.setProgramInfoLen((bs[15]&0x0F) << 8 | bs[16]&0xFF);
//            Integer skip = pmt.getProgramInfoLen();
//            for (int i = 0; i < pmt.getSectionLen()-16-skip; ){
//                PMTinfo.PMTStream stream = pmt.new PMTStream();
//                stream.setStreamType(bs[17+skip+i] & 0xFF);
//                stream.setPid((bs[18+skip+i]&0x1F)<<8 | bs[19+skip+i]&0xFF);
//                stream.setEsInfoLen((bs[20+skip+i]&0x0F)<<8 | bs[21+skip+i]&0xFF);
//                i+=5+stream.getEsInfoLen();
//                pmt.getPmtStreams().add(stream);
//            }
//
//            System.out.println(pmt);



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
