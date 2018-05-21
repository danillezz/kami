package com.example.demo;

import com.example.demo.common.AhovRdTask;
import com.example.demo.webservice.KamiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.xml.datatype.DatatypeConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

@Component
public class MainForm {
    private JButton button1;
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextArea textArea1;
    private JCheckBox checkBox1;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JTextField textField19;
    private JTextField textField20;
    private JTextField textField21;
    private JTextArea textArea2;
    private JButton button2;
    private final KamiClient kamiClient;


    public JPanel getPanel() {
        return panel;
    }

    @Autowired
    public MainForm(KamiClient kamiClient) {
        this.kamiClient = kamiClient;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AhovRdTask ahovRdTask = (AhovRdTask) MainForm.this.kamiClient.getAhov().getExecuteResult();
                    JOptionPane.showMessageDialog(null,ahovRdTask.getResults().getQuantity1());
                } catch (DatatypeConfigurationException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
