import java.util.ArrayList;
import java.util.List;

public class User1 {
    private String name;
    private List<Availability1> availabilities =new ArrayList<Availability1>();

    public User1(String name) {
        this.name =name;
    }
    public void addAvailability(Availability1 availability) {
        availabilities.add(availability);
    }
    public List<Availability1> getAvailabilityOnDate(String date) {
        List<Availability1> result =new ArrayList <Availability1>();
        for (Availability1 availability : availabilities) {
            if (availability.getStartTime().startsWith(date)) {
                result.add(availability);
            }
        }
        return result;
    }
}