package com.kiran.major.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket.product}")
    private  String bucketName;

    public Boolean uploadFileToS3(MultipartFile file) throws IOException {

        String fileName=file.getOriginalFilename();
        InputStream inputStream=file.getInputStream();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentLength(file.getSize())
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            return putObjectResponse != null;
        } catch (S3Exception e) {
            // Log the exception and handle the error
            System.err.println("Error occurred while uploading file to S3: " + e.getMessage());
            return false;
        }

    }


}
