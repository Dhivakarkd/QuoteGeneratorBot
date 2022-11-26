package com.dhivakar.quotegeneratorbot.data.repo;

import com.dhivakar.quotegeneratorbot.data.model.BotUser;
import com.dhivakar.quotegeneratorbot.data.model.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteBotUserRepo extends CrudRepository<BotUser,Integer> {

    List<BotUser> findByStatus(UserStatus status);
    Optional<BotUser> findByChatId(long chatId);
}
