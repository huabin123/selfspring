package my.self.test.bean;

import my.self.spring.annotation.Scope;
import my.self.spring.annotation.Service;

/**
 * @Author huabin
 * @DateTime 2023-04-16 15:30
 * @Desc
 */

@Service("UserService1")
@Scope("singleton")
public class UserService1 {
}
