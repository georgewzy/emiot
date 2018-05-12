package com.em.model.em;

import java.util.ArrayList;
import java.util.List;

import com.em.model.BaseModel;
import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="em_alarm_record")
public class EmAlarmRecord extends BaseModel<EmAlarmRecord> {
    
    public static final EmAlarmRecord dao = new EmAlarmRecord();
    
    public Page<EmAlarmRecord> paging(EmAlarmRecord ar, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder();
        sqlExceptSelect
            .append("FROM em_alarm_record ar ")
            .append("LEFT JOIN em_info em ON ar.eminfoid = em.id ")
            .append("LEFT JOIN em_type type ON em.typeid = type.id ")
            .append("LEFT JOIN em_alarmrecordtype art ON ar.alarmrecordtypeid = art.id ")
            .append("WHERE 1 = 1 ");
        
        //base info
        if (!Util.isEmptyString(ar.get("alarmrecordtypeid"))) {
            paras.add(ar.get("alarmrecordtypeid"));
            sqlExceptSelect.append(" AND ar.alarmrecordtypeid = ? ");
        }
        if (!Util.isEmptyString(ar.get("recordtime"))) {
            paras.add("%"+ ar.get("recordtime") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), ar.recordtime, 21) LIKE ? ");
        }
        //
        if (!Util.isEmptyString(ar.get("emtypeid"))) {
            paras.add(ar.get("emtypeid"));
            sqlExceptSelect.append(" AND em.typeid = ? ");
        }
        if (!Util.isEmptyString(ar.get("emcode"))) {
            paras.add("%"+ ar.get("emcode") +"%");
            sqlExceptSelect.append(" AND em.code LIKE ? ");
        }
        
        // areaids
        if (!Util.isEmptyString(ar.get("areaids"))) {
            String[] arr = ar.getStr("areaids").split(",");
            for (String id : arr) {
                paras.add(id);
            }
            sqlExceptSelect.append(" AND em.areaid IN ("+ Util.sqlHolder(arr.length) +") ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "ar.recordtime DESC ";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        StringBuilder select = new StringBuilder()
            .append("SELECT ar.*, em.code AS emcode, em.typeid AS emtypeid ");
        Page<EmAlarmRecord> page = dao.paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    public List<EmAlarmRecord> listBy(Object eminfoid) {
        String sql = "SELECT * FROM em_alarm_record WHERE eminfoid = ? ORDER BY recordtime DESC";
        List<EmAlarmRecord> list = find(sql, eminfoid);
        return list;
    }
    
    public List<EmAlarmRecord> listBy(Object eminfoid, int alarmrecordtypeid) {
        String sql = "SELECT * FROM em_alarm_record WHERE eminfoid = ? AND alarmrecordtypeid = ?";
        List<EmAlarmRecord> list = find(sql, eminfoid, alarmrecordtypeid);
        return list;
    }
    
    public List<EmAlarmRecord> listBy(int alarmtypeid, int alarmrecordtypeid) {
        String sql = "SELECT * FROM em_alarm_record WHERE alarmtypeid = ? AND alarmrecordtypeid = ?";
        List<EmAlarmRecord> list = find(sql, alarmtypeid, alarmrecordtypeid);
        return list;
    }
    
    public int countBy(int alarmtypeid, int alarmrecordtypeid) {
        String sql = "SELECT COUNT(*) FROM em_alarm_record WHERE alarmtypeid = ? AND alarmrecordtypeid = ?";
        return Db.queryInt(sql, alarmtypeid, alarmrecordtypeid);
    }
    
}
