package com.cs.assessment.models;

import java.io.Serializable;

/**
 * Created by vipin on 30-07-2022.
 */
public class LogModel implements Serializable{

        private String id;
        private String state;
        private String type;
        private String host;
        private String timestamp;

        public LogModel(String id, String state, String type, String host, String timestamp) throws Exception {
            this.id = id;
            this.state = state;
            this.type = type;
            this.host = host;
            this.timestamp = timestamp;
        }

        public LogModel(){}

        public String getId(){ return this.id;}

        public void setId(String id){
            this.id = id;
        }

        public String getState(){
            return this.state;
        }

        public void setState(String state){
            this.state = state;
        }

        public String getType(){
            return this.type;
        }

        public void setType(String type){
            this.type = type;
        }

        public String getHost(){
            return this.host;
        }

        public void setHost(String host){
            this.host = host;
        }

        public String getTimestamp(){
            return this.timestamp;
        }

        public void setTimestamp(String timestamp){
            this.timestamp = timestamp;
        }

    @Override
    public String toString() {
        return "LogModel{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
