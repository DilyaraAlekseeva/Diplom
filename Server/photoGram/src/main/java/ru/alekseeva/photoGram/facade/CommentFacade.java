package ru.alekseeva.photoGram.facade;

import org.springframework.stereotype.Component;
import ru.alekseeva.photoGram.dto.CommentDTO;
import ru.alekseeva.photoGram.entity.Comment;

@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(comment.getUsername());
        commentDTO.setMessage(comment.getMessage());

        return commentDTO;
    }

}
