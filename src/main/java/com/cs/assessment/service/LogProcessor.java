package com.cs.assessment.service;

import com.cs.assessment.Dao.LogDao;
import com.cs.assessment.models.EventAggregator;
import com.cs.assessment.models.LogModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vipin on 30-07-2022.
 */
public class LogProcessor {

    public static int threadCount = 200;

    private static final Logger LOGGER = LogManager.getLogger(LogProcessor.class);

    ConcurrentHashMap<String,LogModel> logEventMap = new ConcurrentHashMap();

    public void logFileReaderAndProcessor(String logFilePath) throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        LOGGER.info("Thread Pool initialized with count {}",threadCount);

        LOGGER.info("Reading file from path->{}",logFilePath);

        try (LineIterator it = FileUtils.lineIterator(new File(logFilePath), StandardCharsets.UTF_8.name())) {
            while (it.hasNext()) {
                String line = it.nextLine();
                executorService.execute(new Runnable() {
                    public void run()
                    {
                        // gets the name of current thread
                        System.out.println("Current Thread Name: "+ Thread.currentThread().getName());
                        // gets the ID of the current thread
                        System.out.println("Current Thread ID: "+ Thread.currentThread().getId());

                        LOGGER.info("Started Processing event -> {}",line);
                        LogModel model = new JsonToPojoConverter().convertToLogModelPOJO(line);
                        EventAggregator eventAggregator = processEvents(model);
                        if(eventAggregator != null)
                            LogDao.getInstance().persist(eventAggregator);
                    }
                });
            }
            sleepThread();
            executorService.shutdown();
        }
    }

    private synchronized  EventAggregator processEvents(LogModel model){
        if(!logEventMap.containsKey(model.getId())) {
           logEventMap.put(model.getId(), model);
        } else {
            EventAggregator eventAggregator;
            if (model.getState().equals("STARTED")) {
                eventAggregator = new EventAggregator(model, logEventMap.get(model.getId()));
            } else {
                eventAggregator = new EventAggregator(logEventMap.get(model.getId()),model);
            }
            logEventMap.remove(model.getId());
            return eventAggregator;
        }
        return null;
    }


    private void sleepThread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Error while trying to sleep teh main thread",e);
            System.exit(1);
        }
    }

}
