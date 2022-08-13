/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myclasses;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Andres
 */
public class Random extends JFrame implements ActionListener{
    JPanel GamePanel = new JPanel();
    ArrayList <Pair> patternMatrix = new ArrayList();
    ArrayList<JButton> pattern = new ArrayList();
    JButton[][] arrayButtons;
    int actual = 0;
    int difficulty;
    public Random(int difficulty){
        this.difficulty = difficulty;
        init();
    }
    public void init(){
        arrayButtons = new JButton[5][5];
         this.GamePanel.setLayout(new GridLayout(5,5));
         for (int i = 0; i < 5; i++) {
             for (int j = 0; j < 5; j++) {
                 arrayButtons[i][j] = new JButton();
                 arrayButtons[i][j].setBackground(Color.white);
                 arrayButtons[i][j].setSize(50, 50);
                 arrayButtons[i][j].addActionListener(this);
                 arrayButtons[i][j].setEnabled(false);
                 this.GamePanel.add(arrayButtons[i][j]);
             }
        }
        this.setContentPane(GamePanel);
        this.setPreferredSize(new Dimension(700,500));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        setLocationRelativeTo(this);
        this.setVisible(true);
        generatePattern();
        drawPattern();  
        setEnabledAgain();
    }
    
    public void setEnabledAgain(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arrayButtons[i][j].setEnabled(true);
            }
        }
        JOptionPane.showMessageDialog(this,"Comienza! (Start!)");
    }
    public void generatePattern(){
        
        int startAtX = (int)(Math.random()*(4-0)+0);
        int startAtY = (int)(Math.random()*(4-0)+0);
        patternMatrix.add(new Pair(startAtX,startAtY));
        pattern.add(arrayButtons[startAtX][startAtY]);
        for (int i = 0; i < difficulty;) {
            int direction = (int)(Math.random()*(4-1)+1);
            Pair currentLocation = patternMatrix.get(i);
            switch(direction){
                case 1:
                    if((currentLocation.getValue()-1)>=0){
                        patternMatrix.add(new Pair(currentLocation.getKey(),currentLocation.getValue()-1));
                        pattern.add(arrayButtons[currentLocation.getKey()][currentLocation.getValue()-1]);
                        i++;
                        
                    }else continue;
                    break;
                case 2:
                    if((currentLocation.getKey()-1)>=0){
                        patternMatrix.add(new Pair(currentLocation.getKey()-1,currentLocation.getValue()));
                        pattern.add(arrayButtons[currentLocation.getKey()-1][currentLocation.getValue()]);
                        i++;
                    }else continue;
                    break;
                case 3:
                    if((currentLocation.getValue()+1)<=4){
                        patternMatrix.add(new Pair(currentLocation.getKey(),currentLocation.getValue()+1));
                        pattern.add(arrayButtons[currentLocation.getKey()][currentLocation.getValue()+1]);
                        i++;
                    }else continue;
                    break;
                case 4:
                    if((currentLocation.getKey()+1)<=4){
                        patternMatrix.add(new Pair(currentLocation.getKey()+1,currentLocation.getValue()));
                        pattern.add(arrayButtons[currentLocation.getKey()+1][currentLocation.getValue()]);
                        i++;
                    }else continue;
                    break;
            }
        }
       
        
     }
    
    public void drawPattern(){
        
        for (int i = 0; i < difficulty; i++) {
            Pair location = patternMatrix.get(i);
            this.arrayButtons[location.getKey()][location.getValue()].setBackground(Color.yellow);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Random.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.arrayButtons[location.getKey()][location.getValue()].setBackground(Color.white);
            
        }
    }
    public static void main(String[] args) {
        int difficulty = Integer.parseInt(JOptionPane.showInputDialog(null,"Inserte la dificultad del camino con un número (Insert game difficulty. Recommended: Over 7)"));
        new Random(difficulty);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton source = (JButton)ae.getSource();
        if(source == pattern.get(actual)){
            //JOptionPane.showMessageDialog(this, "Bien. Continúa.");
            actual++;
        }else{
            JOptionPane.showMessageDialog(this, "Mal. Has Tronado (You lost!).");
            this.dispose();
        }
        if(actual == difficulty){
          JOptionPane.showMessageDialog(this, "Felicidades, Ganaste! (Congratulations, you've won!)");  
          this.dispose();
        }
    }
}
