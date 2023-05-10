package my.self.spring.beanDefinition;

/**
 * @Author huabin
 * @DateTime 2023-05-06 12:27
 * @Desc BeanDefinition注册器
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition);
}
