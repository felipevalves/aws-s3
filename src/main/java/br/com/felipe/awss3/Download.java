package br.com.felipe.awss3;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Download {

    public void startDownload(String produto) throws Exception {
        CloseableHttpClient client = getCloseableHttpClientAuth();

        System.out.println("startDownload: " + produto);

        try (CloseableHttpResponse response = client.execute(new HttpGet("http://172.10.1.15:456/ServicosMili/ipedidos/download/query?id="+produto))){

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new Exception("Não foi possível consultar a imagem do produto " + produto + response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                byte[] image = EntityUtils.toByteArray(entity);
                FileUtils.writeByteArrayToFile(new File("C:\\doc\\aws\\" + produto + ".png"), image);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected CloseableHttpClient getCloseableHttpClientAuth() {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("rep4", "MILI44");

        int timeout = 6;
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, credentials);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000)
                .build();

        return HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(provider)
                .setDefaultRequestConfig(config)
                .build();
    }
}
