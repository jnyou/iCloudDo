package org.jnyou.gmall.productservice.com.alibaba.cloud.sentinel.feign;

import feign.Contract;
import feign.MethodMetadata;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解决 在2.2.0.RELEASE里有一行注释描述，接口方法名拼写错误，在2.2.2.RELEASE方法已修正了，即方法名发生了改变。
 * 在spring-cloud-alibaba-sentinel中的SentinelContractHolder类，用到了该接口的这个方法(feign2.2.0.RELEASE版本)：
 * @author WGR
 * @create 2020/8/21 -- 0:27
 */
public class SentinelContractHolder implements Contract {
    private final Contract delegate;
 
    /**
     * map key is constructed by ClassFullName + configKey. configKey is constructed by
     * {@link feign.Feign#configKey}
     */
    public final static Map<String, MethodMetadata> METADATA_MAP = new HashMap<>();
 
    public SentinelContractHolder(Contract delegate) {
        this.delegate = delegate;
    }
 
    @Override
    public List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType) {
        List<MethodMetadata> metadatas = delegate.parseAndValidateMetadata(targetType);
        metadatas.forEach(metadata -> METADATA_MAP
                .put(targetType.getName() + metadata.configKey(), metadata));
        return metadatas;
    }
}