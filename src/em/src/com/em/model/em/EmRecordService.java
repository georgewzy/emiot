package com.em.model.em;

import java.util.ArrayList;
import java.util.List;

import com.em.model.BaseModel;
import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="em_record_service")
public class EmRecordService extends BaseModel<EmRecordService> {
    
    public static final EmRecordService dao = new EmRecordService();
    
    public Page<EmRecordService> paging(EmRecordService rs, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder();
        sqlExceptSelect
            .append("FROM em_record_service rs ")
            .append("LEFT JOIN em_type type ON rs.emtypeid = type.id ")
            .append("LEFT JOIN em_servicetype stype ON rs.servicetypeid = stype.id ")
            .append("LEFT JOIN em_info em ON em.id = rs.eminfoid ")
            .append("WHERE 1 = 1 ");
        
        if (!Util.isEmptyString(rs.get("eminfoid"))) {
            paras.add(rs.get("eminfoid"));
            sqlExceptSelect.append(" AND rs.eminfoid = ? ");
        }
        if (!Util.isEmptyString(rs.get("emtypeid"))) {
            paras.add(rs.get("emtypeid"));
            sqlExceptSelect.append(" AND rs.emtypeid = ? ");
        }
        if (!Util.isEmptyString(rs.get("areaid"))) {
            paras.add(rs.get("areaid"));
            sqlExceptSelect.append(" AND em.areaid = ? ");
        }
        if (!Util.isEmptyString(rs.get("servicetypeid"))) {
            paras.add(rs.get("servicetypeid"));
            sqlExceptSelect.append(" AND rs.servicetypeid = ? ");
        }
        if (!Util.isEmptyString(rs.get("serviceuser"))) {
            paras.add("%"+ rs.get("serviceuser") +"%");
            sqlExceptSelect.append(" AND rs.serviceuser LIKE ? ");
        }
        if (!Util.isEmptyString(rs.get("servicetime"))) {
            paras.add("%"+ rs.get("servicetime") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), servicetime, 21) LIKE ? ");
        }
        if (!Util.isEmptyString(rs.get("beizhu"))) {
            paras.add("%"+ rs.get("beizhu") +"%");
            sqlExceptSelect.append(" AND rs.beizhu LIKE ? ");
        }
        if (!Util.isEmptyString(rs.get("code"))) {
            paras.add("%"+ rs.get("code") +"%");
            sqlExceptSelect.append(" AND em.code LIKE ? ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "rs.servicetime DESC ";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        StringBuilder select = new StringBuilder()
            .append("SELECT rs.*, type.name AS typename, stype.name AS servicename, em.areaid, em.code");
        Page<EmRecordService> page = dao.paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
}
