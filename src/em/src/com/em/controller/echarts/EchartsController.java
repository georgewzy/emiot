package com.em.controller.echarts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.em.controller.BaseController;
import com.em.model.em.EmAlarmRecord;
import com.em.model.em.EmAlarmtype;
import com.em.model.em.EmArea;
import com.em.model.em.EmBatteryState;
import com.em.model.em.EmInfo;
import com.em.model.em.EmInfoConfig;
import com.em.model.em.EmOnofftime;
import com.em.model.em.EmRecordCurrent;
import com.em.model.em.EmRecordHistory;
import com.em.tools.Util;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;

@ControllerBind(controllerKey="/sys/echarts", viewPath="/pages/echarts")
public class EchartsController extends BaseController {

    @Override
    public void index() {
        // TODO Auto-generated method stub
        
    }
    
    // 报警统计
    public void embug() {
        //"位置报警","电压上限报警","电压下限报警","电流上限报警","电流下限报警","灯状态报警","传感器故障报警","电池故障报警"
        String[] tits = new String[] {};
        List<EmAlarmtype> typeList        = EmAlarmtype.dao.list();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        
        // 正常设备
        int count = EmRecordCurrent.dao.countOK();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", count);
        map.put("name", "正常设备");
        mapList.add(map);
        tits = (String[]) ArrayUtils.add(tits, "正常设备");
        
        int total = 0;
        for (EmAlarmtype t : typeList) {
            tits = (String[]) ArrayUtils.add(tits, t.get("name"));
            count = EmAlarmRecord.dao.countBy(t.getInt("id"), 1);
            total += count;
            map   = new HashMap<String, Object>();
            map.put("value", count);
            map.put("name", t.get("name"));
            mapList.add(map);
        }
        
        if (total == 0) {
            Map<String, Object> okMap = mapList.get(0);
            okMap.put("value", 100);
        }
        
        setAttr("tits", JsonKit.toJson(tits));
        setAttr("data", JsonKit.toJson(mapList));
        render("em_bug_pie.jsp");
    }
    
    // 分省统计
    public void emmap() {
        List<EmArea> areaList = EmArea.dao.listGroupProvince();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        int max = 0;
        for (EmArea ea : areaList) {
            String ids = EmArea.dao.getChildrenids(ea.getStr("province"));
            int count = EmInfo.dao.countBy(ids);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", ea.get("province"));
            map.put("value", count);
            mapList.add(map);
            if (max < count) max = count;
        }
        setAttr("datas", JsonKit.toJson(mapList));
        setAttr("max", max);
        render("em_map.jsp");
    }
    
    // 电池电压
    public void emvoltage() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        EmInfoConfig emconfig = EmInfoConfig.dao.getBy(id);
        String[] tits = new String[] {};
        Double[] datas = new Double[] {};
        Object maxV = 0f;
        Object minV = 0f;
        Integer batteryNum = 0;
        if (em != null) {
            batteryNum = em.getInt("batteryid") == null ? 0 : em.getInt("batteryid");
            for (int i = 1; i <= batteryNum; i++) {
                tits = (String[]) ArrayUtils.add(tits, "电池"+ i);
            }
            EmRecordCurrent current = EmRecordCurrent.dao.getBy(id);
            Object voltage = 0f;
            for (int i = 1; i <= batteryNum; i++) {
                String keyname = "battery"+ i +"_voltage";
                if (current != null) voltage = current.get(keyname) == null ? 0f : current.get(keyname);
                datas = (Double[]) ArrayUtils.add(datas, voltage);
            }
        }
        if (emconfig != null) {
            maxV = emconfig.get("voltageupper");
            minV = emconfig.get("voltagelimit");
        }
        setAttr("tits", JsonKit.toJson(tits));
        setAttr("datas", JsonKit.toJson(datas));
        setAttr("maxV", maxV);
        setAttr("minV", minV);
        setAttr("batteryNum", batteryNum);
        render("em_voltage.jsp");
    }
    
    // 电压电流曲线
    public void voltagecurrent() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        EmInfoConfig config = EmInfoConfig.dao.getBy(id);
        Object[] times = new Object[] {};
        Object[] data_voltage = new Object[] {};
        Object[] data_current = new Object[] {};
        if (em != null) {
            List<EmRecordHistory> rList = EmRecordHistory.dao.listVoltageCurrentBy(id);
            for (EmRecordHistory rr : rList) {
                if (!Util.isEmptyString(rr.get("workcommtime"))) {
                    Object commtime = Util.getFormatDate(rr.getDate("workcommtime"), "yyyy-MM-dd HH:mm");
                    Object voltage  = rr.get("voltage") == null ? 0 : rr.get("voltage");
                    Object current  = rr.get("current") == null ? 0 : rr.get("current");
                    times = ArrayUtils.add(times, commtime);
                    data_voltage = ArrayUtils.add(data_voltage, voltage);
                    data_current = ArrayUtils.add(data_current, current);
                }
            }
        }
        if (times.length == 0) {
            times = new Object[] {"2015-09-10 15:00","2015-09-11 18:00","2015-09-12 10:00","2015-09-13 11:00","2015-09-14 16:00","2015-09-15 08:00","2015-09-16 13:00"};
            data_voltage = new Object[] {120, 132, 101, 134, 90, 230, 210};
            data_current = new Object[] {220, 182, 191, 234, 290, 330, 310};
            setAttr("isTest", true);
        }
        setAttr("times", JsonKit.toJson(times));
        setAttr("data_voltage", JsonKit.toJson(data_voltage));
        setAttr("data_current", JsonKit.toJson(data_current));
        setAttr("config", config);
        render("em_voltagecurrent.jsp");
    }
    // 充放电量
    public void recordhistory() {
    	String id = getPara();
    	EmInfo em = EmInfo.dao.findById(id);
    	double ah = (double)em.getInt("ah");
    	Object[] times = new Object[] {};
    	Object[] data_chargesoc = new Object[] {};
    	Object[] data_dischargesoc = new Object[] {};
    	if (em != null) {
    		List<EmRecordHistory> rList = EmRecordHistory.dao.listRecordHistoryBy(id);
    		for (EmRecordHistory rr : rList) {
    			if (!Util.isEmptyString(rr.get("workcommtime"))) {
    				Object commtime = Util.getFormatDate(rr.getTimestamp("workcommtime"), "yyyy-MM-dd HH:mm:ss");
    				Object chargesoc  = rr.getDouble("chargesoc") == null ? new Double(0) : rr.getDouble("chargesoc");
    				Object dischargesoc  = rr.getDouble("dischargesoc") == null ? new Double(0) : rr.getDouble("dischargesoc");
    				times = ArrayUtils.add(times, commtime);
    				data_chargesoc = ArrayUtils.add(data_chargesoc, chargesoc);
    				data_dischargesoc = ArrayUtils.add(data_dischargesoc, dischargesoc);
    			}
    		}
    	}
    	if (times.length == 0) {
    		times = new Object[] {"2015-09-10 15:00","2015-09-11 18:00","2015-09-12 10:00","2015-09-13 11:00","2015-09-14 16:00","2015-09-15 08:00","2015-09-16 13:00"};
    		data_chargesoc = new Object[] {0.3, 1.62, 1.32, 1.142, 1.052, 1.2, 1.3};
    		data_dischargesoc = new Object[] {1.07, 1.33, 1.02, 1.36, 1.01, 1.11, 1.4};
    		setAttr("isTest", true);
    	}
    	setAttr("times", JsonKit.toJson(times));
    	setAttr("data_chargesoc", JsonKit.toJson(data_chargesoc));
    	setAttr("data_dischargesoc", JsonKit.toJson(data_dischargesoc));
    	setAttr("ah", ah);
    	render("em_recordhistory.jsp");
    }
    
    // 电池状态
    public void battery() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        Object[] tits  = new Object[] {};
        Object[] times = new Object[] {};
        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
        Map<String, String> stateMap       = EmBatteryState.dao.listMap();
        Integer batteryNum = 0;
        if (em != null) {
            batteryNum = em.getInt("batteryid") == null ? 0 : em.getInt("batteryid");
            List<EmRecordHistory> rList = EmRecordHistory.dao.listBatteryBy(id);
            if (!rList.isEmpty()) {
                for (int i = 1; i <= batteryNum; i++) {
                    String name_v = "电池"+ i +"电压";
                    String name_p = "电池"+ i +"剩余电量";
                    //String name_s = "电池"+ i +"状态";
                    tits          = ArrayUtils.addAll(tits, new Object[] {name_v, name_p}); //, name_s
                    Object[] data_voltage = new Object[] {};
                    Object[] data_power   = new Object[] {};
                    Object[] data_state   = new Object[] {};
                    List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
                    for (EmRecordHistory rr : rList) {
                        if (!Util.isEmptyString(rr.get("batterycommtime"))) {
                            String key_voltage = "battery"+ i +"_voltage";
                            String key_power   = "battery"+ i +"_power";
                            String key_state   = "battery"+ i +"_stateid";
                            String state       = "";
                            Object commtime    = Util.getFormatDate(rr.getDate("batterycommtime"), "yyyy-MM-dd HH:mm");
                            Object voltage     = rr.get(key_voltage) == null ? 0 : rr.get(key_voltage);
                            Object power       = rr.get(key_power) == null ? 0 : rr.get(key_power);
                            
                            if (!rr.get(key_state).equals(0)) {
                                Map<String, Object> obj = new HashMap<String, Object>();
                                obj.put("value", voltage);
                                obj.put("symbol", rr.get(key_state).equals(1) ? "star" : "triangle");
                                obj.put("symbolSize", 8);
                                voltage = obj;
                            }
                            
                            if (rr.get(key_state) != null && !rr.get(key_state).equals(0)) {
                                state = stateMap.get(String.valueOf(rr.get(key_state)));
                            }
                            
                            if (times.length != rList.size())
                                times = ArrayUtils.add(times, commtime);
                            
                            data_voltage = ArrayUtils.add(data_voltage, voltage);
                            data_power   = ArrayUtils.add(data_power, power);
                            data_state   = ArrayUtils.add(data_state, Util.isEmptyString(state) ? "-" : new String[] {state});
                            if (!Util.isEmptyString(state)) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("name", "状态");
                                map.put("value", i);
                                map.put("symbol", "triangle");
                                map.put("xAxis", commtime);
                                map.put("yAixs", "100");
                                
                                pointList.add(map);
                            }
                        }
                    }
                    Map<String, Object> mapdata = new HashMap<String, Object>();
                    mapdata.put("name", name_v);
                    mapdata.put("type", "line");
                    mapdata.put("data", data_voltage);
                    dataList.add(mapdata);
                    mapdata = new HashMap<String, Object>();
                    mapdata.put("name", name_p);
                    mapdata.put("type", "line");
                    mapdata.put("yAxisIndex", 1);
                    mapdata.put("data", data_power);
                    dataList.add(mapdata);
                    //mapdata = new HashMap<String, Object>();
                    //mapdata.put("name", name_s);
                    //mapdata.put("type", "scatter");
                    //mapdata.put("data", data_state);
                    //Map<String, Object> point = new HashMap<String, Object>();
                    //point.put("data", pointList);
                    //mapdata.put("markPoint", point);
                    
                    //dataList.add(mapdata);
                }
            }
        }
        if (times.length == 0) {
            tits  = new Object[] {};
            times = new Object[] {"2015-09-10 15:00","2015-09-11 18:00","2015-09-12 10:00","2015-09-13 11:00","2015-09-14 16:00","2015-09-15 08:00","2015-09-16 13:00"};
            
            for (int i = 1; i < 5; i++) {
                Map<String, Object> sMap = new HashMap<String, Object>();
                sMap.put("icon", "image://../images/triangle.png");
                sMap.put("name", "电池"+ i +"状态");
                tits = ArrayUtils.addAll(tits, new Object[] {"电池"+ i +"电压","电池"+ i +"剩余电量", sMap});
                int dd = i * 10;
                Object[] data_voltage = new Object[] {120 - dd, 102 + dd , 101 + dd, 134 - dd, 90 + dd, 230 + dd, 210 - dd};
                Object[] data_power   = new Object[] {220 - dd, 182 + dd, 191 + dd, 234 - dd, 290 + dd, 330 + dd, 310 - dd};
                Object[] data_state   = new Object[] {"-", new String[] {"电池异常"}, "-", "-", "-", new String[] {"旁路"}, "-"};
                Map<String, Object> mapdata = new HashMap<String, Object>();
                mapdata.put("name", "电池"+ i +"电压");
                mapdata.put("type", "line");
                mapdata.put("data", data_voltage);
                dataList.add(mapdata);
                mapdata = new HashMap<String, Object>();
                mapdata.put("name", "电池"+ i +"剩余电量");
                mapdata.put("type", "line");
                mapdata.put("yAxisIndex", 1);
                mapdata.put("data", data_power);
                dataList.add(mapdata);
                mapdata = new HashMap<String, Object>();
                mapdata.put("name", "电池"+ i +"状态");
                mapdata.put("type", "scatter");
                mapdata.put("data", data_state);
                Map<String, Object> point = new HashMap<String, Object>();
                
                List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", "状态");
                map.put("value", new String[] {"异常"});
                map.put("symbol", "triangle");
                map.put("xAxis", times[i + 1]);
                map.put("yAixs", data_voltage[i + 1]);
                pointList.add(map);
                map = new HashMap<String, Object>();
                map.put("name", "状态");
                map.put("value", new String[] {"旁路"});
                map.put("symbol", "triangle");
                map.put("xAxis", times[i]);
                map.put("yAixs", data_voltage[i]);
                pointList.add(map);
                point.put("data", pointList);
                mapdata.put("markPoint", point);
                dataList.add(mapdata);
            }
            
            setAttr("isTest", true);
        }
        
        setAttr("tits", JsonKit.toJson(tits));
        setAttr("times", JsonKit.toJson(times));
        setAttr("datas", JsonKit.toJson(dataList));
        setAttr("batteryNum", batteryNum);
        render("em_battery.jsp");
    }
 // 开关时间曲线
    public void onofftime() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        Object[] times = new Object[] {};
        Object[] data_onoffstate = new Object[] {};
        
        if (em != null) {
            List<EmOnofftime> rList = EmOnofftime.dao.list(id);
            for (EmOnofftime rr : rList) {
                if (!Util.isEmptyString(rr.get("onofftime"))) {
                    Object onofftime = Util.getFormatDate(rr.getTimestamp("onofftime"), "yyyy-MM-dd HH:mm:ss");
                    Object onoffstate  = rr.get("onoffstate")?0:1 ;
                    times = ArrayUtils.add(times, onofftime);
                    data_onoffstate = ArrayUtils.add(data_onoffstate, onoffstate);
                }
            }
        }
        if (times.length == 0) {
            times = new Object[] {"2015-09-10 15:00","2015-09-11 18:00","2015-09-12 10:00","2015-09-13 11:00","2015-09-14 16:00","2015-09-15 08:00","2015-09-16 13:00"};
            data_onoffstate = new Object[] {0, 1, 1, 1, 0, 0, 1};
            setAttr("isTest", true);
        }
        setAttr("times", JsonKit.toJson(times));
        setAttr("data_onoffstate", JsonKit.toJson(data_onoffstate));
        render("em_onofftime.jsp");
    }
    
}
