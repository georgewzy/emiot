package com.em.model.em;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="em_record_current")
public class EmRecordCurrent extends BaseModel<EmRecordCurrent> {
    
    public static final EmRecordCurrent dao = new EmRecordCurrent();
    
    public EmRecordCurrent getBy(Object eminfoid) {
        StringBuffer sql = new StringBuffer()
            .append("SELECT c.*, ")
            .append("ws.name AS workstate, os.name AS onlinestate, p.name AS position ")
            .append("FROM em_record_current c ")
            .append("LEFT JOIN em_work_state ws ON c.workstateid = ws.id ")
            .append("LEFT JOIN em_online_state os ON c.onlinestateid = os.id ")
            .append("LEFT JOIN em_position p ON c.positionid = p.id ")
            .append("WHERE c.eminfoid = ? ");
        return findFirst(sql.toString(), eminfoid);
    }
    
    // count 正常的设备
    public int countOK() {
        String sql = "SELECT COUNT(*) FROM em_record_current WHERE emstate = 0";
        return Db.queryInt(sql);
    }

}
