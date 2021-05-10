package com.example.rms.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class Storage {
    private final AmazonS3 s3;

    @Autowired
    public Storage(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
//                    metadata.addUserMetadata(key, value);
                    if (key == "Content-Length") {
                        metadata.setContentLength(Long.parseLong(value));
                    }
                    if (key == "Content-Type") {
                        metadata.setContentType(value);
                    }
                }
            }
        });

//        System.out.println("Metadata: " + optionalMetadata);
//
//        System.out.println("end here");

        try {
            s3.putObject(new PutObjectRequest(path, fileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
//            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file", e);
        }
    }

    public S3ObjectInputStream download(String path, String key) {
        try {
            S3Object object =  s3.getObject(path, key);
            return object.getObjectContent();
//            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to get requested file from storage", e);
        }
    }

    public void delete(String path, String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(path, key);
            System.out.println("Path: "+path);
            System.out.println("Key: " + key);
            s3.deleteObject(deleteObjectRequest);
        } catch (AmazonServiceException e) {
//            throw new IllegalStateException("failed to delete file", e);
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

}
