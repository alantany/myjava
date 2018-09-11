package es_test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class es_delete {
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
        RestHighLevelClient client=new RestHighLevelClient(builder);
        DeleteRequest request=new DeleteRequest("people","doc","pM0Yw2UBPbdasrz8Fp1f");
        try{
            DeleteResponse response=client.delete(request);
            String name=response.getIndex();
            String type=response.getType();
            String id=response.getId();
            int status=response.status().getStatus();
            System.out.println(name+" "+type+" "+id+" "+status);
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}