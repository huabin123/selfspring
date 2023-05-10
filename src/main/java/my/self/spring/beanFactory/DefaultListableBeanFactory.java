package my.self.spring.beanFactory;

import my.self.spring.annotation.ComponentScan;
import my.self.spring.annotation.Scope;
import my.self.spring.annotation.Service;
import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.AnnotateGenericBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huabin
 * @DateTime 2023-05-08 12:57
 * @Desc
 */
public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, AnnotateBeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    public void doScan() {

        for (String beanName : beanDefinitionMap.keySet()) {
            AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition)beanDefinitionMap.get(beanName);
            if (bd.getClazz().isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan)bd.getClazz().getAnnotation(ComponentScan.class);
                String basePackage = componentScan.value();
                URL resource = this.getClass().getClassLoader().getResource(basePackage.replace(".", "/"));
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        try {
                            Class<?> clazz = this.getClass().getClassLoader().loadClass(basePackage.concat(".").concat(f.getName().split("\\.")[0]));
                            if (clazz.isAnnotationPresent(Service.class)) {
                                String name = ((Service) clazz.getAnnotation(Service.class)).value();
                                AnnotateGenericBeanDefinition abd = new AnnotateGenericBeanDefinition();
                                abd.setClazz(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    abd.setScope(((Scope)clazz.getAnnotation(Scope.class)).value());
                                } else {
                                    abd.setScope("singleton");
                                }
                                beanDefinitionMap.put(name, abd);
                                // 注意，这里的beanDefinitionMap除了包含我们扫描路径下我们自己写的bean，还包含了配置类appconfig，所以这里需要一个list记录我们真正需要初始化的bean
                                beanDefinitionNames.add(name);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

    }

    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    private Object doGetBean(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean != null) {
            return bean;
        }
        // 需要根据BeanDefinition创建bean
        AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition) beanDefinitionMap.get(beanName);
        Object cBean = createBean(beanName, bd);
        if (bd.getScope().equals("singleton")) {
            singletonObjects.put(beanName, cBean);
        }
        return cBean;
    }

    private Object createBean(String beanName, AnnotateGenericBeanDefinition bd) {
        return doCreateBean(beanName, bd);
    }

    private Object doCreateBean(String beanName, AnnotateGenericBeanDefinition bd) {
        try {
            return bd.getClazz().getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void preInstantiateSingleton() {
        // 初始化我们定义的bean
        // 问题：为什么不直接用beanDefinitionNames？
        // 因为bean的扫描是出于并发环境中的，如果直接对beanDefinitionNames进行遍历，且遍历途中有其他线程进行了beanDefinitionNames.add操作就会报ModifyIn（遍历中修改）异常
        // 所以这里需要对beanDefinitionNames进行一次遍历
        ArrayList<String> beanNames = new ArrayList<>(beanDefinitionNames);
        for (String beanName : beanNames) {
            AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition) beanDefinitionMap.get(beanName);
            if (bd.getScope().equals("singleton")) {
                // 创建单例对象，然后把这个单例对象保存到我们的单例池（缓存池）里
                // getBean里包含了创建对象，然后放到SingletonObject里
                getBean(beanName);
            }

        }
    }
}
