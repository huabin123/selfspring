package my.self.spring.beanDefinition;

/**
 * @Author huabin
 * @DateTime 2023-05-06 12:30
 * @Desc
 */
public class BeanDefinitionReaderUtils {
    public static void registerBeanDefinition(AnnotateBeanDefinition beanDefinition,
                                              BeanDefinitionRegistry registry) {
        String beanName = ((AnnotateGenericBeanDefinition)beanDefinition).getClazz().getSimpleName();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
