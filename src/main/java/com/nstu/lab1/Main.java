package com.nstu.lab1;

import javax.swing.SwingUtilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nstu.lab1.data.Fraction;
import com.nstu.lab1.data.Point2D;
import com.nstu.lab1.data.factories.FractionFactory;
import com.nstu.lab1.data.factories.Point2DFactory;
import com.nstu.lab1.dataStructures.ListDeserializer;
import com.nstu.lab1.dataStructures.ListSerializer;
import com.nstu.lab1.dataStructures.MyList;
import com.nstu.lab1.ui.ListManagerFrame;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        SwingUtilities.invokeLater(() -> new ListManagerFrame<>());

    }
}