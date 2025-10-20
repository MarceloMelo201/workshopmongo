package com.marcelomelo.workshopmongo.controller;

import com.marcelomelo.workshopmongo.dtos.post.PostCreateDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostResponseDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostUpdateDTO;
import com.marcelomelo.workshopmongo.service.PostService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestBody @Valid PostCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(dto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> findAll() {
        List<PostResponseDTO> list = postService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable ObjectId idPost) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findById(idPost));
    }

    @PutMapping("/{idPost}")
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody PostUpdateDTO dto, @PathVariable ObjectId idPost) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(idPost, dto));
    }

    @DeleteMapping("/{idPost}")
    public ResponseEntity<?> deletePost(@PathVariable ObjectId idPost) {
        postService.deletePost(idPost);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
