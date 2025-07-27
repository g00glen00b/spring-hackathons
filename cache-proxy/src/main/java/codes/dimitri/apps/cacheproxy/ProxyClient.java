package codes.dimitri.apps.cacheproxy;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public class ProxyClient {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String PROXY_CACHE_NAME = "proxy";
    private static final String CACHE_HIT_HEADER = "X-Cache";
    private final RestClient restClient;
    private final Cache cache;

    public ProxyClient(RestClient restClient, CacheManager cacheManager) {
        this.restClient = restClient;
        this.cache = Objects.requireNonNull(cacheManager.getCache(PROXY_CACHE_NAME));
    }

    public ResponseEntity<String> proxy(HttpServletRequest request) {
        ProxyCacheKey cacheKey = ProxyCacheKey.create(request);
        ResponseWrapper wrapper = cache.get(cacheKey, ResponseWrapper.class);
        if (wrapper != null) {
            log.info("💾 Retrieved '{}' from cache", cacheKey.requestUrl());
            return cloneWithCacheHit(wrapper.response(), "HIT");
        }
        ResponseEntity<String> response = invoke(cacheKey);
        cache.put(cacheKey, new ResponseWrapper(response));
        log.info("🌎Retrieved '{}' from web", cacheKey.requestUrl());
        return cloneWithCacheHit(response, "MISS");
    }

    public void clear() {
        log.info("🗑️ Cache cleared");
        cache.clear();
    }

    private ResponseEntity<String> cloneWithCacheHit(ResponseEntity<String> response, String cacheHit) {
        return ResponseEntity
            .status(response.getStatusCode())
            .headers(response.getHeaders())
            .header(CACHE_HIT_HEADER, cacheHit)
            .body(response.getBody());
    }

    private ResponseEntity<String> invoke(ProxyCacheKey cacheKey) {
        return restClient
            .method(cacheKey.method())
            .uri(cacheKey.requestUrl())
            .headers(headers -> headers.addAll(cacheKey.headers()))
            .body(cacheKey.body())
            .retrieve()
            .toEntity(String.class);
    }
}
