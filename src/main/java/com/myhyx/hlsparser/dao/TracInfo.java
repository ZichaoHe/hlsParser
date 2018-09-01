package com.myhyx.hlsparser.dao;

import com.myhyx.hlsparser.Define.Tag;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by miguc on 2018/9/1.
 */
@Data
public class TracInfo {

    private String discontinue;
    private String key;
    private String programDate;
    private String duration;
    private String uri;

    public boolean isDiscontinue(){
        if (discontinue == null){
            return false;
        }
        return true;
    }

    public Long parseProgramDate(){
        if (programDate == null){
            return 0L;
        }
        String datestr = programDate.replace(Tag.PROGRAM_DATE_TIME+Tag.COLON, "");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss+Z");
        try {
            Date date = sdf.parse(datestr);
            return date.getTime();
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }
}
