package io.jnyou.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author jnyou
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "io.jnyou.service.impl")
public class JetCacheConfig {


}