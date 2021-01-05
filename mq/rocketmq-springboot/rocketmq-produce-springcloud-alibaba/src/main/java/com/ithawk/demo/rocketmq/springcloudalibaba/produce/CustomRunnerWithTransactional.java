package com.ithawk.demo.rocketmq.springcloudalibaba.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class CustomRunnerWithTransactional implements CommandLineRunner {

    @Autowired
    private SenderService senderService;

    @Override
    public void run(String... args) throws Exception {
        // COMMIT_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg1", 1);
        // ROLLBACK_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg2", 2);
        // ROLLBACK_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg3", 3);
        // COMMIT_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg4", 4);
    }

}
