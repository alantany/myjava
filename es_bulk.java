package es_test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class es_bulk {
        public static void main( String[] args ) //throws Exception
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
                RestHighLevelClient client=new RestHighLevelClient(builder);
                BulkRequest bulkRequest=new BulkRequest();
                bulkRequest.add(new IndexRequest("test","doc","1").source(XContentType.JSON,"f1","foo"));
                bulkRequest.add(new IndexRequest("test","doc","2").source(XContentType.JSON,"f2","bar"));
                bulkRequest.add(new IndexRequest("test","doc","3").source(XContentType.JSON,"f3","baz"));
                try{
                    BulkResponse responses=client.bulk(bulkRequest);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
}
