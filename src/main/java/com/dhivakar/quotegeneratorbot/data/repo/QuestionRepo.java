package com.dhivakar.quotegeneratorbot.data.repo;

import com.dhivakar.quotegeneratorbot.data.model.QuestionDO;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<QuestionDO, Integer> {
}
