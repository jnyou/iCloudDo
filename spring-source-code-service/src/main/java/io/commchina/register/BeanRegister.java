package io.commchina.register;

import io.commchina.bean.Girl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p>
 * BeanRegister
 *
 * @version 1.0.Release
 * @author: youjiannan
 * @description BeanRegister
 **/
@Slf4j
public class BeanRegister implements ImportBeanDefinitionRegistrar {

    /**
     * 往容器中加入bean对象
     * @param annotationMetadata
     * @param registry
     * @return:
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        //判断这个是否在容器内
        boolean isContainsBeanDefinition = registry.containsBeanDefinition("io.commchina.bean.Student");
        if(isContainsBeanDefinition){
            //如果在容器内，那么就把Girl类创建出来也加入到容器中
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Girl.class);
//            registry.removeBeanDefinition("");
            registry.registerBeanDefinition("girl", rootBeanDefinition);
        }
    }
}