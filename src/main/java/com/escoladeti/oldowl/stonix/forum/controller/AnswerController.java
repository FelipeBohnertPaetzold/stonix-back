package com.escoladeti.oldowl.stonix.forum.controller;

import com.escoladeti.oldowl.stonix.forum.model.Answer;
import com.escoladeti.oldowl.stonix.forum.model.Question;
import com.escoladeti.oldowl.stonix.forum.repository.AnswerRepository;
import com.escoladeti.oldowl.stonix.forum.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Felipe on 28/05/2016.
 */
@RestController
@RequestMapping(AnswerController.MAPPING)
@CrossOrigin("*")
public class AnswerController extends SuperController<Answer, AnswerRepository> {
    public static final String MAPPING = "/api/answers";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository repository;

    @Override
    public AnswerRepository getRepository() {
        return repository;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Answer> create(@RequestBody final Answer answer) {
        try {
            Question question = questionRepository.findOne(answer.getQuestion().getId());
            question.setNumberAnswers(question.getNumberAnswers()+ 1);
            questionRepository.save(question);
            return super.create(answer);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Answer> update(@RequestBody final Answer answer) {
        try {
            if (answer.getId() == null || answer.getDescription().equals("")) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            answer.setLastUpdate(new Date(System.currentTimeMillis()));
            return super.update(answer);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/question/{id}")
    public List<Answer> getAnswersByQuestion(@PathVariable("id") final String id) {
        return repository.findByQuestionIdAndDeadIsFalse(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{questionId}/better/{answerId}")
    public ResponseEntity<Answer> betterAnswer(@PathVariable("questionId") final String questionId, @PathVariable("answerId") final String answerId) {
        try {
            Question question = questionRepository.findOne(questionId);
            if (!question.getAnswered()) {
                Answer answer = repository.findOne(answerId);
                answer.acceptAnswer();
                question.acceptAnswer();
                questionRepository.save(question);
                return super.update(answer);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Answer> kill(@PathVariable("id") final String id){
        Answer answer = repository.findOne(id);
        try {
            if (answer.getBestAnswer().equals(true)) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                Question question = questionRepository.findOne(answer.getQuestion().getId());
                question.setNumberAnswers(question.getNumberAnswers() - 1);
                questionRepository.save(question);
                return super.kill(id);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    public ResponseEntity<List<Answer>> listAnswersByUser(@PathVariable("id") final String userId){
        List<Answer> answers = repository.findByDeadIsFalseAndUserIdOrderByLastUpdateDesc(userId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count/question/{questionId}")
    public ResponseEntity<Integer> countAnswerByQuestion(@PathVariable("questionId") final String questionId){
        return new ResponseEntity<>(repository.countByQuestionId(questionId), HttpStatus.OK);
    }
}
