package kvbdev;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

    public static void main(String[] args) throws Exception {

        try (CloseableHttpClient httpClient = getHttpClient()) {

            CatFactsServiceImpl catFactsService = new CatFactsServiceImpl(httpClient);

            catFactsService.getVotedFacts()
                    .forEach(System.out::println);

        }
    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

}
