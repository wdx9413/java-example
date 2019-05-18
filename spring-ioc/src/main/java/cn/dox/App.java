package cn.dox;


public class App {
    public static void main( String[] args ) {
        try {
            String location = App.class.getClassLoader().getResource("application.xml").getFile();
            BeanMannager beanMannager = new BeanMannager();
            beanMannager.loadBeans(location);
            Dog dog = (Dog) beanMannager.getBean("dog");
            System.out.println(dog);
            Person person = (Person) beanMannager.getBean("person");
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
