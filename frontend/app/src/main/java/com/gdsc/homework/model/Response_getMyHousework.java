package com.gdsc.homework.model;

import java.util.List;

public class Response_getMyHousework {
    private Integer status;
    private String message;
    private List<data> data;

    public Response_getMyHousework() {
    }

    public Response_getMyHousework(Integer status, String message, List<Response_getMyHousework.data> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Response_getMyHousework.data> getData() {
        return data;
    }

    public void setData(List<Response_getMyHousework.data> data) {
        this.data = data;
    }

    public class data {
        private long id;
        private String name;
        private long userId;
        private long roomId;
        private String day;
        private String startTime;
        private String finishTime;
        private boolean repeat;
        private int penalty;
        private String memo;
        private boolean finished;

        public data() {
        }

        public data(long id, String name, long userId, long roomId, String day, String startTime, String finishTime, boolean repeat, int penalty, String memo, boolean finished) {
            this.id = id;
            this.name = name;
            this.userId = userId;
            this.roomId = roomId;
            this.day = day;
            this.startTime = startTime;
            this.finishTime = finishTime;
            this.repeat = repeat;
            this.penalty = penalty;
            this.memo = memo;
            this.finished = finished;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public boolean isRepeat() {
            return repeat;
        }

        public void setRepeat(boolean repeat) {
            this.repeat = repeat;
        }

        public int getPenalty() {
            return penalty;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }
    }
}
