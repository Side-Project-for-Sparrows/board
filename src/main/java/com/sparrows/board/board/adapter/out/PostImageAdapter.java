package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.PostImageRepository;
import com.sparrows.board.board.model.entity.PostImageEntity;
import com.sparrows.board.board.port.out.PostImagePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostImageAdapter implements PostImagePort {

    @Autowired
    PostImageRepository postImageRepository;

    @Override
    public PostImageEntity save(PostImageEntity postImageEntity) {
        return postImageRepository.save(postImageEntity);
    }
}
