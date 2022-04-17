package com.lrsrodrigues.spring_boot_aws.services;

import com.lrsrodrigues.spring_boot_aws.dtos.UserDTO;
import com.lrsrodrigues.spring_boot_aws.model.User;
import com.lrsrodrigues.spring_boot_aws.repositories.UserRepository;
import com.lrsrodrigues.spring_boot_aws.services.exceptions.ResourceNotFoundException;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${cloud.aws.sqs.emailQueue}")
    private String emailQueue;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return findUserInRepository(id);
    }

    @Transactional
    public User insert(UserDTO obj) {
        User user = fromData(null, obj);

        messagingTemplate.convertAndSend(emailQueue, user);
        log.info("Message sent {}", user.getEmail());

        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, UserDTO obj) {
        User user = fromData(id, obj);
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = findUserInRepository(id);
        userRepository.deleteById(user.getId());
    }

    private User fromData(Long id, UserDTO obj) {
        User user;

        if (id == null) {
            user = new User();
        } else {
            user = findUserInRepository(id);
        }

        user.setName(obj.getName());
        user.setEmail(obj.getEmail());

        return user;
    }

    private User findUserInRepository(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
