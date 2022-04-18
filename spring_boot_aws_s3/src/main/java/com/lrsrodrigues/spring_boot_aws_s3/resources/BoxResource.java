package com.lrsrodrigues.spring_boot_aws_s3.resources;

import com.lrsrodrigues.spring_boot_aws_s3.dtos.BoxDTO;
import com.lrsrodrigues.spring_boot_aws_s3.model.Box;
import com.lrsrodrigues.spring_boot_aws_s3.dtos.ResponseDTO;
import com.lrsrodrigues.spring_boot_aws_s3.services.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/box")
public class BoxResource {

    @Autowired
    private BoxService boxService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createBox(
            @RequestBody BoxDTO boxDTO
    ) {
        ResponseDTO response = boxService.createBox(boxDTO.getUsername());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/{username}")
    public ResponseEntity<Box> sendingFileToBox(
            @PathVariable("username") String username,
            MultipartFile file
    ) {
        Box box = boxService.sendingFileToBox(username, file);
        return ResponseEntity.ok().body(box);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<List<Box>> listBoxFiles(
            @PathVariable("username") String username
    ) {
        List<Box> boxList = boxService.listBoxFiles(username);
        return ResponseEntity.ok().body(boxList);
    }

    @DeleteMapping(value = "/{username}/{filename}")
    public ResponseEntity<List<Box>> listBoxFiles(
            @PathVariable("username") String username,
            @PathVariable("filename") String filename
    ) {
        boxService.deleteFile(username, filename);
        return ResponseEntity.noContent().build();
    }



}
