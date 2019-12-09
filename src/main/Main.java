package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args){

        Thread t = new Thread(EnigmaWindow::new);

        t.start();
    }
}

class EnigmaWindow extends JFrame {

    private JTextField input, output;

    private Enigma enigma = new Enigma();

    EnigmaWindow(){

        super("Enigma");

        Toolkit tdefault = Toolkit.getDefaultToolkit();
        Dimension dim = tdefault.getScreenSize();
        setBounds((int) dim.getWidth()/4 + 200, (int) dim.getHeight()/4 , 320, 170);
        setResizable(false);

        Container cp = getContentPane();

        JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title.setSize(400, 100);
        title.add(new JLabel("Enigma"));
        cp.add(title, BorderLayout.NORTH);

        JPanel in_out = new JPanel(new GridLayout(2, 2));
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Insert text:"));
        in_out.add(p);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input = new JTextField(14);
        input.addActionListener(new Encrypt());
        p2.add(input);
        in_out.add(p2);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Encrypted text:"));
        in_out.add(p1);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        output = new JTextField(14);
        p3.add(output);
        in_out.add(p3);

        cp.add(in_out);

        JPanel butt =  new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton settings = new JButton("Settings");
        settings.addActionListener(new Settings());
        JButton restore = new JButton("Restore");
        restore.addActionListener(new Reset());
        butt.add(settings);
        butt.add(restore);
        cp.add(butt, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class Settings implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setEnabled(false);
            new SettingsWindow();
        }
    }

    class Reset implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                enigma = enigma.restore();
            } catch(NullPointerException e){
                JOptionPane.showMessageDialog(EnigmaWindow.this, "Please, select a configurati"
                        + "on before restoring", "No configuration selected", JOptionPane.WARNING_MESSAGE);
                new SettingsWindow();
            }
        }
    }

    class Encrypt implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try{
                output.setText(enigma.encodeSentence(input.getText()));
            } catch(NullPointerException e){
                JOptionPane.showMessageDialog(EnigmaWindow.this, "Please, select a configurati"
                        + "on before encrypting", "No configuration selected", JOptionPane.WARNING_MESSAGE);
                new SettingsWindow();
            }
        }
    }

    class SettingsWindow extends JFrame {
        private JTextField rotor1_INIT, rotor2_INIT, rotor3_INIT;
        private JComboBox<String> r1, r2, r3;

        SettingsWindow(){
            super("Enigma: Settings");

            int x = (int) EnigmaWindow.this.getLocation().getX();
            int y = (int) EnigmaWindow.this.getLocation().getY();

            setBounds(x+50, y, 170, 210);

            Container settingsCP = getContentPane();
            settingsCP.add(new JLabel("Enigma: Settings"), BorderLayout.NORTH);
            
            JPanel p0 = new JPanel(new FlowLayout(10));


            p0.add(new JLabel("R1:"));
            rotor1_INIT = new JTextField(2);
            rotor1_INIT.setText("0");
            p0.add(rotor1_INIT);
            String[] rotorOptions = {"SWISSK I", "SWISSK II", "SWISSK III"};
            r1 = new JComboBox<>(rotorOptions);
            p0.add(r1);


            JPanel p1 = new JPanel(new FlowLayout(10));

            p1.add(new JLabel("R2:"));
            rotor2_INIT = new JTextField(2);
            rotor2_INIT.setText("0");
            p1.add(rotor2_INIT);
            r2 = new JComboBox<>(rotorOptions);
            p1.add(r2);


            JPanel p2 = new JPanel(new FlowLayout(10));

            p2.add(new JLabel("R3:"));
            rotor3_INIT = new JTextField(2);
            rotor3_INIT.setText("0");
            p2.add(rotor3_INIT);
            r3 = new JComboBox<>(rotorOptions);
            p2.add(r3);


            JPanel p0_p1_p2 = new JPanel(new FlowLayout(2));
            p0_p1_p2.add(p0);
            p0_p1_p2.add(p1);
            p0_p1_p2.add(p2);

            settingsCP.add(p0_p1_p2, BorderLayout.CENTER);
            JButton accept = new JButton("Accept");
            accept.addActionListener(new AcceptSettings());
            JPanel acceptPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
            acceptPane.add(accept);
            settingsCP.add(acceptPane, BorderLayout.SOUTH);


            addWindowListener(new Restore());
            setVisible(true);
        }

        class Restore extends WindowAdapter{
            @Override
            public void windowClosing(WindowEvent e) {
                EnigmaWindow.this.setEnabled(true);
                dispose();
            }
        }

        class AcceptSettings implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String s1 = (String) r1.getSelectedItem();
                String s2 = (String) r2.getSelectedItem();
                String s3 = (String) r3.getSelectedItem();

                int p1 = Integer.parseInt(rotor1_INIT.getText());
                int p2 = Integer.parseInt(rotor2_INIT.getText());
                int p3 = Integer.parseInt(rotor3_INIT.getText());

                enigma.setWiring(s1, p1, s2, p2, s3, p3);


                EnigmaWindow.this.setEnabled(true);
                dispose();
            }
        }

    }
}
