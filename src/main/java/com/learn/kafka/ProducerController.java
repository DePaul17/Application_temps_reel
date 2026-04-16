package com.learn.kafka;

import com.learn.kafka.producer.MessageProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class ProducerController {

    private final MessageProducer producer;

    public ProducerController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        producer.sendMessage("mon-tunnel-topic", message);
        return "Message envoyé : " + message;
    }
}