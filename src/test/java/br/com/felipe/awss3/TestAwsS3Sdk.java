package br.com.felipe.awss3;

import br.com.felipe.awss3.sdk.MySdkS3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestAwsS3Sdk {

    @Autowired
    private MySdkS3 s3;

    @Test
    void test_upload_file() {
        s3.upload();

        Assertions.assertTrue(true);
    }

    @Test
    void test_delete_all() {
        s3.deleteAll();

        Assertions.assertTrue(true);
    }

    @Test
    void test_download_file() throws IOException {
        List<String> list = s3.listFiles();

        for (String file : list) {
            s3.download(file);
        }

        Assertions.assertTrue(true);
    }

    @Test
    void test_list_files() {
        List<String> list = s3.listFiles();

        Assertions.assertNotNull(list);
        System.out.println(list);
    }
}