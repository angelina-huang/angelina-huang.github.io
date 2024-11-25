public class Availability1 {
    private String startTime;
    private String endTime;

    public Availability1(String startTime, String endTime) {
        this.startTime=startTime;
        this.endTime=endTime;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public Availability1 getOverlap(Availability1 other) {
        if (startTime.compareTo(other.getEndTime()) <0 && endTime.compareTo(other.getStartTime()) >0) {
            String latestStart=startTime.compareTo(other.getStartTime()) >0 ? startTime : other.getStartTime();
            String earliestEnd=endTime.compareTo(other.getEndTime()) <0 ? endTime : other.getEndTime();
            return new Availability1(latestStart, earliestEnd);
        }
        return null;
    }
}