package ui.gui;

import courses.Course;
import courses.item.Item;
import courses.item.RegularItem;
import courses.item.UrgentItem;
import exceptions.InvalidNameException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.util.ArrayList;

public class CourseGui extends JFrame implements ActionListener {
    private MainGui mainGui;
    private Course course;
    private CourseButton courseButton;
    private GridBagConstraints gbc;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private ArrayList<JTextField> textFields;
    private JLabel addItemLbl;
    private Integer numItemButtons;

    public CourseGui(CourseButton courseButton, MainGui mainGui) {
        super(courseButton.getCourse().getName() + " " + courseButton.getCourse().getSection());
        this.courseButton = courseButton;
        this.course = courseButton.getCourse();
        this.mainGui = mainGui;
        gbc = new GridBagConstraints();
        textFields = new ArrayList<>();
        numItemButtons = 0;

        setUpCourseGui();
    }

    //Modifies: this
    //Effects: sets up the course gui by adding its layout and buttons
    private void setUpCourseGui() {
        setPreferredSize(new Dimension(900, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        topPanel = new JPanel(new GridBagLayout());
        setUpGridBagConstraints(2,0,0,1);
        add(topPanel, gbc);
        bottomPanel = new JPanel(new GridBagLayout());
        setUpGridBagConstraints(2,0,1,1);
        add(bottomPanel, gbc);

        getContentPane().setBackground(new Color(209, 193, 255));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        gbc.insets = new Insets(10,15,10,15);

        setUpTopButtons();
        itemSetup();
    }

    //Modifies: this
    //Effects: Setting up the top
    private void setUpTopButtons() {
        JButton addItem = new JButton("Add Item");
        addItem.setActionCommand("Add Item");
        addItem.addActionListener(this);
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 2, 1);
        topPanel.add(addItem, gbc);
        JLabel descriptionLbl = new JLabel("Description:");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 1);
        topPanel.add(descriptionLbl, gbc);
        JLabel dateLbl = new JLabel("Date (D/M/Y):");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 1);
        topPanel.add(dateLbl, gbc);
        JLabel completedLbl = new JLabel("Completed?: (false/true)");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 1, 1);
        topPanel.add(completedLbl, gbc);
        JLabel urgentLbl = new JLabel("Urgent?: (y/n)");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 1, 1);
        topPanel.add(urgentLbl, gbc);
        setUpTextFields();
    }

    //Modifies: this
    //Effects: sets up the top text fields
    private void setUpTextFields() {
        for (int i = 0; i < 4; i++) {
            textFields.add(new JTextField());
            setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 2, 1);
            topPanel.add(textFields.get(i), gbc);
        }

        JButton removeCourse = new JButton("Remove Course");
        removeCourse.setActionCommand("Remove");
        removeCourse.addActionListener(this);
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 5);
        topPanel.add(removeCourse, gbc);

        addItemLbl = new JLabel("");
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 1, 1);
        topPanel.add(addItemLbl, gbc);
    }

    //Effects: sets up the item labels and set completed buttons
    //Modifies: this
    private void itemSetup() {
        for (Item item: course.getItems()) {
            addItemLbl(item);
        }
    }

    //Modifies: this
    //Effects: checks to see which action was performed then proceeds with appropriate response
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Remove")) {
            mainGui.removeCourse(courseButton);
            setVisible(false);
            dispose();
        } else if (e.getActionCommand().equals("Add Item")) {
            addItemLbl.setText(addItem(textFields.get(0).getText(), textFields.get(1).getText(),
                    textFields.get(2).getText(), textFields.get(3).getText()));
            bottomPanel.revalidate();
            bottomPanel.repaint();
        } else if (e.getActionCommand().equals("set completed")) {
            ItemButton itemButton = (ItemButton) e.getSource();
            itemButton.getItem().changeCompleted();
            itemButton.getTArea().setText(itemButton.getItem().toString());

        }
    }

    //Modifies: this, Course
    //Effects: adds item to the course and sets up buttons
    private String addItem(String description, String date, String completed, String urgent) {
        boolean b = Boolean.parseBoolean(completed);
        try {
            if (urgent.equals("y")) {
                UrgentItem item = new UrgentItem(description, date, b);
                course.addItem(item);
                addItemLbl(item);
                return "Added Item";
            } else {
                RegularItem item = new RegularItem(description, date, b);
                course.addItem(item);
                addItemLbl(item);
                return "Added Item";
            }
        } catch (InvalidNameException e) {
            return "invalid name";
        } catch (DateTimeException e) {
            return "invalid date";
        }
    }

    //Modifies: this
    //Effects:Adds the Label and the Button corresponding to the item on the to do list
    private void addItemLbl(Item item) {
        gbc.insets = new Insets(2,0,2,20);
        JTextArea label = new JTextArea(item.toString());
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, numItemButtons, 1);
        bottomPanel.add(label, gbc);

        ItemButton currItemComp = new ItemButton(label, "set completed", item);
        currItemComp.setActionCommand("set completed");
        currItemComp.addActionListener(this);
        setUpGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, numItemButtons, 1);
        bottomPanel.add(currItemComp, gbc);

        numItemButtons += 1;
    }


    //MODIFIES: this
    //EFFECTS:Sets grid bag constraints to the input
    private void setUpGridBagConstraints(int fill, int gridX, int gridY, int gridWidth) {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;
        gbc.fill = fill;
    }

    public CourseButton getCourseButton() {
        return courseButton;
    }
}
