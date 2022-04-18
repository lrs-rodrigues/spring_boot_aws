package com.lrsrodrigues.spring_boot_aws_rds.resources;

import com.lrsrodrigues.spring_boot_aws_rds.dtos.UserDTO;
import com.lrsrodrigues.spring_boot_aws_rds.model.User;
import com.lrsrodrigues.spring_boot_aws_rds.services.UserService;
import com.lrsrodrigues.spring_boot_aws_rds.utils.URIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody UserDTO userDTORequest) {
        User user = userService.insert(userDTORequest);
        return ResponseEntity.created(URIUtil.uri(user.getId())).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> insert(
            @PathVariable("id") Long id,
            @RequestBody UserDTO userDTORequest
    ) {
        User user = userService.update(id, userDTORequest);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
