package com.escoladeti.oldowl.stonix.forum.repository;

import com.escoladeti.oldowl.stonix.forum.model.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tdc on 09/04/16.
 */
@Repository
public interface AnswerRepository extends SuperRepository<Answer> {
    List<Answer> findByQuestionIdAndDeadIsFalse(String questionId);

    List<Answer> findByDeadIsFalseAndUserIdOrderByLastUpdateDesc(String userId);

    Integer countByQuestionId(String questionId);
}
