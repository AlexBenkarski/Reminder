import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class Reminder implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime dateTime; // date and time
    private String title; // reminder title
    private String description; // description of the reminder
    private String location; // location of the reminder
    private Priority priority; // priority level of the reminder
    private Status status; // status of the reminder
    private String userId; // owner of the reminder
    private static Scanner scnr;

    @Override
    public String toString() {
        return title +
                "\nDescription: " + description +
                "\nDateTime: " + dateTime +
                "\nLocation: " + location + "\n";
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Reminder(String title, String description, LocalDateTime dateTime, String location, Priority priority,
                    Status status) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.priority = priority;
        this.status = status;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public enum Status {
        PENDING, COMPLETE, MISSED
    }

    public String getUserId() {
        return userId;
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime.isAfter(LocalDateTime.now())) {
            this.dateTime = dateTime;
        } else {
            throw new IllegalArgumentException("Reminder date and time must be in the future!");
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        List<Reminder> reminders = readRemindersFromFile();

        System.out.println("Welcome to the Reminder App!");


        while (true) {
            //MENU
            System.out.println("Menu:");
            System.out.println("1. Create a reminder");
            System.out.println("2. View all reminders");
            System.out.println("3. Quit program");

            int choice;
            try {
                choice = Integer.parseInt(scnr.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a valid choice");
                continue;
            }

            switch (choice) {
                case 1:
                    Reminder reminder = createReminder(scnr);
                    reminders.add(reminder);
                    System.out.println("Reminder created!");
                    break;
                case 2:
                    if (reminders.isEmpty()) {
                        System.out.println("No reminders available");
                    }else {
                        System.out.println("All reminders!");
                        for(Reminder r : reminders){
                            System.out.println(r);
                        }

                    }
                    break;
                case 3:
                    saveReminders(reminders);
                    System.out.println("Goodbye!");
                    scnr.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice");
                    break;
            }
        }
    }
    private static List<Reminder> readRemindersFromFile(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("reminder.txt"))){
            return (List<Reminder>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            return new ArrayList<>();
        }
    }
        private static Reminder createReminder (Scanner scnr){
            System.out.print("Enter the title of the reminder: ");
            String title = scnr.nextLine();

            System.out.print("Enter the description of the reminder: ");
            String description = scnr.nextLine();

            System.out.print("Enter the date and time of the reminder (yyyy-MM-dd HH:mm): ");
            String dateTimeStr = scnr.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("Enter the location of the reminder: ");
            String location = scnr.nextLine();

            System.out.print("Enter the priority of the reminder (HIGH, MEDIUM, LOW): ");
            String priorityStr = scnr.nextLine();
            Reminder.Priority priority = Reminder.Priority.valueOf(priorityStr.toUpperCase());

            return new Reminder(title, description, dateTime, location, priority, null);
        }
            private static void saveReminders (List <Reminder> reminders) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("reminder.txt"))) {
                    out.writeObject(reminders);
                    System.out.println("Reminders saved to reminder.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }