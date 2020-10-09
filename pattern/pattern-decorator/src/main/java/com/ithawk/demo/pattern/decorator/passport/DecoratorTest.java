package com.ithawk.demo.pattern.decorator.passport;

import com.ithawk.demo.pattern.decorator.passport.old.SigninService;
import com.ithawk.demo.pattern.decorator.passport.upgrade.ISiginForThirdService;
import com.ithawk.demo.pattern.decorator.passport.upgrade.SiginForThirdService;
//import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
//import org.springframework.http.server.reactive.HttpHeadResponseDecorator;

/**
 *
 */
public class DecoratorTest {

    public static void main(String[] args) {

        //满足一个is-a
        ISiginForThirdService siginForThirdService = new SiginForThirdService(new SigninService());
        siginForThirdService.loginForQQ("sdfasfdasfsf");

    }


}
