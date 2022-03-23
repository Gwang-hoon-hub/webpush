package com.pang.webpush.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.pang.webpush.dto.FcmMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FcmService {

    private final Map<String, String> tokenMap = new HashMap<>();

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/moabuza/messages:send";
    private final ObjectMapper objectMapper;

//    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH = "moabuza-firebase-adminsdk-h9ox1-5af37638bf (1).json";

    // 메시징만 권한 설정
    @Value("${fcm.key.scope}")
    private String fireBaseScope;


    public void register(final String nickname, final String token) {
        tokenMap.put(nickname, token);
    }

    public String getToken(String nickname){
        return tokenMap.get(nickname);
    }

    public String sendMessageTo(String nickname, String title, String body) throws IOException {
        String targetToken = getToken(nickname);
        System.out.println("targetToken : " + targetToken);

        String message = makeMessage(targetToken, title, body);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        Response response = client.newCall(request)
                .execute();
        System.out.println(response.body().string());
        return "완성";
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }


    public String getAccessToken() throws IOException {
        // 이렇게 얻어온 AccessToken은 아래에서, RestAPI를
        // 이용해 FCM에 Push 요청을 보낼때, Header에 설정하여, 인증을 위해 사용할 것입니다.
        System.out.println("여기서 에러가 나오는가");
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                .createScoped(List.of(fireBaseScope));
        googleCredentials.refreshIfExpired();
        return "rt : " + googleCredentials.getAccessToken().getTokenValue();
    }
}
