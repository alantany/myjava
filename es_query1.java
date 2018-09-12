package es_test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class es_query1 {
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
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        SearchRequest request = new SearchRequest("antmq_broker_consumer-2018-09-05");
        request.source(searchSourceBuilder);
        try {
            SearchResponse response = client.search(request);
            SearchHits hits = response.getHits();
            SearchHit[] result = hits.getHits();
            for (SearchHit searchHit : result) {
                Map<String, Object> json = searchHit.getSourceAsMap();
                //                String s=searchHit.getSourceAsString();
                Set keyset=json.keySet();
                Iterator<String> iterator=keyset.iterator();
                while (iterator.hasNext()){
                    String key=iterator.next();
                    Object value=json.get(key);
                    System.out.println(key+" "+value.toString());
                }
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
