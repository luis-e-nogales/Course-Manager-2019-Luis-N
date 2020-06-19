package ui;

import courses.CourseManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import exceptions.InvalidNameException;
import ui.gui.MainGui;
import network.WebPageReader;
import weather.WeatherHandler;


public class Main {
    private CourseManager manager;
    private Scanner scanner;
    private MainCourses mainCourses;
    private MainItems mainItems;
    private WeatherHandler weatherHandler;


    //Scanner code and usage of java.util.Scanner found in the Lecture lab of Basic's
    // in the project LittleLoggingCalculator
    private Main() throws IOException {
        scanner = new Scanner(System.in);
        manager = new CourseManager();
        mainCourses = new MainCourses(scanner, manager);
        mainItems = new MainItems(scanner);
        //weatherHandler = new WeatherHandler();

        MainGui mainGui = new MainGui(manager);

        //checkWeather();
        runInputs();
    }

    public static void main(String[] args) throws IOException {
        Main mainLoop = new Main();
    }

    //MODIFIES: this
    //EFFECTS: repeatedly runs commands until the exit command is placed.
    private void runInputs() throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("Would you like to load previous data? (y/n)");
        String answerToLoad = scanner.nextLine();
        if (answerToLoad.equals("y")) {
            startLoad();
        }
        while (true) {
            System.out.println("input command: (add course/view courses/remove course/save/select course)");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            String confirmation = executeCommands(command);
            System.out.println(confirmation);
        }
    }

    //MODIFIES: this
    //EFFECTS: tries to load the course manager data from previous sessions.
    private void startLoad() {
        try {
            manager.load("CourseManagerSaved.txt");
        } catch (IOException e) {
            System.out.println("Loading file not found. Could not Load.");
        } finally {
            System.out.println("Attempted to load a file");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes the user input, and prints the actions taken.
    private String executeCommands(String input) throws FileNotFoundException, UnsupportedEncodingException {
        String response = "invalid input";
        if (input.equals("add course")) {
            try {
                response = manager.addCourse(mainCourses.createCourse());
            } catch (InvalidNameException e) {
                response = e.presentException();
            }
        } else if (input.equals("view courses")) {
            manager.printAllCourses();
            response = "presented courses";
        } else if (input.equals("remove course")) {
            response = mainCourses.selectCourseIndexSetupDay("remove");
        } else if (input.equals("save")) {
            response = manager.save("CourseManagerSaved.txt");
        } else if (input.equals("select course")) {
            response = mainCourses.selectCourseIndexSetupDay("view/edit");
        }
        return response;

    }


    //Modifies: this
    //EFFECTS: checks the current weather from openweathermap.org api
    private void checkWeather() {
        try {
            WebPageReader webPageReader =
                    new WebPageReader("https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&APPID=",
                            "912a2eaa52177707e1b3e7775472dc1f");

            weatherHandler.checkWeather(webPageReader.parseWeather());
        } catch (IOException e) {
            System.out.println("unable to load climate data");
        }

    }

}
