package com.escoladeti.oldowl.stonix.forum.repository;

import com.escoladeti.oldowl.stonix.forum.model.LikedQuestion;

import java.util.List;

/**
 * Created by Felipe on 03/11/2016.
 */
public interface LikedQuestionRepository extends SuperRepository<LikedQuestion> {
    List<LikedQuestion> findByDeadIsFalseAndQuestionId(String questionId);
    List<LikedQuestion> findByDeadIsFalseAndUserId(String userId);

    LikedQuestion findByDeadIsFalseAndUserIdAndQuestionId(String userId, String questionId);
}
