package com.pang.webpush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushNotificationReqDto {
    private String title;
    private String message;
    private String topic;
    private String token;

}
