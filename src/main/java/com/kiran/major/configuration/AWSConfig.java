package com.kiran.major.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfig {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Value("${aws.region}")
    private Region regionName;

    @Bean
    public S3Client amazonS3(){

        AwsBasicCredentials credentials= AwsBasicCredentials.create(accessKey,secretKey);

        return S3Client.builder()
                .region(regionName)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
