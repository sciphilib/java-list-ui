package com.nstu.lab1.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nstu.lab1.data.Fraction;
import com.nstu.lab1.data.Point2D;
import com.nstu.lab1.data.factories.FractionFactory;
import com.nstu.lab1.data.factories.Point2DFactory;
import com.nstu.lab1.dataStructures.ListSerializer;
import com.nstu.lab1.dataStructures.MyList;
import com.fasterxml.jackson.core.type.TypeReference;

public class ListManagerFrame<T> extends JFrame {
    private MyList<T> dataList;
    private DefaultListModel<Object> listModel;
    private JList<Object> displayList;
    private JComboBox<String> dataTypeComboBox;
    private String currentDataType;
    private JButton applyButton;
    private JButton resetButton;

    public ListManagerFrame() {
        dataList = new MyList<>();
        listModel = new DefaultListModel<>();
        displayList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(displayList);

        dataTypeComboBox = new JComboBox<>(new String[]{"Integer", "Point2D", "Fraction"});
        JButton addButton = new JButton("Add");
        JButton addByIndexButton = new JButton("AddByIndex");
        JButton removeButton = new JButton("Remove");
        JButton saveButton = new JButton("Save to JSON");
        JButton loadButton = new JButton("Load from JSON");
        JButton sortButton = new JButton("Sort");
        applyButton = new JButton("Apply");
        resetButton = new JButton("Reset");

        applyButton.addActionListener(this::applyDataType);
        resetButton.addActionListener(this::resetList);
        addButton.addActionListener(this::addItem);
        addByIndexButton.addActionListener(this::addItemByIndex);
        removeButton.addActionListener(this::removeItem);
        saveButton.addActionListener(this::saveToJson);
        loadButton.addActionListener(this::loadFromJson);
        sortButton.addActionListener(this::sortList);

        JPanel controlPanel = new JPanel();
        controlPanel.add(dataTypeComboBox);
        controlPanel.add(applyButton);
        controlPanel.add(resetButton);
        controlPanel.add(addButton);
        controlPanel.add(addByIndexButton);
        controlPanel.add(removeButton);
        controlPanel.add(sortButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("List Manager");
        this.setVisible(true);
    }

    private void addItem(ActionEvent e) {
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        T newItem = null;

        switch (dataType) {
            case "Integer":
                String numberStr = JOptionPane.showInputDialog("Enter a number:");
                newItem = (T) Integer.valueOf(numberStr);
                break;
            case "Point2D":
                newItem = (T) getPoint2DFromInput();
                break;
            case "Fraction":
                newItem = (T) getFractionFromInput();
                break;
        }

        if (newItem != null) {
            dataList.add(newItem);
            listModel.addElement(newItem);
        }
    }

    private void addItemByIndex(ActionEvent e) {
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        T newItem = null;
        String indexStr = (String) JOptionPane.showInputDialog("Enter an index:");
        int index = Integer.parseInt(indexStr);

        if (index < 0 || index > dataList.getLength()) {
            JOptionPane.showMessageDialog(this, "Incorrect index");
            return;
        }

        switch (dataType) {
            case "Integer":
                String numberStr = JOptionPane.showInputDialog("Enter a number:");
                newItem = (T) Integer.valueOf(numberStr);
                break;
            case "Point2D":
                newItem = (T) getPoint2DFromInput();
                break;
            case "Fraction":
                newItem = (T) getFractionFromInput();
                break;
        }

        if (newItem != null) {
            dataList.add(newItem, index);
            listModel.insertElementAt(newItem, index);
        }
    }

    private void applyDataType(ActionEvent e) {
        currentDataType = (String) dataTypeComboBox.getSelectedItem();
        dataList = new MyList<>();
        listModel.clear();
        dataTypeComboBox.setEnabled(false);
        resetButton.setEnabled(true);
    }

    private void resetList(ActionEvent e) {
        currentDataType = null;
        dataList = null;
        listModel.clear();
        dataTypeComboBox.setEnabled(true);
        resetButton.setEnabled(false);
    }

    private void removeItem(ActionEvent e) {
        int selectedIndex = displayList.getSelectedIndex();
        if (selectedIndex != -1) {
            dataList.remove(selectedIndex);
            listModel.remove(selectedIndex);
        }
    }

    private void saveToJson(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(MyList.class, new ListSerializer());
                mapper.registerModule(module);
                mapper.writeValue(file, dataList);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "Error saving to JSON: " + ioException.getMessage(),
                        "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadFromJson(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ObjectMapper mapper = new ObjectMapper();
                MyList<?> loadedList;
                switch (currentDataType) {
                    case "Point2D":
                        loadedList = mapper.readValue(file, new TypeReference<MyList<Point2D>>() {});
                        break;
                    case "Fraction":
                        loadedList = mapper.readValue(file, new TypeReference<MyList<Fraction>>() {});
                        break;
                    case "Integer":
                        loadedList = mapper.readValue(file, new TypeReference<MyList<Integer>>() {});
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown data type");
                }
                updateList(loadedList);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "Error loading from JSON: " + ioException.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sortList(ActionEvent e) {
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        Point2DFactory point2dFactory = new Point2DFactory();
        FractionFactory fractionFactory = new FractionFactory();

        switch (dataType) {
            case "Point2D":
                dataList.sort((Comparator<T>)point2dFactory.getComparator());
                break;
            case "Fraction":
                dataList.sort((Comparator<T>)fractionFactory.getComparator());
                break;
            case "Integer":
                dataList.sort((Comparator<T>)Comparator.naturalOrder());
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + dataType);
        }

        updateListModel();
    }

    private void updateList(MyList<?> newList) {
        listModel.clear();
        if (newList != null) {
            newList.forEach(v -> listModel.addElement(v));
        }
    }

    private void updateListModel() {
        listModel.clear();
        dataList.forEach(v -> listModel.addElement(v));
    }

    private Point2D getPoint2DFromInput() {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
    
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);
    
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            return new Point2D(x, y);
        }
        return null;
    }

    private Fraction getFractionFromInput() {
        JTextField wholePartField = new JTextField(5);
        JTextField numeratorField = new JTextField(5);
        JTextField denominatorField = new JTextField(5);
    
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Whole Part:"));
        myPanel.add(wholePartField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Numerator:"));
        myPanel.add(numeratorField);
        myPanel.add(new JLabel("Denominator:"));
        myPanel.add(denominatorField);
    
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Please Enter Whole Part, Numerator, and Denominator", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int wholePart = Integer.parseInt(wholePartField.getText());
            int numerator = Integer.parseInt(numeratorField.getText());
            int denominator = Integer.parseInt(denominatorField.getText());
            return new Fraction(wholePart, numerator, denominator);
        }
        return null;
    }
}