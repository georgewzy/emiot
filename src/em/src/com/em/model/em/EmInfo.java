package com.em.model.em;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.em.model.BaseModel;
import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="em_info")
public class EmInfo extends BaseModel<EmInfo> {
    
    public static final EmInfo dao = new EmInfo();
    
    public Page<EmInfo> paging(EmInfo em, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder();
        sqlExceptSelect
            .append("FROM em_info em ")
            .append("LEFT JOIN em_type type ON em.typeid = type.id ")
            .append("LEFT JOIN sys_area area ON em.areaid = area.id ")
            .append("LEFT JOIN em_power power ON em.powerid = power.id ")
            .append("LEFT JOIN em_battery battery ON em.batteryid = battery.id ")
            .append("LEFT JOIN em_battery_type batterytype ON em.batterytypeid = batterytype.id ")
            .append("LEFT JOIN em_communication comm ON em.commid = comm.id ")
            .append("LEFT JOIN em_communication_protocol commp ON em.cpid = commp.id ")
            .append("LEFT JOIN em_brightlevel brightlevel ON em.brightlevelid = brightlevel.id ")
            .append("LEFT JOIN em_excursus excursus ON em.excursusid = excursus.id ")
            .append("LEFT JOIN em_info_setting setting ON em.id = setting.eminfoid ")
            .append("LEFT JOIN em_light light ON setting.lightid = light.id ")
            .append("LEFT JOIN em_light_source lightsource ON setting.lightsourceid = lightsource.id ")
            .append("LEFT JOIN em_info_config config ON em.id = config.eminfoid ")
            .append("WHERE em.deletetime IS NULL ");
        
        //base info
        if (!Util.isEmptyString(em.get("areaid"))) {
            paras.add(em.get("areaid"));
            sqlExceptSelect.append(" AND em.areaid = ? ");
        }
        if (!Util.isEmptyString(em.get("typeid"))) {
            paras.add(em.get("typeid"));
            sqlExceptSelect.append(" AND em.typeid = ? ");
        }
        if (!Util.isEmptyString(em.get("code"))) {
            paras.add("%"+ em.get("code") +"%");
            sqlExceptSelect.append(" AND em.code LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("name"))) {
            paras.add("%"+ em.get("name") +"%");
            sqlExceptSelect.append(" AND em.name LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("address"))) {
            paras.add("%"+ em.get("address") +"%");
            sqlExceptSelect.append(" AND em.address LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("lng"))) {
            paras.add("%"+ em.get("lng") +"%");
            sqlExceptSelect.append(" AND em.lng LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("lat"))) {
            paras.add("%"+ em.get("lat") +"%");
            sqlExceptSelect.append(" AND em.lat LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("ah"))) {
            paras.add(em.get("ah"));
            sqlExceptSelect.append(" AND em.ah = ? ");
        }
        if (!Util.isEmptyString(em.get("cardno"))) {
            paras.add("%"+ em.get("cardno") +"%");
            sqlExceptSelect.append(" AND em.cardno LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("batteryid"))) {
            paras.add(em.get("batteryid"));
            sqlExceptSelect.append(" AND em.batteryid = ? ");
        }
        if (!Util.isEmptyString(em.get("batterytypeid"))) {
            paras.add(em.get("batterytypeid"));
            sqlExceptSelect.append(" AND em.batterytypeid = ? ");
        }
        if (!Util.isEmptyString(em.get("brightlevelid"))) {
            paras.add(em.get("brightlevelid"));
            sqlExceptSelect.append(" AND em.brightlevelid = ? ");
        }
        if (!Util.isEmptyString(em.get("commid"))) {
            paras.add(em.get("commid"));
            sqlExceptSelect.append(" AND em.commid = ? ");
        }
        if (!Util.isEmptyString(em.get("cpid"))) {
            paras.add(em.get("cpid"));
            sqlExceptSelect.append(" AND em.cpid = ? ");
        }
        if (!Util.isEmptyString(em.get("powerid"))) {
            paras.add(em.get("powerid"));
            sqlExceptSelect.append(" AND em.powerid = ? ");
        }
        if (!Util.isEmptyString(em.get("excursusid"))) {
            paras.add(em.get("excursusid"));
            sqlExceptSelect.append(" AND em.excursusid = ? ");
        }
        //setting
        if (!Util.isEmptyString(em.get("ip"))) {
            paras.add("%"+ em.get("ip") +"%");
            sqlExceptSelect.append(" AND setting.ip LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("port"))) {
            paras.add("%"+ em.get("port") +"%");
            sqlExceptSelect.append(" AND setting.port LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("apn"))) {
            paras.add("%"+ em.get("apn") +"%");
            sqlExceptSelect.append(" AND setting.apn LIKE ? ");
        }
        if (!Util.isEmptyString(em.get("lightid"))) {
            paras.add(em.get("lightid"));
            sqlExceptSelect.append(" AND setting.lightid = ? ");
        }
        if (!Util.isEmptyString(em.get("lightsourceid"))) {
            paras.add(em.get("lightsourceid"));
            sqlExceptSelect.append(" AND setting.lightsourceid = ? ");
        }
        if (!Util.isEmptyString(em.get("charginglimit"))) {
            paras.add(em.get("charginglimit"));
            sqlExceptSelect.append(" AND setting.charginglimit = ? ");
        }
        if (!Util.isEmptyString(em.get("dischargelimit"))) {
            paras.add(em.get("dischargelimit"));
            sqlExceptSelect.append(" AND setting.dischargelimit = ? ");
        }
        if (!Util.isEmptyString(em.get("forcedwork"))) {
            paras.add(em.get("forcedwork"));
            sqlExceptSelect.append(" AND setting.forcedwork = ? ");
        }
        if (!Util.isEmptyString(em.get("syncwork"))) {
            paras.add(em.get("syncwork"));
            sqlExceptSelect.append(" AND setting.syncwork = ? ");
        }
        //config
        if (!Util.isEmptyString(em.get("events"))) {
            paras.add(em.get("events"));
            sqlExceptSelect.append(" AND config.events = ? ");
        }
        if (!Util.isEmptyString(em.get("scope"))) {
            paras.add(em.get("scope"));
            sqlExceptSelect.append(" AND config.scope = ? ");
        }
        if (!Util.isEmptyString(em.get("voltageupper"))) {
            paras.add(em.get("voltageupper"));
            sqlExceptSelect.append(" AND config.voltageupper = ? ");
        }
        if (!Util.isEmptyString(em.get("voltagelimit"))) {
            paras.add(em.get("voltagelimit"));
            sqlExceptSelect.append(" AND config.voltagelimit = ? ");
        }
        if (!Util.isEmptyString(em.get("currentupper"))) {
            paras.add(em.get("currentupper"));
            sqlExceptSelect.append(" AND config.currentupper = ? ");
        }
        if (!Util.isEmptyString(em.get("currentlimit"))) {
            paras.add(em.get("currentlimit"));
            sqlExceptSelect.append(" AND config.currentlimit = ? ");
        }
        
        // areaids
        if (!Util.isEmptyString(em.get("areaids"))) {
            String[] arr = em.getStr("areaids").split(",");
            for (String id : arr) {
                paras.add(id);
            }
            sqlExceptSelect.append(" AND em.areaid IN ("+ Util.sqlHolder(arr.length) +") ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "type.id ";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        StringBuilder select = new StringBuilder()
            .append("SELECT em.*,")
            .append("type.name AS typename, comm.name AS commname, commp.name AS commpname, ")
            .append("power.name AS powername, battery.name AS batteryname, batterytype.name AS batterytypename, ")
            .append("brightlevel.name AS brightlevelname, setting.lightid, setting.lightsourceid, light.name AS lightname, lightsource.name AS lightsourcename, ")
            .append("setting.charginglimit, setting.dischargelimit, setting.forcedwork, setting.syncwork");
        Page<EmInfo> page = dao.paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    public List<EmInfo> list(EmInfo e) {
        Object[] paras    = new Object[] {};
        StringBuilder sql = new StringBuilder()
            .append("SELECT em.*, type.name AS typename, type.icon AS typeicon, type.pic AS typepic, ")
            .append("rc.emstate, rc.onlinestateid, online.name AS onlinestate, rc.workcommtime ")
            .append("FROM em_info em ")
            .append("LEFT JOIN em_type type ON em.typeid = type.id ")
            .append("LEFT JOIN em_record_current rc ON em.id = rc.eminfoid ")
            .append("LEFT JOIN em_online_state online ON rc.onlinestateid = online.id ")
            .append("WHERE em.deletetime IS NULL ");
        
        if (!Util.isEmptyString(e.get("areaids"))) {
            String[] arr = e.getStr("areaids").split(",");
            sql.append(" AND areaid IN ("+ Util.sqlHolder(arr.length) +") ");
            paras = ArrayUtils.addAll(paras, arr);
        }
        if (!Util.isEmptyString(e.get("typeid"))) {
            paras = ArrayUtils.add(paras, e.get("typeid"));
            sql.append(" AND typeid = ? ");
        }
        
        sql.append(" ORDER BY type.seq ");
        
        if (ArrayUtils.isEmpty(paras))
            return find(sql.toString());
        else
            return find(sql.toString(), paras);
    }
    
    public EmInfo getBy(String id) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT em.*, type.name AS typename, type.pic, power.name AS powername, comm.name AS commname, excursus.name AS excursusname ")
            .append("FROM em_info em ")
            .append("LEFT JOIN em_type type ON em.typeid = type.id ")
            .append("LEFT JOIN em_power power ON em.powerid = power.id ")
            .append("LEFT JOIN em_communication comm ON em.commid = comm.id ")
            .append("LEFT JOIN em_excursus excursus ON em.excursusid = excursus.id ")
            .append("WHERE em.id = ? ");
        return findFirst(sql.toString(), id);
    }
    
    public int countBy(String areaids) {
        if (Util.isEmptyString(areaids)) return 0;
        Object[] arr = areaids.split(",");
        String sql = "SELECT COUNT(*) FROM em_info WHERE areaid IN ("+ Util.sqlHolder(arr.length) +") AND deletetime IS NULL";
        return Db.queryInt(sql, arr);
    }
    
}
