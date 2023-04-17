package my.self.spring.beanDefinition;

import my.self.spring.annotation.Scope;

/**
 * @Author huabin
 * @DateTime 2023-04-16 16:07
 * @Desc
 */
public class AnnotateBeanDefinitionReader {

    // 注册路径扫描bean（AppConfig）到bean工厂里
    public void register(Class<?> componentClass) {
        registerBean(componentClass);
    }

    private void registerBean(Class<?> componentClass) {
        doRegisterBean(componentClass);
    }

    // 实际注册bean的方法
    private void doRegisterBean(Class<?> componentClass) {
        // 把appConfig读成一个BeanDefinition
        AnnotateGenericBeanDefinition beanDefinition =
                new AnnotateGenericBeanDefinition();

        beanDefinition.setClazz(componentClass);

        if (componentClass.isAnnotationPresent(Scope.class)) {
            String scope = componentClass.getAnnotation(Scope.class).value();
            beanDefinition.setScope(scope);
        } else {
            beanDefinition.setScope("singleton");
        }


        // BeanDefinition创建完成后就可以给BeanFactory进行bean注册了
    }
}
