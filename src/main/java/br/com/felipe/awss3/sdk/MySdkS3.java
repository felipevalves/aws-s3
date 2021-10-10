package br.com.felipe.awss3.sdk;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class MySdkS3 {

    @Value("${file.path}")
    private String path;

    @Value("${file.filename}")
    private String filename;

    @Value("${cloud.aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    public void upload() {
        log.info("upload start");

        Random random = new Random();
        int pos = random.nextInt(1,100);


        amazonS3.putObject(bucketName, pos + "-" + filename, new File(path + File.separator + filename));
        log.info("upload end");
    }

    public void deleteAll() {
        log.info("deleteAll start");

        List<String> listFiles = listFiles();
        String[] itemsArray = new String[listFiles.size()];
        itemsArray = listFiles.toArray(itemsArray);

        DeleteObjectsRequest  request = new DeleteObjectsRequest (bucketName)
                .withKeys(itemsArray);

        amazonS3.deleteObjects(request);

        log.info("deleteAll end");
    }

    public void delete(String filename) {
        log.info("delete start");

        amazonS3.deleteObject(bucketName, filename);

        log.info("delete end");
    }

    public void download(String filename) throws IOException {
        S3Object obj = amazonS3.getObject(bucketName, filename);
        S3ObjectInputStream inputStream = obj.getObjectContent();

        FileUtils.copyInputStreamToFile(inputStream, new File(path + File.separator + filename));
    }

    public List<String> listFiles() {
        ObjectListing list = amazonS3.listObjects(bucketName);

        return list.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }
}
