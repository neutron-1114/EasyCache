package com.jinxlbj.easycache.aop;

import com.jinxlbj.easycache.annotation.EasyCacheFunc;
import com.jinxlbj.easycache.core.EasyCache;
import com.jinxlbj.easycache.core.EasyCacheContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Aspect
@Component
public class EasyCacheAspect {

    private static final ConcurrentHashMap<String, EasyCacheFunc> ANNOTATION_CACHE = new ConcurrentHashMap<>(32);

    public EasyCacheAspect() {
    }

    @Around("@annotation(com.jinxlbj.easycache.annotation.EasyCacheFunc)")
    public Object cacheable(ProceedingJoinPoint pjp) throws Throwable {
        EasyCacheFunc easyCacheFunc = getAnnotation(pjp);
        String namespace = easyCacheFunc.namespace();
        EasyCache easyCache = EasyCacheContext.getEasyCache(namespace);
        if (easyCache == null) {
            return pjp.proceed();
        }
        String key = buildKey(pjp);
        int ttl = easyCacheFunc.ttl();
        switch (easyCacheFunc.type()) {
            case 0:
                Optional<Object> cache = easyCache.get(key);
                if (cache.isPresent()) {
                    return cache.get();
                }
                Object result = pjp.proceed();
                setCache(easyCache, key, result, ttl);
                return result;
            case 1:
                result = pjp.proceed();
                setCache(easyCache, key, result, ttl);
                return result;
            case 2:
                easyCache.expire(key);
                return pjp.proceed();
            default:
                return pjp.proceed();
        }
    }

    private void setCache(EasyCache easyCache, String key, Object value, int ttl) {
        easyCache.set(key, value, ttl);
    }

    private String buildKey(ProceedingJoinPoint pjp) {
        StringBuilder sb = new StringBuilder();
        String funcName = pjp.getSignature().getName();
        sb.append(funcName);
        sb.append(":");
        sb.append(Arrays.stream(pjp.getArgs()).map(Object::toString).collect(Collectors.joining("_")));
        return sb.toString();
    }

    private EasyCacheFunc getAnnotation(ProceedingJoinPoint pjp) {
        return ANNOTATION_CACHE.computeIfAbsent(pjp.toLongString(), (k) -> {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            return AnnotationUtils.getAnnotation(signature.getMethod(), EasyCacheFunc.class);
        });
    }

}
