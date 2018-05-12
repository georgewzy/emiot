package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_record_lbs_history")
public class EmRecordLbsDao extends BaseModel<EmRecordLbsDao> {
    
    public static final EmRecordLbsDao dao = new EmRecordLbsDao();
    public List<EmRecordLbsDao> list(String eminfoid) {
        String sql = "SELECT ID,EMINFOID,BASECOUNT,BASE1_RSSI,LNG_LBS,LAT_LBS,COMMTIME,POSTIME FROM EM_RECORD_LBS_HISTORY WHERE EMINFOID = ?";
        List<EmRecordLbsDao> list = find(sql,eminfoid);
        return list;
    }
}
