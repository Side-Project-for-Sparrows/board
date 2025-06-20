package com.sparrows.board.board.adapter.out;

import com.sparrows.board.board.adapter.repository.LikeRepository;
import com.sparrows.board.board.model.entity.LikeEntity;
import com.sparrows.board.board.port.out.LikePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeAdapter implements LikePort {
    @Autowired
    LikeRepository likeRepository;

    @Override
    public LikeEntity findByPostIdAndUserId(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId,userId);
    }

    @Override
    public void save(LikeEntity like) {
        likeRepository.save(like);
    }

    @Override
    public void delete(LikeEntity entity) {
        likeRepository.delete(entity);
    }
}
