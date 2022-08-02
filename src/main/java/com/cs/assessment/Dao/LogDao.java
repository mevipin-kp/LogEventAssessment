package com.cs.assessment.Dao;


import com.cs.assessment.service.LogProcessor;
import com.cs.assessment.models.EventAggregator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.InsertQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vipin on 31-07-2022.
 */
public class LogDao {

    private static final Logger LOGGER = LogManager.getLogger(LogDao.class);

    private static LogDao single_instance = null;
    private static final String DATABASE_NAME = "logEventsDB";
    private static final String TABLE_NAME= "LOG_EVENT";
    private HikariDataSource hikariDatasource;

    public static LogDao getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new LogDao();
        }
        return single_instance;
    }


    private LogDao(){
        createConnectionPool();
        createLogEventTable();
    }


    public void persist(EventAggregator eventAggregator){

        try (Connection connection = hikariDatasource.getConnection()) {
            InsertQuery<?> insert = DSL.using(connection).insertQuery(DSL.table(TABLE_NAME));
            insert.addValue(DSL.field("EVENTID", String.class), eventAggregator.getEVENTID());
            insert.addValue(DSL.field("TIMETAKEN", Long.class), eventAggregator.getTIMETAKEN());
            insert.addValue(DSL.field("TYPE", String.class), eventAggregator.getTYPE());
            insert.addValue(DSL.field("HOST", String.class), eventAggregator.getHOST());
            insert.addValue(DSL.field("ALERT", Boolean.class), eventAggregator.getALERT());
            insert.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while creating the table",e);
            System.exit(1);
        }

    }

    private void createLogEventTable() {
        try (Connection connection = hikariDatasource.getConnection()) {
            DSL.using(connection).createTableIfNotExists("LOG_EVENT")
                    //.column("ID", SQLDataType.UUID.nullable(false))
                    .column("EVENTID", SQLDataType.VARCHAR(255).nullable(false))
                    .column("TIMETAKEN", SQLDataType.BIGINT.nullable(false))
                    .column("TYPE", SQLDataType.VARCHAR(255))
                    .column("HOST", SQLDataType.VARCHAR(255))
                    .column("ALERT", SQLDataType.BOOLEAN.nullable(false))
                    .execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while creating the table",e);
            System.exit(1);
        }
    }


    private void createConnectionPool() {
        LOGGER.info("Creating connection pool...!");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:hsqldb:file:hsqldb/" + DATABASE_NAME);
        hikariConfig.setUsername("SA");
        hikariConfig.setPassword("");
        hikariConfig.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setMaximumPoolSize(LogProcessor.threadCount);
        hikariConfig.setMinimumIdle(2);
        hikariDatasource = new HikariDataSource(hikariConfig);

    }


}
