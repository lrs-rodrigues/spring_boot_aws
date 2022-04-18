package com.lrsrodrigues.spring_boot_aws_rds.services;

import com.lrsrodrigues.spring_boot_aws_rds.dtos.UserDTO;
import com.lrsrodrigues.spring_boot_aws_rds.model.User;
import com.lrsrodrigues.spring_boot_aws_rds.repositories.UserRepository;
import com.lrsrodrigues.spring_boot_aws_rds.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return findUserInRepository(id);
    }

    @Transactional
    public User insert(UserDTO obj) {
        User user = fromData(null, obj);
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
