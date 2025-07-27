package codes.dimitri.apps.cacheproxy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public record ProxyCacheKey(
    HttpMethod method,
    String requestUrl,
    String body,
    HttpHeaders headers
) {
    private static final List<String> FILTERED_HEADERS = List.of("host", "accept-encoding");

    public static ProxyCacheKey create(HttpServletRequest request) {
        return new ProxyCacheKey(
            HttpMethod.valueOf(request.getMethod()),
            request.getRequestURI(),
            getRequestBody(request),
            getHeaders(request)
        );
    }

    private static String getRequestBody(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            return null;
        }
    }

    private static HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!FILTERED_HEADERS.contains(headerName.toLowerCase(Locale.ROOT))) {
                headers.add(headerName, request.getHeader(headerName));
            }
        }
        return headers;
    }
}
