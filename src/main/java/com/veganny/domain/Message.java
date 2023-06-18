package com.veganny.domain;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Message {
    private String senderName;
    private String receiverName;
    private String bodyMessage;
    private String date;
    private MessageStatus status;
}
