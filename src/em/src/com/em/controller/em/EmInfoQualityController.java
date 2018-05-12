package com.em.controller.em;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmInfo;
import com.em.model.em.EmInfoQuality;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey="/sys/em/quality", viewPath="/pages/em/quality")
public class EmInfoQualityController extends BaseController {

    @Override
    public void index() {
        // TODO Auto-generated method stub
        String eminfoid = getPara();
        EmInfo em       = EmInfo.dao.getBy(eminfoid);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        EmInfoQuality quality = EmInfoQuality.dao.getBy(eminfoid);
        setAttr("em", em);
        setAttr("quality", quality);
        render("edit.jsp");
    }
    
    public void save() {
        EmInfoQuality quality = getModel(EmInfoQuality.class, "quality");
        if (Util.isEmptyString(quality.get("id"))) {
            quality.set("id", Util.getUUID()).save();
        } else {
            quality.update();
        }
        render(BjuiRender.success());
    }
    
    public void del() {
        
    }
    
}
