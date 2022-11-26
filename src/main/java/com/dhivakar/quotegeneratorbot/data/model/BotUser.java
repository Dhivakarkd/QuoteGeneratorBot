package com.dhivakar.quotegeneratorbot.data.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "quote_bot_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "chat_id")
    private long chatID;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
