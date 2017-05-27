package example.wxx.com.essayjoke.model;

/**
 * 作者：wengxingxia
 * 时间：2017/5/26 0026 10:22
 */

public class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
