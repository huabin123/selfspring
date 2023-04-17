package my.self.test.config;

import my.self.spring.annotation.ComponentScan;
import my.self.spring.annotation.Scope;

/**
 * @Author huabin
 * @DateTime 2023-04-16 15:30
 * @Desc
 */
@ComponentScan("my.self.test.bean")
@Scope
public class AppConfig {
}
