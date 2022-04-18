package com.lrsrodrigues.spring_boot_aws.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lrsrodrigues.spring_boot_aws.model.User;
import com.lrsrodrigues.spring_boot_aws.repositories.UserRepository;
import com.lrsrodrigues.spring_boot_aws.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
public class AvatarService {

    private static final Logger log = LoggerFactory.getLogger(AvatarService.class);

    @Value("${cloud.aws.s3.avatarBucket}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private UserRepository userRepository;

    public boolean avatarUpload(Long id, MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        String filename = user.getAvatarId();

        if (filename == null) {
            filename = generateFilename(file);
        }

        boolean avatarSent = uploadingAvatarToS3(filename, file);

        if (avatarSent) updateAvatarIdUser(user, filename);
        return avatarSent;
    }
    private String generateFilename(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    private boolean uploadingAvatarToS3(String filename, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(BUCKET_NAME, filename, file.getInputStream(), metadata);
            log.info("Avatar uploaded successfully!");
            return true;
        } catch (SdkClientException | IOException e) {
            log.error("Error uploading avatar -> " + e.getMessage());
            return false;
        }
    }

    @Transactional
    private void updateAvatarIdUser(User user, String filename) {
        user.setAvatarId(filename);

        String avatarUrl = getAvatarURL(filename);
        user.setAvatarUrl(avatarUrl);

        userRepository.save(user);
    }

    private String getAvatarURL(String filename) {
        return amazonS3.getUrl(BUCKET_NAME, filename).toString();
    }

}
