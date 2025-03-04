/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getUsers());

        // 依赖来源二：依赖注入（ 內建非 Bean 对象（依赖））
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject() == applicationContext);

        /**
         * 依赖查找（错误），思考：为什么上面依赖注入可以，这里依赖查找报错
         * 依赖查找和依赖注入都属于依赖，这个依赖它是不是来自于同一个地方，答案肯定是否定的，从此处可以看的出来，beanFactory 并不是一个
         * 普通的bean,其实是我们的内建依赖，他就是非Spring的Bean。
         * 这里可以得出一个结论：
         * 依赖大致可以分为两类，一个是普通的Bean ,第二种方式是非Bean
         */
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        // 依赖来源三：容器內建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);

        whoIsIoCContainer(userRepository,applicationContext);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {


        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFactory()


        // 这个表达式为什么不会成立
        BeanFactory beanFactory = userRepository.getBeanFactory();
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplicationContext is BeanFactory

    }

}
