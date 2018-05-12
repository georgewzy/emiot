package com.em.tools.render;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class JYObjectRender extends Render {
    
    private Object object;
    
    public JYObjectRender(Object object) {
        this.object = object;
    }
    
    public static JYObjectRender object(Object object) {
        JYObjectRender render = new JYObjectRender(object);
        return render;
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        ObjectOutputStream oos = null; 
        OutputStream ous;
        try {
            response.setContentType("application/x-java-serialized-object");
            ous = response.getOutputStream();
            oos = new ObjectOutputStream(ous);
            oos.writeObject(object);
            oos.flush();
            oos.close();
            ous.close();
        } catch (Exception e) {
            throw new RenderException(e);
        }
    }
}
