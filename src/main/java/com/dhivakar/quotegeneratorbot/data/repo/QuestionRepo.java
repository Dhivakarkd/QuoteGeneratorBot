package com.dhivakar.quotegeneratorbot.data.repo;

import com.dhivakar.quotegeneratorbot.data.model.QuestionDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<QuestionDO, Integer> {

    @Query(value = "SELECT * FROM question_meta WHERE category = 'FUNNY' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    QuestionDO getRandomFunnyQuestion();

    @Query(value = "SELECT * FROM question_meta WHERE category = 'WEIRD' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    QuestionDO getRandomWeirdQuestion();

    @Query(value = "SELECT * FROM question_meta WHERE category = 'DEEP_MEANING' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    QuestionDO getDeepQuestion();

    @Query(value = "SELECT * FROM question_meta WHERE category = 'NVR_HV_I_EVE' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    QuestionDO getNHIEQuestion();

    @Query(value = "SELECT * FROM question_meta WHERE category = 'NON_SENSE' ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    QuestionDO getSillyQuestion();
}
