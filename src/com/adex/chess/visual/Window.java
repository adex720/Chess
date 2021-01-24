package com.adex.chess.visual;

import javax.swing.*;

public class Window extends JFrame {

    public final Panel PANEL = new Panel();

    public Window(){
        initUI();
    }

    private void initUI(){
        add(PANEL);

        setResizable(false);
        pack();

        setTitle("Chess");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }



}
