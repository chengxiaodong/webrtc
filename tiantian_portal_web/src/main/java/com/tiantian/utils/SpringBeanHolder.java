package com.tiantian.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author 晓东
 *
 */
public class SpringBeanHolder implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac=applicationContext;
    }

    public static Object getBean(String beanName){
        return ac.getBean(beanName);
    }
}
