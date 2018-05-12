package com.em.model.em;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.em.model.BaseModel;
import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="em_record_history")
public class EmRecordHistory extends BaseModel<EmRecordHistory> {
    
    public static final EmRecordHistory dao = new EmRecordHistory();
    
    public Page<EmRecordHistory> paging(EmRecordHistory his, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder();
        sqlExceptSelect
            .append("FROM em_record_history his ")
            .append("LEFT JOIN em_info em ON his.eminfoid = em.id ")
            .append("LEFT JOIN em_type type ON em.typeid = type.id ")
            
            /*.append("LEFT JOIN sys_area area ON his.areaid = area.id ")
            .append("LEFT JOIN em_power power ON his.powerid = power.id ")
            .append("LEFT JOIN em_battery battery ON his.batteryid = battery.id ")
            .append("LEFT JOIN em_battery_type batterytype ON his.batterytypeid = batterytype.id ")
            .append("LEFT JOIN em_communication comm ON his.commid = comm.id ")
            .append("LEFT JOIN em_communication_protocol commp ON his.cpid = commp.id ")
            .append("LEFT JOIN em_brightlevel brightlevel ON his.brightlevelid = brightlevel.id ")
            .append("LEFT JOIN em_excursus excursus ON his.excursusid = excursus.id ")
            .append("LEFT JOIN em_info_setting setting ON his.id = setting.eminfoid ")
            .append("LEFT JOIN em_light light ON setting.lightid = light.id ")
            .append("LEFT JOIN em_light_source lightsource ON setting.lightsourceid = lightsource.id ")
            .append("LEFT JOIN em_info_config config ON his.id = config.eminfoid ")*/
            .append("WHERE 1 = 1 ");
        
        //base info
        if (!Util.isEmptyString(his.get("positionid"))) {
            paras.add(his.get("positionid"));
            sqlExceptSelect.append(" AND his.positionid = ? ");
        }
        if (!Util.isEmptyString(his.get("lng_agps"))) {
            paras.add("%"+ his.get("lng_agps") +"%");
            sqlExceptSelect.append(" AND his.lng_agps LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("lat_agps"))) {
            paras.add("%"+ his.get("lat_agps") +"%");
            sqlExceptSelect.append(" AND his.lat_agps LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("lng_lbs"))) {
            paras.add("%"+ his.get("lng_lbs") +"%");
            sqlExceptSelect.append(" AND his.lng_lbs LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("lat_lbs"))) {
            paras.add("%"+ his.get("lat_lbs") +"%");
            sqlExceptSelect.append(" AND his.lat_lbs LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("lng_gps"))) {
            paras.add("%"+ his.get("lng_gps") +"%");
            sqlExceptSelect.append(" AND his.lng_gps LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("lat_gps"))) {
            paras.add("%"+ his.get("lat_gps") +"%");
            sqlExceptSelect.append(" AND his.lat_gps LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("positioncommtime"))) {
            paras.add("%"+ his.get("positioncommtime") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), positioncommtime, 21) LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("emstate"))) {
            paras.add(his.get("emstate"));
            sqlExceptSelect.append(" AND his.emstate = ? ");
        }
        if (!Util.isEmptyString(his.get("workstateid"))) {
            paras.add(his.get("workstateid"));
            sqlExceptSelect.append(" AND his.workstateid = ? ");
        }
        if (!Util.isEmptyString(his.get("onlinestateid"))) {
            paras.add(his.get("onlinestateid"));
            sqlExceptSelect.append(" AND his.onlinestateid = ? ");
        }
        if (!Util.isEmptyString(his.get("commid"))) {
            paras.add(his.get("commid"));
            sqlExceptSelect.append(" AND his.commid = ? ");
        }
        if (!Util.isEmptyString(his.get("currentvoltage"))) {
            paras.add("%"+ his.get("currentvoltage") +"%");
            sqlExceptSelect.append(" AND his.currentvoltage LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("currentcurrent"))) {
            paras.add("%"+ his.get("currentcurrent") +"%");
            sqlExceptSelect.append(" AND his.currentcurrent LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("currentlightsourceid"))) {
            paras.add(his.get("currentlightsourceid"));
            sqlExceptSelect.append(" AND his.currentlightsourceid = ? ");
        }
        if (!Util.isEmptyString(his.get("currentlightid"))) {
            paras.add(his.get("currentlightid"));
            sqlExceptSelect.append(" AND his.currentlightid = ? ");
        }
        if (!Util.isEmptyString(his.get("workcommtime"))) {
            paras.add("%"+ his.get("workcommtime") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), workcommtime, 21) LIKE ? ");
        }
        if (!Util.isEmptyString(his.get("battery1_power"))) {
            paras.add(his.get("battery1_power"));
            sqlExceptSelect.append(" AND his.battery1_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery1_voltage"))) {
            paras.add(his.get("battery1_voltage"));
            sqlExceptSelect.append(" AND his.battery1_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery1_stateid"))) {
            paras.add(his.get("battery1_stateid"));
            sqlExceptSelect.append(" AND his.battery1_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery2_power"))) {
            paras.add(his.get("battery2_power"));
            sqlExceptSelect.append(" AND his.battery2_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery2_voltage"))) {
            paras.add(his.get("battery2_voltage"));
            sqlExceptSelect.append(" AND his.battery2_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery2_stateid"))) {
            paras.add(his.get("battery2_stateid"));
            sqlExceptSelect.append(" AND his.battery2_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery3_power"))) {
            paras.add(his.get("battery3_power"));
            sqlExceptSelect.append(" AND his.battery3_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery3_voltage"))) {
            paras.add(his.get("battery3_voltage"));
            sqlExceptSelect.append(" AND his.battery3_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery3_stateid"))) {
            paras.add(his.get("battery3_stateid"));
            sqlExceptSelect.append(" AND his.battery3_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery4_power"))) {
            paras.add(his.get("battery4_power"));
            sqlExceptSelect.append(" AND his.battery4_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery4_voltage"))) {
            paras.add(his.get("battery4_voltage"));
            sqlExceptSelect.append(" AND his.battery4_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery4_stateid"))) {
            paras.add(his.get("battery4_stateid"));
            sqlExceptSelect.append(" AND his.battery4_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery5_power"))) {
            paras.add(his.get("battery5_power"));
            sqlExceptSelect.append(" AND his.battery5_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery5_voltage"))) {
            paras.add(his.get("battery5_voltage"));
            sqlExceptSelect.append(" AND his.battery5_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery5_stateid"))) {
            paras.add(his.get("battery5_stateid"));
            sqlExceptSelect.append(" AND his.battery5_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery6_power"))) {
            paras.add(his.get("battery6_power"));
            sqlExceptSelect.append(" AND his.battery6_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery6_voltage"))) {
            paras.add(his.get("battery6_voltage"));
            sqlExceptSelect.append(" AND his.battery6_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery6_stateid"))) {
            paras.add(his.get("battery6_stateid"));
            sqlExceptSelect.append(" AND his.battery6_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery7_power"))) {
            paras.add(his.get("battery7_power"));
            sqlExceptSelect.append(" AND his.battery7_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery7_voltage"))) {
            paras.add(his.get("battery7_voltage"));
            sqlExceptSelect.append(" AND his.battery7_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery7_stateid"))) {
            paras.add(his.get("battery7_stateid"));
            sqlExceptSelect.append(" AND his.battery7_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("battery8_power"))) {
            paras.add(his.get("battery8_power"));
            sqlExceptSelect.append(" AND his.battery8_power = ? ");
        }
        if (!Util.isEmptyString(his.get("battery8_voltage"))) {
            paras.add(his.get("battery8_voltage"));
            sqlExceptSelect.append(" AND his.battery8_voltage = ? ");
        }
        if (!Util.isEmptyString(his.get("battery8_stateid"))) {
            paras.add(his.get("battery8_stateid"));
            sqlExceptSelect.append(" AND his.battery8_stateid = ? ");
        }
        if (!Util.isEmptyString(his.get("batterycommtime"))) {
            paras.add("%"+ his.get("workcommtime") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), batterycommtime, 21) LIKE ? ");
        }
        //
        if (!Util.isEmptyString(his.get("emtypeid"))) {
            paras.add(his.get("emtypeid"));
            sqlExceptSelect.append(" AND em.typeid = ? ");
        }
        if (!Util.isEmptyString(his.get("emcode"))) {
            paras.add("%"+ his.get("emcode") +"%");
            sqlExceptSelect.append(" AND em.code LIKE ? ");
        }
        
        // eminfoid
        if (!Util.isEmptyString(his.get("eminfoid"))) {
            paras.add(his.get("eminfoid"));
            sqlExceptSelect.append(" AND his.eminfoid = ? ");
        }
        
        // areaids
        if (!Util.isEmptyString(his.get("areaids"))) {
            String[] arr = his.getStr("areaids").split(",");
            for (String id : arr) {
                paras.add(id);
            }
            sqlExceptSelect.append(" AND em.areaid IN ("+ Util.sqlHolder(arr.length) +") ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "positioncommtime DESC, workcommtime DESC, batterycommtime DESC ";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        StringBuilder select = new StringBuilder()
            .append("SELECT his.*, em.code AS emcode, em.typeid AS emtypeid ");
//            .append("type.name AS typename, comm.name AS commname, commp.name AS commpname, ")
//            .append("power.name AS powername, battery.name AS batteryname, batterytype.name AS batterytypename, ")
//            .append("brightlevel.name AS brightlevelname, setting.lightid, setting.lightsourceid, light.name AS lightname, lightsource.name AS lightsourcename, ")
//            .append("setting.charginglimit, setting.dischargelimit, setting.forcedwork, setting.syncwork");
        Page<EmRecordHistory> page = dao.paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    // list voltage && current by eminfoid
    public List<EmRecordHistory> listVoltageCurrentBy(Object eminfoid) {
        StringBuffer sql = new StringBuffer()
            .append("SELECT ")
            .append("voltage, [current], workcommtime ")
            .append("FROM em_record_history ")
            .append("WHERE eminfoid = ? ")
            .append("ORDER BY workcommtime ASC ");
        return find(sql.toString(), eminfoid);
    }
    
    // list battery by eminfoid
    public List<EmRecordHistory> listBatteryBy(Object eminfoid) {
        StringBuffer sql = new StringBuffer()
            .append("SELECT ")
            .append("battery1_power,battery1_voltage,battery1_stateid,battery2_power,battery2_voltage,battery2_stateid,")
            .append("battery3_power,battery3_voltage,battery3_stateid,battery4_power,battery4_voltage,battery4_stateid,")
            .append("battery5_power,battery5_voltage,battery5_stateid,battery6_power,battery6_voltage,battery6_stateid,")
            .append("battery7_power,battery7_voltage,battery7_stateid,battery8_power,battery8_voltage,battery8_stateid,")
            .append("batterycommtime ")
            .append("FROM em_record_history ")
            .append("WHERE eminfoid = ? ")
            .append("ORDER BY batterycommtime ASC ");
        return find(sql.toString(), eminfoid);
    }
    
    // list position by eminfoid
    public List<EmRecordHistory> listPositionBy(EmRecordHistory his, Object eminfoid) {
        StringBuffer sql = new StringBuffer()
            .append("SELECT ")
            .append("positionid,lng_agps,lat_agps,lng_lbs,lat_lbs,lng_gps,lat_gps,positioncommtime ")
            .append("FROM em_record_history ")
            .append("WHERE eminfoid = ? ");
        
        List<Object> paras = new ArrayList<Object>();
        paras.add(eminfoid);
        if (!Util.isEmptyString(his.get("starttime"))) {
            Date starttime = null;
            try {
                starttime = Util.convertStringToDate(his.getStr("starttime"));
            } catch (Exception e) {e.printStackTrace();}
            if (starttime != null) {
                sql.append(" AND positioncommtime >= ? ");
                paras.add(Util.getSqlTime(starttime));
            }
        }
        if (!Util.isEmptyString(his.get("endtime"))) {
            Date endtime = null;
            try {
                endtime = Util.convertStringToDate(his.getStr("endtime") + " 23:59:59");
            } catch (Exception e) {e.printStackTrace();}
            if (endtime != null) {
                sql.append(" AND positioncommtime <= ? ");
                paras.add(Util.getSqlTime(endtime));
            }
        }
        
        sql.append("ORDER BY positioncommtime ASC ");
        return find(sql.toString(), paras.toArray());
    }
    public List<EmRecordHistory> listRecordHistoryBy(Object eminfoid) {
        StringBuffer sql = new StringBuffer()
            .append("SELECT ")
            .append("CHARGESOC,DISCHARGESOC, WORKCOMMTIME ")
            .append("FROM em_record_history ")
            .append("WHERE eminfoid = ? ")
            .append("ORDER BY workcommtime ASC ");
        return find(sql.toString(), eminfoid);
    }
    
    
}
