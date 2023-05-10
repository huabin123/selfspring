package my.self.spring.applicationContext;

import my.self.spring.beanDefinition.AnnotateBeanDefinitionReader;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

/**
 * @Author huabin
 * @DateTime 2023-04-16 15:40
 * @Desc
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements BeanDefinitionRegistry {

    private AnnotateBeanDefinitionReader reader;
//    private DefaultListableBeanFactory factory;

    // 如果有人调用这个无参构造，必须先调用父类的无参构造
    public AnnotationConfigApplicationContext() {
        this.reader = new AnnotateBeanDefinitionReader(this);
//        this.factory = new DefaultListableBeanFactory();
    }

    public AnnotationConfigApplicationContext(Class<?> componentClass) {
        // 1、先读取传入的componentClass中的信息
        // 问题1：怎么读？ 可以定义一个阅读器 专门读取. AnnotateBeanDefinitionReader
        this();  // this调用的是当前类的无参构造，当前无参调用之前先调用父类的无参（bean工厂在这里被创建）
        // 2、先把配置类注册到bea工厂里
        register(componentClass);

        // 3、扫描这个路径，然后提出出这个路径下的所有bean，然后注册到bean工厂
        refresh();
    }

    private void register(Class<?> componentClass) {
        this.reader.register(componentClass);
    }

//    @Override
//    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
//        this.factory.registerBeanDefinition(beanName, beanDefinition);
//    }
}
