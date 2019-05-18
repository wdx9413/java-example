package cn.dox;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanMannager {
    private final Map<String, Object> beans = new ConcurrentHashMap<>();
    private static final Map<Class, Class> basicCast = Collections.unmodifiableMap(new HashMap<Class, Class>(){
        {
            put(byte.class, Byte.class);
            put(short.class, Short.class);
            put(int.class, Integer.class);
            put(long.class, Long.class);
            put(char.class, Character.class);
            put(boolean.class, Boolean.class);
            put(float.class, Float.class);
            put(double.class, Double.class);
        }
    });

    public Object getBean(String name) {
        Object o = beans.get(name);
        if (o == null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }
        return o;
    }

    public void loadBeans(String location) throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(location);
        Element root = document.getRootElement();
        for (Element element : root.elements()) {
            String className = element.attributeValue("class");
            String id = element.attributeValue("id");
            Class c = Class.forName(className);
            Object object = c.newInstance();
            for (Element e : element.elements()) {
                if (e.getName() == "property") {
                    String propertyName = e.attributeValue("name");
                    String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                    Field field = c.getDeclaredField(propertyName);
                    Class parameterType = field.getType();
                    Method method = c.getMethod(setterName, field.getType());
                    String propertyValue = e.attributeValue("value");
                    Object setValue = null;
                    // 基本数据类型（或包装类）与String
                    if (propertyValue != null) {
                        if (parameterType == String.class) {
                            setValue = propertyValue;
                        } else if (basicCast.containsValue(parameterType)) {
                            System.out.println(parameterType.getName());
                            // string to box type
                            Method convertMethod = parameterType.getMethod("valueOf", String.class);
                            setValue = convertMethod.invoke(null, propertyValue);
                        } else if (basicCast.containsKey(parameterType)) {
                            Class boxType = basicCast.get(parameterType);
                            Method convertMethod = boxType.getMethod("valueOf", String.class);
                            setValue = convertMethod.invoke(null, propertyValue);
                        } else {
                            throw new IllegalArgumentException("please use ref property");
                        }
                    } else {
                        String propertyRef = e.attributeValue("ref");
                        setValue = beans.get(propertyRef);
                    }
                    method.invoke(object, setValue);
                }
            }
            registerBean(id, object);
        }
    }

    public void registerBean(String id, Object o) {
        beans.put(id, o);
    }
}
