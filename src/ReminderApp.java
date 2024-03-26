import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReminderApp extends JFrame {
    private DefaultListModel<Reminder> reminderListModel;
    private JList<Reminder> reminderJList;
    private List<Reminder> reminders;

    public ReminderApp() {
        super("Reminder App");
        this.reminders = readRemindersFromFile();
        UI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void UI() {
        setLayout(new BorderLayout());

        // List model and JList initialization
        reminderListModel = new DefaultListModel<>();
        reminders.forEach(reminderListModel::addElement);
        reminderJList = new JList<>(reminderListModel);
        add(new JScrollPane(reminderJList), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createButton = new JButton("Create Reminder");
        JButton viewButton = new JButton("View Reminders");
        JButton editButton = new JButton("Edit Reminder");
        buttonsPanel.add(createButton);
        buttonsPanel.add(viewButton);
        buttonsPanel.add(editButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Action Listeners
        createButton.addActionListener(this::createReminderAction);
        viewButton.addActionListener(this::viewRemindersAction);
        editButton.addActionListener(this::editReminderAction);
    }

    private void createReminderAction(ActionEvent e) {
        JDialog dialog = new JDialog(this, "Create Reminder", true);
        dialog.setLayout(new GridLayout(0, 2));

        // title of reminder
        JTextField titleField = new JTextField();
        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);

        //description of reminder
        JTextField descField = new JTextField();
        dialog.add(new JLabel("Description: "));
        dialog.add(descField);

        //location of reminder
        JTextField locationField = new JTextField();
        dialog.add(new JLabel("Location: "));
        dialog.add(locationField);

        //date and time of reminder
        JTextField dateandTimeField = new JTextField();
        dialog.add(new JLabel("Date and Time: "));
        dialog.add(dateandTimeField);

        //drop down for priority
        JComboBox<Reminder.Priority> priorityComboBox = new JComboBox<>(Reminder.Priority.values());
        dialog.add(new JLabel("Priority: "));
        dialog.add(priorityComboBox);

        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(event -> {
            // Gather data from fields and create Reminder
            String title = titleField.getText();
            String description = descField.getText();
            String dateTimeStr = dateandTimeField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Error with date and time");
                return;
            }
            String location = locationField.getText();
            Reminder.Priority priority = (Reminder.Priority) priorityComboBox.getSelectedItem();


            Reminder newReminder = createReminder(title, description, dateTime, location, priority);
            reminders.add(newReminder);
            reminderListModel.addElement(newReminder);

            dialog.dispose();
        });

        dialog.add(submitButton);
        dialog.pack();
        dialog.setVisible(true);
    }

    protected static Reminder createReminder(String title, String description, LocalDateTime dateTime, String location, Reminder.Priority priority) {
        return new Reminder(title, description, dateTime, location, priority, Reminder.Status.PENDING);
    }

    private void viewRemindersAction(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "View Reminders Placeholder");
    }

    private void editReminderAction(ActionEvent e) {


    }

    private static List<Reminder> readRemindersFromFile() {
        return Reminder.readRemindersFromFile();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReminderApp::new);
    }
}