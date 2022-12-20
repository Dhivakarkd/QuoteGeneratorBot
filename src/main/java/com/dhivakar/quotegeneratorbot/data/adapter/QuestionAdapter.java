package com.dhivakar.quotegeneratorbot.data.adapter;

import com.dhivakar.quotegeneratorbot.data.model.QuestionDO;
import com.dhivakar.quotegeneratorbot.data.repo.QuestionRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class QuestionAdapter {

    private final QuestionRepo questionRepo;

    public QuestionDO getQuestion() {
        return questionRepo.findById(1).get();
    }


    public QuestionDO getFunnyQuestion() {
        return questionRepo.getRandomFunnyQuestion();
    }

    public QuestionDO getWeirdQuestion() {
        return questionRepo.getRandomWeirdQuestion();
    }

    public QuestionDO getDeepQuestion() {
        return questionRepo.getDeepQuestion();
    }

    public QuestionDO getNHIEQuestion() {
        return questionRepo.getNHIEQuestion();
    }

    public QuestionDO getSillyQuestion() {
        return questionRepo.getSillyQuestion();
    }

    public QuestionDO getTruthQuestion() {
        return questionRepo.getTruthQuestion();
    }

    public QuestionDO getDareQuestion() {
        return questionRepo.getDareQuestion();
    }

}
