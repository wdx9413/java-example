package cn.dox;

import cn.dox.annotation.Autowired;
import cn.dox.annotation.Bean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanMannager {
    private final Map<String, Object> beans = new ConcurrentHashMap<>();
    // 拆装箱（有什么好的解决方法吗）
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


    public Object getBean(String name) throws Exception{
        Object o = beans.get(name);
        if (o == null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }

        // 注解类型递归注入依赖
        if (o instanceof Class) {
            Class c = (Class) o;
            o = c.newInstance();
            inject(c, o);
        }
        return o;
    }
    public void registerBean(String id, Object o) {
        beans.put(id, o);
    }


    /**
     * 解析xml文件
     * @param location
     * @throws Exception
     */
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

    /**
     * 扫描当前包下所有类，加载bean
     * @param app
     * @throws Exception
     */
    public void annotationBeans(Class app) throws Exception{
        String packageName = App.class.getPackage().getName();
        String packagePath = App.class.getClassLoader().getResource("cn/dox/App.class").getFile(); // ..../App.class
        packagePath = packagePath.substring(0, packagePath.lastIndexOf("/"));
        scanBeans(packagePath, packageName);
    }
    private void scanBeans(String packagePath, String packageName) throws Exception {
        File file = new File(packagePath);
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles(f -> f.isDirectory() || f.getName().endsWith(".class"));
        for (File f : files) {
            if (f.isDirectory()) {
                scanBeans(f.getPath(), packageName + "." + f.getName());
                continue;
            }
            String className = f.getName();
            className = className.substring(0, className.length() - 6);
            Class<?> c = Class.forName(packageName + "." + className);
            Annotation annotation = c.getAnnotation(Bean.class);
            if (annotation != null) {
                String id = c.getSimpleName();
                registerBean(id, c);
            }
        }
    }

    private void inject(Class c, Object o) throws Exception{
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Autowired.class) != null) {
                field.setAccessible(true);
                String beanName = field.getType().getSimpleName();
                field.set(o, getBean(beanName));
            }
        }
    }


}
