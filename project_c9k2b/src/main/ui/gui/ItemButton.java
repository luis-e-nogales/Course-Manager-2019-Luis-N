package ui.gui;

import courses.item.Item;

import javax.swing.*;
import java.util.Objects;

public class ItemButton extends JButton {
    private JTextArea label;
    private String labelDesc;
    private Item item;

    //Modifies: this
    //EFFECTS: generates Item Button
    public ItemButton(JTextArea itemLabel, String text, Item item) {
        super(text);
        label = itemLabel;
        labelDesc = itemLabel.getText();
        this.item = item;
    }


    public JTextArea getTArea() {
        return label;
    }

    public Item getItem() {
        return item;
    }
}
