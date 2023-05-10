package my.self.spring.beanDefinition;

/**
 * @Author huabin
 * @DateTime 2023-04-16 16:24
 * @Desc 注解形式的配置类的Bean定义类
 */
public class AnnotateGenericBeanDefinition implements AnnotateBeanDefinition{
    private Class clazz;
    private String scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
