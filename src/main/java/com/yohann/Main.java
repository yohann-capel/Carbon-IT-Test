package com.yohann;

import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TreasureMap;
import com.yohann.ui.MainWindow;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });


    }
}