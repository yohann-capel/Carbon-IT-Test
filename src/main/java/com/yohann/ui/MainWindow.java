package com.yohann.ui;

import com.yohann.Engine;
import com.yohann.exceptions.MultipleTreasureMapSizeException;
import com.yohann.models.TreasureMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class MainWindow extends JFrame {
    private final Logger logger = Logger.getLogger("MainWindow");
    private final JTextField inputFilePath;
    private final JTextField saveFilePath;

    public MainWindow() {
        super("La chasse aux trésors");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        inputFilePath = new JTextField(30);
        inputFilePath.setEditable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputFilePath, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        add(inputFileBrowseButton(), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        saveFilePath = new JTextField(35);
        saveFilePath.setEditable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(saveFilePath, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 0, 0);
        add(saveFileBrowseButton(), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 0);
        add(runButton(), gbc);
    }

    private JButton runButton() {
        JButton runButton = new JButton("Lancer");

        runButton.addActionListener(event -> {
            logger.info("Program start");

            Engine engine = new Engine(Paths.get(inputFilePath.getText()));
            try {
                TreasureMap map = engine.generateMap();
                engine.run(map);
                engine.save(map, Paths.get(saveFilePath.getText()));
                JOptionPane.showMessageDialog(this, String.format("Fichier sauvegardé à %s", saveFilePath.getText()));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Le fichier d'input n'a pas été trouvé");
            } catch (MultipleTreasureMapSizeException e) {
                JOptionPane.showMessageDialog(this, "Le fichier contient deux tailles de carte différentes");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des données");
            } finally {
                this.dispose();
            }
        });

        return runButton;
    }

    private JButton inputFileBrowseButton() {
        JButton browseButton = new JButton("Input file browse");

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers textes", "txt");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(selectedFile.getAbsolutePath().endsWith(".txt")) {
                    inputFilePath.setText(selectedFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this, "Le fichier doit etre un fichier texte");
                }
            }
        });

        return browseButton;
    }

    private JButton saveFileBrowseButton() {
        JButton browseButton = new JButton("Save file browse");

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers textes", "txt");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(!selectedFile.getAbsolutePath().endsWith(".txt")) {
                    selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                }
                saveFilePath.setText(selectedFile.getAbsolutePath());
            }
        });

        return browseButton;
    }
}
