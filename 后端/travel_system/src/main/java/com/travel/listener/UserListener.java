package com.travel.listener;

import com.travel.service.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    @Autowired
    private UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "travel.insert.queue", durable = "true"),
            exchange = @Exchange(value = "www.travel.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"user.insert", "user.update", "user.delete",
                    "role.insert", "role.update", "role.delete",
                    "per.insert", "per.update", "per.delete",
            }
    ))
    public void typeAll(String userId) {
        if (userId == null){
            return;
        }
        userService.findUserAndRoleAndpermByUserId(userId);
    }
}
