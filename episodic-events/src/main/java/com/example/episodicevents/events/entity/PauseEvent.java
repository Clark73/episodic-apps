package com.example.episodicevents.events.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PauseEvent extends Event {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonProperty("type")
    @Override
    public String getEventType() {
        return "pause";
    }

    static public class Data {
        private int offset;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offest) {
            this.offset = offest;
        }
    }

}
