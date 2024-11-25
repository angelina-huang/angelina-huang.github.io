import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        List <User1> users =new ArrayList<User1>();

        // Input for users and their availabilities
        System.out.print("Enter number of users: ");
        int userCount =Integer.parseInt(scanner.nextLine());

        for (int i=0; i <userCount; i++) {
            System.out.print("Enter name for user " + (i+1) + ": ");
            String name =scanner.nextLine();
            User1 user =new User1(name);

            System.out.print("Enter number of availability slots for " + name + ": ");
            int slotCount =Integer.parseInt(scanner.nextLine());

            for (int j =0; j <slotCount; j++) {
                System.out.print("Enter start time for slot " + (j + 1) + " (format: YYYY-MM-DD HH:MM): ");
                String startTime =scanner.nextLine();
                System.out.print("Enter end time for slot " + (j + 1) + " (format: YYYY-MM-DD HH:MM): ");
                String endTime =scanner.nextLine();
                user.addAvailability(new Availability1(startTime, endTime));
            }
            users.add(user);
        }
        // Input for meeting details
        System.out.print("Enter meeting duration in hours: ");
        int durationHours =Integer.parseInt(scanner.nextLine());
        System.out.print("Enter deadline for the meeting (format: YYYY-MM-DD): ");
        String deadline =scanner.nextLine() + " 23:59";

        MeetingScheduler1 scheduler =new MeetingScheduler1(users);
        Meeting1 meeting =scheduler.scheduleMeeting(durationHours, deadline);

        if (meeting !=null) {
            System.out.println("Meeting scheduled on " + meeting.getDate() + " from " + meeting.getStartTime() + " to " + meeting.getEndTime());
        } else {
            System.out.println("No available time slots for the meeting.");
        }
        scanner.close();
    }
}