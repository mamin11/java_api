package com.example.rms.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        System.out.println("Metadata: " + optionalMetadata);

        System.out.println("end here");

        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file", e);
        }
    }
}
