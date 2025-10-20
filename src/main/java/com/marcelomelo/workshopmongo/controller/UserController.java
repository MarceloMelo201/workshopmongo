package com.marcelomelo.workshopmongo.controller;

import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.user.UserCreateDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserResponseDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserUpdateDTO;
import com.marcelomelo.workshopmongo.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> list = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<UserResponseDTO> findById(@PathVariable ObjectId id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable ObjectId id,
            @RequestBody @Valid UserUpdateDTO dto) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
