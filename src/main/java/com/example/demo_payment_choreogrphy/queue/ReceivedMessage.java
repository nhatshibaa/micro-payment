package com.example.demo_payment_choreogrphy.queue;

import com.example.demo_payment_choreogrphy.queue.event.OrderEvent;
import com.example.demo_payment_choreogrphy.service.PaymentService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo_payment_choreogrphy.queue.Config.QUEUE_PRODUCT_TO_PAYMENT;

@Log4j2
@Component
public class ReceivedMessage {
    @Autowired
    PaymentService paymentService;

    @Autowired
    SendMessage sendMessage;

    @RabbitListener(queues = {QUEUE_PRODUCT_TO_PAYMENT})
    public void getMessage(String message){
        log.info("nhan message " + message);
        Gson gson = new Gson();
        OrderEvent orderEvent = gson.fromJson(message, OrderEvent.class);
        boolean paymentCheck = paymentService.deductPaymentCheck(orderEvent.getCustomerName(),orderEvent.getTotalPrice());
        if (!paymentCheck){
            orderEvent.setStatus(-1);
            sendMessage.sendMessage(gson.toJson(orderEvent));
            return;
        }
        orderEvent.setStatus(3);
        sendMessage.sendMessage(gson.toJson(orderEvent));

    }
}
