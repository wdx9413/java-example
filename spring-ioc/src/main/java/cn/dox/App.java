package cn.dox;


import cn.dox.xml_obj.Dog;
import cn.dox.xml_obj.Person;

public class App {
    public static void main( String[] args ) {
        try {
            BeanMannager beanMannager = new BeanMannager();

            // 根据配置文件创建bean
            String location = App.class.getClassLoader().getResource("application.xml").getFile();
            beanMannager.loadBeans(location);
            Dog dog = (Dog) beanMannager.getBean("dog");
            System.out.println(dog);
            Person person = (Person) beanMannager.getBean("person");
            System.out.println(person);

            // 扫描全局包创建bean
            beanMannager.annotationBeans(App.class);


            System.out.println(beanMannager.getBean("Building"));
            System.out.println(beanMannager.getBean("Room"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
