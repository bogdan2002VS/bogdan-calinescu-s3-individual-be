package com.veganny.controller;

import com.veganny.domain.Message;
import com.veganny.domain.MessageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private ChatController chatController;


    @Test
    @DisplayName("Should send a private message to the specified receiver and return the message")
    void recMessageWhenSendingPrivateMessageToReceiver() {// create a message object
        Message message = new Message("sender", "receiver", "Hello", "2022-01-01", MessageStatus.MESSAGE);

        // mock the simpMessagingTemplate's convertAndSendToUser method
        doNothing().when(simpMessagingTemplate).convertAndSendToUser(
                message.getReceiverName(), "/private", message);

        // call the recMessage method and assert that it returns the message object
        Message result = chatController.recMessage(message);
        assertEquals(message, result);

        // verify that the simpMessagingTemplate's convertAndSendToUser method was called once with the correct arguments
        verify(simpMessagingTemplate, times(1)).convertAndSendToUser(
                message.getReceiverName(), "/private", message);
    }


}