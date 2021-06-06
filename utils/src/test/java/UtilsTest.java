import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
    @Test
    void urlInputGET_simple() {
        String url = Utils.parseUrl("""
                GET / HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                 \r\n \
                 """);

        assertThat(url).isEqualTo("/");
    }

    @Test
    void filePathGET_returnsURL() {
        String url = Utils.parseUrl("""
                GET / Index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                 \r\n \
                 """);

        assertThat(url).isEqualTo("/index.html");
    }


}


}
