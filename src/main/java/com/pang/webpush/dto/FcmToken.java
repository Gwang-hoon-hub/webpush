package com.pang.webpush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FcmToken {
    // fcm 토큰 받아오기
    private String token;
    private String nickname;
}
