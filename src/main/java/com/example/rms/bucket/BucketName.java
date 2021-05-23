package com.example.rms.bucket;

public enum BucketName {
    PROFILE_IMAGE("vue-app-s3");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
