package com.lrsrodrigues.spring_boot_aws_s3.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lrsrodrigues.spring_boot_aws_s3.model.Box;
import com.lrsrodrigues.spring_boot_aws_s3.dtos.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoxService {

    @Value("${cloud.aws.s3.baseBucket}")
    private String baseBucket;

    @Autowired
    private AmazonS3 amazonS3;

    public ResponseDTO createBox(String username) {
        String bucketName = bucketName(username);
        amazonS3.createBucket(bucketName);

        return ResponseDTO.builder()
            .message("Box created successfully")
            .boxName(bucketName)
            .build();
    }

    public Box sendingFileToBox(String username, MultipartFile file) {
        try {
            String bucketName = bucketName(username);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), metadata);

            return new Box(file.getOriginalFilename(), null, file.getSize(), getAvatarURL(bucketName, file.getOriginalFilename()));
        } catch (SdkClientException | IOException e) {
            log.error("Error creating the box -> " + e.getMessage());
            return null;
        }
    }

    public List<Box> listBoxFiles(String username) {
        String bucketName = bucketName(username);

        if (!amazonS3.doesBucketExistV2(bucketName)) return null;

        return amazonS3.listObjectsV2(bucketName)
                .getObjectSummaries().stream()
                .map(file -> new Box(file.getKey(), file.getETag(), file.getSize(), getAvatarURL(bucketName, file.getKey())))
                .collect(Collectors.toList());
    }

    public void deleteFile(String username, String filename) {
        String bucketName = bucketName(username);
        if (!amazonS3.doesBucketExistV2(bucketName)) return;

        amazonS3.deleteObject(bucketName, filename);
    }

    private String bucketName(String username) {
        return baseBucket + "-" + username;
    }

    private String getAvatarURL(String bucketname, String filename) {
        return amazonS3.getUrl(bucketname, filename).toString();
    }
}
