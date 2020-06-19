package ui.gui;

import courses.Course;
import courses.CourseManager;
import exceptions.InvalidNameException;
import network.WebPageReader;
import weather.Weather;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


public class MainGui extends JFrame implements ActionListener {
    private ArrayList<Component> setupComponents;
    private ArrayList<JLabel> hours;
    private ArrayList<CourseButton> courses;
    private GridBagConstraints gbc;
    private JLabel saveAnswer;
    private JLabel addAnswer;
    private JTextField courseName;
    private JTextField courseDay;
    private JTextField courseTime;
    private JTextField courseSection;
    private CourseManager courseManager;


    //Modifies: this
    //Effects:Sets up the frame, and initial Layout

    public MainGui(CourseManager courseManager) {
        super("Course Manager");
        this.courseManager = courseManager;
        courses = new ArrayList<>();
        hours = new ArrayList<>();
        gbc = new GridBagConstraints();
        setupComponents = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 900));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        JButton btn = new JButton("Yes");
        JButton btn2 = new JButton("No");

        btn.setActionCommand("Yes");
        btn.addActionListener(this);
        btn2.setActionCommand("No");
        btn2.addActionListener(this);

        JLabel label = new JLabel("Would you like to load previous data?");
        setUpWeather();
        setUpGridBagConstraints(2,1,3,1);
        add(btn, gbc);
        setUpGridBagConstraints(2,2,3,1);
        add(btn2, gbc);
        setUpGridBagConstraints(2,0,3,1);
        add(label, gbc);
        Random r  = new Random();
        getContentPane().setBackground(new Color(150 + r.nextInt(105), 150 + r.nextInt(105),
                150 + r.nextInt(105)));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        setupComponents.add(btn);
        setupComponents.add(btn2);
        setupComponents.add(label);
    }

    //Modifies: this, CourseManager
    //Effects: determines what action to do when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Yes")) {
            removeSetupComps();
            startLoad();
            managerSetup();
        } else if (e.getActionCommand().equals("No")) {
            removeSetupComps();
            managerSetup();
        } else if (e.getActionCommand().equals("Add course")) {
            addCourse(courseName.getText(), courseSection.getText(), courseTime.getText(), courseDay.getText());
        } else if (e.getActionCommand().equals("Save")) {
            saveManager();
        } else {
            courseButtonClicked(e);
        }
    }

    //Modifies: this
    //EFFECTS: removes component
    private void removeSetupComps() {
        for (Component c : setupComponents) {
            c.setVisible(false);
            remove(c);
        }
    }

    //Modifies: this, courseManager
    //Effects: saves the course manager
    private void saveManager() {
        String response = "";
        try {
            response = courseManager.save("CourseManagerSaved.txt");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            response = "couldn't save";
        }
        saveAnswer.setText(response);
    }


    //MODIFIES: this
    //EFFECTS: tries to load the course manager data from previous sessions.
    private void startLoad() {
        try {
            courseManager.load("CourseManagerSaved.txt");
        } catch (IOException e) {
            System.out.println("Loading file not found. Could not Load.");
        } finally {
            System.out.println("Attempted to load a file");
        }
    }

    //Modifies: this
    //Effects: sets up the second layout
    private void managerSetup() {
        gbc.insets = new Insets(15,0,15,30);
        setUpTop(gbc);
    }

    //Modifies: this
    //Effects: sets up the top labels
    private void setUpTop(GridBagConstraints gb) {
        JLabel newName = new JLabel("Course Name:");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0,1);
        add(newName, gbc);
        JLabel newSection = new JLabel("Course Section:");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0,1);
        add(newSection, gbc);
        JLabel newTime = new JLabel("Course Time:");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 0,1);
        add(newTime, gbc);
        JLabel neWDay = new JLabel("Course Day:");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 0,1);
        add(neWDay, gbc);

        setUpTopButtonsAndLabels();
        setUpTimeLabels();
        setUpCourseButtons();
        setUpTextBoxes();
    }

    //Modifies: this
    //Effects: sets up the top buttons
    private void setUpTopButtonsAndLabels() {
        JButton addCourse = new JButton("Add course");
        addCourse.setActionCommand("Add course");
        addCourse.addActionListener(this);
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 1,1);
        add(addCourse, gbc);
        JButton save = new JButton("Save");
        save.setActionCommand("Save");
        save.addActionListener(this);
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 5, 1,1);
        add(save, gbc);
        addAnswer = new JLabel("");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 0,1);
        add(addAnswer, gbc);
        saveAnswer = new JLabel("");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 5, 0,1);
        add(saveAnswer, gbc);
    }

    //Modifies: this
    //Effects: sets up the text boxes for the add course implementation
    private void setUpTextBoxes() {
        courseDay = new JTextField();
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 1, 1);
        add(courseDay, gbc);
        courseName = new JTextField();
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 1);
        add(courseName, gbc);
        courseSection = new JTextField();
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 1);
        add(courseSection, gbc);
        courseTime = new JTextField();
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 1, 1);
        add(courseTime, gbc);

    }

    //MODIFIES: this
    //EFFECTS:Sets grid bag constraints to the input
    private void setUpGridBagConstraints(int fill, int gridX, int gridY, int gridWidth) {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;
        gbc.fill = fill;
    }


    //MODIFIES: this
    //EFFECTS: sets up the hour labels and the
    private void setUpTimeLabels() {
        int startHour = 8;
        int endHour = 20;
        for (int h = startHour; h <= endHour; h++) {
            JLabel tempLabel = new JLabel(h + ":00 hrs");
            setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, h - 4, 1);
            add(tempLabel, gbc);
            hours.add(tempLabel);
        }

        addDayLabel("monday", 1);
        addDayLabel("tuesday", 2);
        addDayLabel("wednesday", 3);
        addDayLabel("thursday", 4);
        addDayLabel("friday", 5);
    }

    //MODIFIES: this
    //EFFECTS:adds label according to a certain day
    private void addDayLabel(String day, int xpos) {
        JLabel templbl = new JLabel(day + ":");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, xpos, 3, 1);
        add(templbl, gbc);
    }

    //Modifies: this
    //Effects: sets up the course buttons
    private void setUpCourseButtons() {
        for (String key: courseManager.getCoursesMap().keySet()) {
            for (Course course : courseManager.getCoursesOnDay(key)) {
                CourseButton tempButton = new CourseButton(course.getName() + " " + course.getSection(), course);
                tempButton.setBackground(new Color(209, 193, 255));
                setUpGridBagConstraints(GridBagConstraints.HORIZONTAL,
                        dayToColumn(key), course.getTime() - 4, 1);
                tempButton.setActionCommand(course.getDay() + "," + course.getName()
                        + "," + course.getSection() + "," + course.getTime());
                tempButton.addActionListener(this);
                add(tempButton, gbc);
                courses.add(tempButton);
            }

        }
    }

    //EFFECTS:Turns a day string into a column position
    private int dayToColumn(String day) {
        switch (day) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            default:
                return 0;
        }
    }

    //MODIFIES: THIS
    //EFFECTS: adds a course to the set of courses and to gui
    private void addCourse(String name, String section, String hour, String day) {
        String response = "";
        try {
            Course newCourse = new Course(name, section, Integer.parseInt(hour), day);
            response = courseManager.addCourse(newCourse);

            if (response.equals("added course")) {
                addCourseButton(newCourse);
            }
        } catch (InvalidNameException e) {
            response = "invalid course values";
        } catch (NumberFormatException e) {
            response = "not a valid hour";
        }
        addAnswer.setText(response);
    }

    //MODIFIES: this
    //EFFECTS: adds a course button according to its specs
    private void addCourseButton(Course c) {
        CourseButton newCourseButton = new CourseButton(c.getName() + " " + c.getSection(), c);
        newCourseButton.setBackground(new Color(209, 193, 255));
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, dayToColumn(c.getDay()),
                c.getTime() - 4, 1);
        newCourseButton.setActionCommand(c.getDay() + "," + c.getName()
                + "," + c.getSection() + "," + c.getTime());
        newCourseButton.addActionListener(this);
        add(newCourseButton, gbc);
        courses.add(newCourseButton);
        revalidate();
    }

    //Effects: creates a new course Gui if the course is clicked
    private void courseButtonClicked(ActionEvent e) {
        CourseButton courseButton = (CourseButton) e.getSource();
        new CourseGui(courseButton, this);
    }


    //Effects: removes a course from the list and the frame
    public void removeCourse(CourseButton courseButton) {
        courseManager.removeCourse(courseButton.getCourse());
        int removeCourseIndex = -1;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(courseButton)) {
                courseButton.setVisible(false);
                remove(courseButton);
                removeCourseIndex = i;
                pack();
                setVisible(true);
            }
        }

        if (removeCourseIndex != -1) {
            courses.remove(removeCourseIndex);
        }


    }

    //Modifies: this
    //Effects: sets up the
    private void setUpWeather() {
        try {
            WebPageReader webPageReader =
                    new WebPageReader("https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&APPID=",
                            "912a2eaa52177707e1b3e7775472dc1f");
            Weather weather = webPageReader.parseWeather();
            JLabel weatherImage = new JLabel(new ImageIcon(weather.getImage()));
            setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0,1);
            add(weatherImage, gbc);
            setupComponents.add(weatherImage);
            setUpWeatherLbls(weather);
        } catch (IOException e) {
            System.out.println("unable to load climate data");
        }
    }

    private void setUpWeatherLbls(Weather weather) {
        JLabel temp = new JLabel("Temp: " + Math.round(weather.getTemp())
                + ", max: " + Math.round(weather.getMaxTemp()) + ", min: " + Math.round(weather.getMinTemp()));
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2,1);
        add(temp, gbc);
        setupComponents.add(temp);

        JLabel weatherDesc = new JLabel("Description: " + weather.getDescription());
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1,1);
        add(weatherDesc, gbc);
        setupComponents.add(weatherDesc);
    }



}
