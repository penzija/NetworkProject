import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
    @Test
    void check_HTTP_requestURL_startPosition() {
        String result = Utils.findInRequestStartOfURL("""
                GET / HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                 \r\n \
                 """);

        assertThat(result).isEqualTo("/");
    }

    @Test
    void check_HTTP_request_completeURL() {
        String url = Utils.findInRequestCompleteURL("""
                GET /Index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                 \r\n \
                 """);

        assertThat(url).isEqualTo("/Index.html");
    }

    @Test
    void check_HTTP_requestMethod_isHEAD() {
        String result = Utils.parseTypeOfRequestHTTP("""
                HEAD / Index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                 \r\n \
                 """);

        assertThat(result).isEqualTo("HEAD");
    }
}
