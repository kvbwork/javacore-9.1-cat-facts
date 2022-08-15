package kvbdev;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

public class CatFactsServiceImpl {

    protected static final String ALL_FACTS_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected final CloseableHttpClient httpClient;
    protected final ObjectMapper mapper = new ObjectMapper();

    public CatFactsServiceImpl(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Stream<CatFact> getAllFacts() {
        HttpGet request = new HttpGet(ALL_FACTS_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            return mapper.readValue(body, new TypeReference<List<CatFact>>() {
            }).stream();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return Stream.empty();
    }

    public Stream<CatFact> getVotedFacts() {
        return getAllFacts()
                .filter(catFact -> catFact.getUpvotes() != null && catFact.getUpvotes() > 0);
    }
}
