package codes.dimitri.apps.cacheproxy;

import org.springframework.http.ResponseEntity;

public record ResponseWrapper(ResponseEntity<String> response) {
}
