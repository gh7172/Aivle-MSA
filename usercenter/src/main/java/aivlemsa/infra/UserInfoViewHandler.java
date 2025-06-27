package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UserInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private UserInfoRepository userInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOnUserLoggedIn_then_CREATE_1(
        @Payload OnUserLoggedIn onUserLoggedIn
    ) {
        try {
            if (!onUserLoggedIn.validate()) return;

            // view 객체 생성
            UserInfo userInfo = new UserInfo();
            // view 객체에 이벤트의 Value 를 set 함
            userInfo.setUserId(onUserLoggedIn.getUserId());
            // view 레파지 토리에 save
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionPaymentSucceeded_then_UPDATE_1(
        @Payload SubscriptionPaymentSucceeded subscriptionPaymentSucceeded
    ) {
        try {
            if (!subscriptionPaymentSucceeded.validate()) return;
            // view 객체 조회
            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(
                subscriptionPaymentSucceeded.getUserId()
            );

            if (userInfoOptional.isPresent()) {
                UserInfo userInfo = userInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                userInfo.setPoints(subscriptionPaymentSucceeded.getPoints());
                // view 레파지 토리에 save
                userInfoRepository.save(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPurchasePaymentSucceeded_then_UPDATE_2(
        @Payload PurchasePaymentSucceeded purchasePaymentSucceeded
    ) {
        try {
            if (!purchasePaymentSucceeded.validate()) return;
            // view 객체 조회
            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(
                purchasePaymentSucceeded.getUserId()
            );

            if (userInfoOptional.isPresent()) {
                UserInfo userInfo = userInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                userInfo.setPoints(purchasePaymentSucceeded.getPoints());
                // view 레파지 토리에 save
                userInfoRepository.save(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserSubUpdated_then_UPDATE_3(
        @Payload UserSubUpdated userSubUpdated
    ) {
        try {
            if (!userSubUpdated.validate()) return;
            // view 객체 조회
            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(
                userSubUpdated.getUserId()
            );

            if (userInfoOptional.isPresent()) {
                UserInfo userInfo = userInfoOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                userInfo.setSubscriptionExpiryDate(
                    userSubUpdated.getSubscriptionExpiryDate()
                );
                // view 레파지 토리에 save
                userInfoRepository.save(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserSubUpdated_then_UPDATE_4(
        @Payload UserSubUpdated userSubUpdated
    ) {
        try {
            if (!userSubUpdated.validate()) return;
            // view 객체 조회

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
