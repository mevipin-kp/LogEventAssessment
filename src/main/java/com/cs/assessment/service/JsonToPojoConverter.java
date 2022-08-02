package com.cs.assessment.service;

import com.cs.assessment.models.LogModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vipin on 31-07-2022.
 */
public class JsonToPojoConverter {

    private static final Logger LOGGER = LogManager.getLogger(JsonToPojoConverter.class);

    public LogModel convertToLogModelPOJO(String logLine) {
        LOGGER.info("Converting the Json String to POJO. Json = {}",logLine);
        ObjectMapper obj = new ObjectMapper();

            try {
                return obj.readValue(logLine, LogModel.class);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error while paqrsing and converting to POJO model",e);
                System.exit(1);
            }
       return null;
    }
}
