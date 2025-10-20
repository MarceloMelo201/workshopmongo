package com.marcelomelo.workshopmongo.service;

import com.marcelomelo.workshopmongo.domain.entities.CommentEntity;
import com.marcelomelo.workshopmongo.domain.entities.PostEntity;
import com.marcelomelo.workshopmongo.dtos.comment.AuthorDTO;
import com.marcelomelo.workshopmongo.dtos.comment.CommentCreateDTO;
import com.marcelomelo.workshopmongo.dtos.comment.CommentResponseDTO;
import com.marcelomelo.workshopmongo.dtos.user.UserResponseDTO;
import com.marcelomelo.workshopmongo.exception.CommentNotFound;
import com.marcelomelo.workshopmongo.exception.PostNotFound;
import com.marcelomelo.workshopmongo.mapper.CommentMapper;
import com.marcelomelo.workshopmongo.repository.CommentRepository;
import com.marcelomelo.workshopmongo.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public CommentResponseDTO createComment(ObjectId idUser, ObjectId idPost, CommentCreateDTO dto) {
        UserResponseDTO user = userService.findById(idUser);
        PostEntity post = postRepository.findById(idPost).orElseThrow(PostNotFound::new);

        CommentEntity comment = CommentEntity
                .builder()
                .idPost(idPost)
                .author(new AuthorDTO(idUser, user.name()))
                .date(LocalDate.now())
                .text(dto.text())
                .build();

        post.getComments().add(comment);
        postRepository.save(post);
        return commentMapper.toResponse(comment);
    }

    public List<CommentResponseDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toResponse).toList();
    }

    public CommentResponseDTO findById(ObjectId idComment) {
        return commentMapper.toResponse(commentRepository.findById(idComment).orElseThrow(CommentNotFound::new));
    }

    @Transactional
    public CommentResponseDTO updateComment(ObjectId idComment, ObjectId idPost, CommentCreateDTO dto) {
        PostEntity post = postRepository.findById(idPost).orElseThrow(PostNotFound::new);

        CommentEntity comment = post.getComments()
                .stream()
                .filter(c -> c.getIdComment().equals(idComment))
                .findFirst()
                .orElseThrow(CommentNotFound::new);

        if (!dto.text().isBlank()) comment.setText(dto.text());
        comment.setDate(LocalDate.now());
        postRepository.save(post);
        return commentMapper.toResponse(comment);
    }


    @Transactional
    public void deleteComment(ObjectId idComment, ObjectId idPost, CommentCreateDTO dto){
        PostEntity post = postRepository.findById(idPost).orElseThrow(PostNotFound::new);

        CommentEntity comment = post.getComments()
                .stream()
                .filter(c -> c.getIdComment().equals(idComment))
                .findFirst()
                .orElseThrow(CommentNotFound::new);

        post.getComments().remove(comment);
        postRepository.save(post);
    }

}
