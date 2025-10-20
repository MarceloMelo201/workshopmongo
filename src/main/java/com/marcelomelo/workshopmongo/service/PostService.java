package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import com.marcelomelo.workshopmongo.domain.entities.UserEntity;
import com.marcelomelo.workshopmongo.dtos.post.PostCreateDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostResponseDTO;
import com.marcelomelo.workshopmongo.dtos.post.PostUpdateDTO;
import com.marcelomelo.workshopmongo.exception.PostNotFound;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import com.marcelomelo.workshopmongo.mapper.PostMapper;
import com.marcelomelo.workshopmongo.repository.PostRepository;
import com.marcelomelo.workshopmongo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PostResponseDTO createPost(PostCreateDTO dto) {
        UserEntity user = userRepository.findById(dto.idUser()).orElseThrow(UserNotFound::new);

        PostEntity post = PostEntity
                .builder()
                .user(user)
                .date(LocalDate.now())
                .title(dto.title())
                .body(dto.body())
                .build();

        return postMapper.toResponse(postRepository.save(post));
    }

    public List<PostResponseDTO> findAll() {
        return postRepository.findAll().stream().map(postMapper::toResponse).toList();
    }

    public PostResponseDTO findById(ObjectId idPost) {
        return postMapper.toResponse(postRepository.findById(idPost).orElseThrow(PostNotFound::new));
    }

    @Transactional
    public PostResponseDTO updatePost(ObjectId idPost, PostUpdateDTO dto) {
        PostEntity post = postRepository.findById(idPost).orElseThrow(PostNotFound::new);

        if (!dto.title().isBlank()) post.setTitle(dto.title());
        if (!dto.body().isBlank()) post.setBody(dto.body());
        post.setDate(LocalDate.now());

        return postMapper.toResponse(postRepository.save(post));
    }

    @Transactional
    public void deletePost(ObjectId idPost) {
        PostEntity post = postRepository.findById(idPost).orElseThrow(PostNotFound::new);
        postRepository.delete(post);
    }

}
