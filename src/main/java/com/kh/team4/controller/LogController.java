package com.kh.team4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    public void someMethod() {
        String logMessage = "{\"message\": \"제발제발제발제발.\"}";
        logger.info(logMessage);
    }
}