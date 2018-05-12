package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_record_onofftime_history")
public class EmOnofftime extends BaseModel<EmOnofftime> {
    
    public static final EmOnofftime dao = new EmOnofftime();
    
    public List<EmOnofftime> list(String eminfoid) {
    	 StringBuffer sql = new StringBuffer()
    			 .append("SELECT ")
    			 .append("ONOFFSTATE,ONOFFTIME ")
    			 .append("FROM EM_RECORD_ONOFFTIME_HISTORY ")
    			 .append("WHERE EMINFOID = ? ")
    			 .append("ORDER BY ONOFFTIME ASC ");
    	 return find(sql.toString(), eminfoid);
    }
}
