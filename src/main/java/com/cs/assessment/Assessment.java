package com.cs.assessment;

import com.cs.assessment.service.LogProcessor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;

/**
 * Created by vipin on 30-07-2022.
 */
public class Assessment {

    // Initialising the logger class.
    private static final Logger LOGGER = LogManager.getLogger(Assessment.class);

    public static void main(String args[]) throws Exception {

         //validate command line arg
        if (args== null ||/* args[0].isEmpty() ||*/ args.length > 1){
            LOGGER.error("Program Error : No log file path provided OR more than one path provided to the application. " +
                    "Please provide exactly one log file path");
            System.exit(1);
        }

        String logFileName = args[0];
        LOGGER.info("Log file path received from user : {}",logFileName);
        //Function validate the size of the file before processing against the JVM memory available.
        //validateInputFileSize(logFileName); //currently commentedout
        LogProcessor lp= new LogProcessor();
        lp.logFileReaderAndProcessor(logFileName);
    }

    //Function validate the size of the file before processing against the JVM memory available.
    public static void validateInputFileSize(String fileName) throws Exception {

        File file = new File(fileName);
        Long inputFileSize = file.length();
        Long freeMemory = Runtime.getRuntime().freeMemory();
        LOGGER.info("input File size ={}, Available JVM free memory={}",inputFileSize,freeMemory);

        if(inputFileSize > freeMemory * 0.75) {
            LOGGER.error("System is restricting the input file size to Max {} bytes. Recdeived file size={}",freeMemory * 0.75,inputFileSize);
            System.exit(1);
        }

    }





}
