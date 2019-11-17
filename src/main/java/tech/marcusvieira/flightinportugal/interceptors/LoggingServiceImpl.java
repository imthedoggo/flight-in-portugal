package tech.marcusvieira.flightinportugal.interceptors;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void logRequest(HttpServletRequest httpServletRequest) {
        Map<String, String> parameters = buildParametersMap(httpServletRequest);
        StringBuilder request = new StringBuilder();

        request.append("REQUEST ");
        request.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        request.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        if (!parameters.isEmpty()) {
            request.append("parameters=[").append(parameters).append("] ");
        }

        request.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        log.info(request.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Object body) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> requestHeadersMap = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeadersMap.put(key, value);
        }
        return requestHeadersMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> responseHeadersMap = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            responseHeadersMap.put(header, response.getHeader(header));
        }
        return responseHeadersMap;
    }
}
