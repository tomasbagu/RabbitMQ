package com.example;

import com.rabbitmq.client.*;

public class Consumer {
    private final static String QUEUE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received: '" + message + "'");
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
    }
}
