package my.self.test;

import my.self.spring.applicationContext.AnnotationConfigApplicationContext;
import my.self.test.config.AppConfig;

/**
 * @Author huabin
 * @DateTime 2023-04-16 15:27
 * @Desc
 */
public class SpringTest {

    public static void main(String[] args) {
        // 创建ApplicationContext
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // 调用getBean
    }

}
