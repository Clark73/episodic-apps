package com.example.episodicevents.events.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RewindEvent extends Event {
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
        return "rewind";
    }

    static public class Data {
        private int startOffset;
        private int endOffset;
        private double speed;

        public int getStartOffset() {
            return startOffset;
        }

        public void setStartOffset(int startOffset) {
            this.startOffset = startOffset;
        }

        public int getEndOffset() {
            return endOffset;
        }

        public void setEndOffset(int endOffset) {
            this.endOffset = endOffset;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }
}
