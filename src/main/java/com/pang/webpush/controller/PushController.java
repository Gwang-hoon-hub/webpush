package com.pang.webpush.controller;

import com.pang.webpush.dto.FcmToken;
import com.pang.webpush.dto.Nick;
import com.pang.webpush.service.FCMService1;
import com.pang.webpush.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
public class PushController {
    private final FcmService service;
    
    @PostMapping("/ho")
    public String home(@RequestBody FcmToken token){
        System.out.println("닉네임 맨처음 :  ===== " + token.getNickname());
        System.out.println("token : === " + token.getToken());
        service.register(token.getNickname(), token.getToken());
        return "여기 나오냐";
    }

//    @PostMapping("/member/info")
//    public String getToken() {
////        System.out.println(token.getToken());
////        service.register(token.getNickname(), token.getToken());
//        return "토큰저장";
//    }

    @PostMapping("/push")
    public String info(@RequestBody Nick nickname) throws IOException, ExecutionException, InterruptedException {
        service.sendMessageTo(nickname.getNickname(), "알림간다.", "알림간다.");
        return "됨";
    }

//
//    @PostMapping("/push/noti")
//    public ResponseEntity createGroup(@RequestParam String nickname) throws IOException {
//        String ad = service.sendMessageTo(nickname, "알림간다.", "알림간다.");
//        return ResponseEntity.ok().body(ad);
//    }

}
