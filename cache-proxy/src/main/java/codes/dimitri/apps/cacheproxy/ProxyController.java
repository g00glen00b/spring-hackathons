package codes.dimitri.apps.cacheproxy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnWebApplication
public class ProxyController {
    private final ProxyClient proxyClient;

    public ProxyController(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @DeleteMapping("/cache")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCache() {
        proxyClient.clear();
    }

    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest request) {
        return proxyClient.proxy(request);
    }

}
