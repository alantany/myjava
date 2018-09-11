package es_test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.HashMap;
import java.util.Map;

public class es_index {
    public static void main(String[] args) //throws Exception
    {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "oracle"));
        RestClientBuilder builder = RestClient.builder(new HttpHost("100.73.18.55", 9201),
                new HttpHost("100.73.18.56", 9201)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        Map<String, Object> jsonmap = new HashMap<>();
        jsonmap.put("name", "mike");
        jsonmap.put("age", "10");
        String message;
        IndexRequest indexRequest = new IndexRequest("people", "doc").source(jsonmap);
        try {
            IndexResponse indexResponse = client.index(indexRequest);
            message=indexResponse.getResult().toString();
            System.out.println(message);
            client.close();

        }catch(Exception e) {
        e.printStackTrace();
    }
    }
}
