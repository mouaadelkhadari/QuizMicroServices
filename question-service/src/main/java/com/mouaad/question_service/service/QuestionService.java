package com.mouaad.question_service.service;

import com.mouaad.question_service.dao.QuestionDAO;
import com.mouaad.question_service.model.Question;
import com.mouaad.question_service.model.QuestionWrapper;
import com.mouaad.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class  QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

     public ResponseEntity<Question> createQuestion(Question question){
        return new ResponseEntity<>(questionDAO.save(question), HttpStatus.CREATED);
     }

     public ResponseEntity<List<Question>> createQuestions(List<Question> questions){
        return new ResponseEntity<>(questionDAO.saveAll(questions), HttpStatus.CREATED);
     }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {
        List<Integer> questionsId = questionDAO.findRandomQuestionsByCategory(category, numQuestions);
        return new ResponseEntity<>(questionsId, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {
        List<Question> questions = new ArrayList<>();
        List<QuestionWrapper> wrappers = new ArrayList<>();
        for(Integer id : questionsIds){
            questions.add(questionDAO.findById(id).get());
        }
        for(Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses) {
            if(response.getResponse().equals(questionDAO.findById(response.getId()).get().getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
