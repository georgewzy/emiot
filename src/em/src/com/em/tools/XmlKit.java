package com.em.tools;

import java.util.*;
import java.io.*;

import org.dom4j.*;
import org.dom4j.io.*;

import com.jfinal.kit.PathKit;

/**
 * 
 * @author K'naan
 * 
 * XML 工具类
 *
 */
public class XmlKit {

    private static String ROOT_PATH   = PathKit.getWebRootPath() + "\\WEB-INF\\basedata\\";
    
    private static String ITEM_NAME   = "item";
    
    private static String ATTR_KEY    = "key";
    
    private static String ATTR_VALUE  = "value";
    
    public XmlKit() {}
    
    // 读取XML
    public static Document readFile(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(ROOT_PATH + filename); // 加载xml文件
        return doc;
    }
    
    // 取得根节点
    public static Element getRoot(String filename) throws DocumentException {
        Document doc = readFile(filename);
        return doc.getRootElement();
    }
    
    // 取得指定ID节点
    public static Element getByKey(String filename, String key) throws DocumentException {
        Document doc  = readFile(filename);
        Element model = doc.elementByID(key);
        return model;
    }
    
    public static String getStr(String filename, String key) throws DocumentException {
        if (!filename.endsWith(".xml")) filename += ".xml";
        Element model = getByKey(filename, key);
        Element item  = model.element(ITEM_NAME);
        String txt    = item.attributeValue(ATTR_VALUE);
        return txt;
    }
    
    public static List<String> listStr(String filename, String key) {
        if (!filename.endsWith(".xml")) filename += ".xml";
        List<String> list = null;
        try {
            Element model = getByKey(filename, key);
            List<Element> notes = model.elements(ITEM_NAME);
            list = new ArrayList<String>();
            for (Element n : notes) {
                list.add(n.attributeValue(ATTR_VALUE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public static Map<String, Object> getMap(String filename, String key) {
        if (!filename.endsWith(".xml")) filename += ".xml";
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Element model = getByKey(filename, key);
            List<Element> notes = model.elements(ITEM_NAME);
            for (Element n : notes) {
                map.put(n.attributeValue(ATTR_KEY), n.attributeValue(ATTR_VALUE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }
    
    public static void Test() {
        String fileName = System.getProperty("user.dir") + "\\demo.xml"; //当前路径下的demo.xml

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(fileName); //加载xml文件

            List peoples = doc.selectNodes("//*[@name]"); //选择所有具有name属性的节点(即demo.xml中的所有card节点)
            for (Iterator iter = peoples.iterator(); iter.hasNext(); ) {
                Element card = (Element) iter.next();
                //System.out.println(node.asXML());
                //System.out.println("---------------------------------------");
                //System.out.println(node.attributeCount());
                List attrList = card.attributes();
                //输出每个card的所有属性
                for (Iterator attr = attrList.iterator(); attr.hasNext(); ) {
                    Attribute a = (Attribute) attr.next();
                    System.out.println(a.getName() + "=" + a.getValue());

                }
                System.out.println(
                        "----------------------------------------------------");
            }

            Element zhangsan = (Element) doc.selectSingleNode("//card[@id='2']"); //查找“id属性”=2的card元素
            System.out.println("张三的名称：" + zhangsan.attribute("name").getValue()); //输出zhangsan的name属性

            Node addrFamily = zhangsan.selectSingleNode("./address/item[2]"); //选择zhangsan元素下的address节点下的第2个item子节点
            System.out.println("张三的单位地址：" + addrFamily.getStringValue()); //输出cdata内容

            System.out.println(
                    "----------------------------------------------------");
            //为zhangsan下增加二个节点
            zhangsan.addElement("email").addAttribute("type",
                    "工作").addText("work@some-domain.com");
            zhangsan.addElement("email").addAttribute("type",
                    "私人").addCDATA("private@some-domain.com"); //设置CDATA内容

            System.out.println(zhangsan.asXML()); //打印zhangsan节点的xml内容(调试用)
            System.out.println(
                    "----------------------------------------------------");

            //将上述改动保存到文件demo2.xml
            FileWriter fileWriter = new FileWriter(System.getProperty(
                    "user.dir") + "\\demo2.xml");

            OutputFormat format = OutputFormat.createPrettyPrint(); //设置美观的缩进格式，便于阅读
            //format = OutputFormat.createCompactFormat();//设置紧凑格式（消除多余空格），便于下载
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.setWriter(fileWriter);
            writer.write(doc);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}