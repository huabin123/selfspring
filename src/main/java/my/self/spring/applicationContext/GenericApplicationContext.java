package my.self.spring.applicationContext;

import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;
import my.self.spring.beanFactory.DefaultListableBeanFactory;

/**
 * @Author huabin
 * @DateTime 2023-05-10 09:43
 * @Desc
 */
public class GenericApplicationContext implements BeanDefinitionRegistry {

    private DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public void refresh(){
        // 获取bean工厂
        DefaultListableBeanFactory beanFactory = obtainBeanFactory();
        // 把appConfig下的所有bean(UserService和UserService1)进行扫描，注册到bean工厂
        invokeBeanFactoryPostProcessors(beanFactory);
        // 初始化BeanDefinition所代表的单例bean，放到单例bean的容器（缓存）里
        finishBeanFactoryInitialization(beanFactory);
    }

    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
        this.beanFactory.preInstantiateSingleton();
    }

    private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {
        beanFactory.doScan();
    }

    private DefaultListableBeanFactory obtainBeanFactory() {
        return this.beanFactory;
    }

    public Object getBean(String beanName) {
        return this.beanFactory.getBean(beanName);
    }
}
