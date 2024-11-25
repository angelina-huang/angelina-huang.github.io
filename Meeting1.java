public class Meeting1 {
    private String startTime;
    private String endTime;

    public Meeting1(String startTime, String endTime) {
        this.startTime =startTime;
        this.endTime =endTime;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getDate() {
        return startTime.split(" ")[0];
    }
}
