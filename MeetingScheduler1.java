import java.util.ArrayList;
import java.util.List;

public class MeetingScheduler1 {
    private List<User1> users;

    public MeetingScheduler1(List<User1> users) {
        this.users =users;
    }
    public Meeting1 scheduleMeeting(int durationHours, String deadline) {
        String[] deadlineParts =deadline.split(" ");
        String deadlineDate =deadlineParts[0];
        //String deadlineTime = deadlineParts[1];

        for (String currentDate =getCurrentDate(); !isAfter(currentDate, deadlineDate); currentDate =getNextDate(currentDate)) {
            List<Availability1> commonSlots = findCommonAvailability(currentDate);

            for (Availability1 slot : commonSlots) {
                if (isDurationAvailable(slot, durationHours)) {
                    String endTime =addHours(slot.getStartTime(), durationHours);
                    return new Meeting1(slot.getStartTime(), endTime);
                }
            }
        }
        return null;
    }
    private List<Availability1> findCommonAvailability(String date) {
        List<Availability1> commonSlots =new ArrayList<Availability1>();

        for (User1 user : users) {
            List<Availability1> userSlots =user.getAvailabilityOnDate(date);
            if (commonSlots.isEmpty()) {
                commonSlots.addAll(userSlots);
            } else {
                commonSlots =getOverlap(commonSlots, userSlots);
            }
        }
        return commonSlots;
    }
    private List<Availability1> getOverlap(List<Availability1> list1, List<Availability1> list2) {
        List<Availability1> overlap =new ArrayList <Availability1>();

        for (Availability1 slot1 : list1) {
            for (Availability1 slot2 : list2) {
                Availability1 common =slot1.getOverlap(slot2);
                if (common !=null) {
                    overlap.add(common);
                }
            }
        }
        return overlap;
    }
    private boolean isAfter(String date1, String date2) {
        return date1.compareTo(date2) > 0;
    }
    private boolean isDurationAvailable(Availability1 slot, int durationHours) {
        String endTime =addHours(slot.getStartTime(), durationHours);
        return endTime.compareTo(slot.getEndTime()) <= 0;
    }
    private String getNextDate(String currentDate) {
        // Simplified next date calculation (works for current month only)
        String[] parts =currentDate.split("-");
        int year =Integer.parseInt(parts[0]);
        int month =Integer.parseInt(parts[1]);
        int day =Integer.parseInt(parts[2]) + 1;
        return String.format("%04d-%02d-%02d", year, month, day);
    }  
    private String getCurrentDate() {
        // Simplified current date (hardcoded to a specific date for simplicity)
        return "2024-11-24";
    }
    private String addHours(String startTime, int hours) {
        String[] parts =startTime.split(" ");
        String date =parts[0];
        String[] timeParts =parts[1].split(":");
        int hour =Integer.parseInt(timeParts[0]);
        int minute =Integer.parseInt(timeParts[1]);
        hour += hours;
        return String.format("%s %02d:%02d", date, hour, minute);
    }
}