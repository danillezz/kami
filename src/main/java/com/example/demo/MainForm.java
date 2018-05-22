package com.example.demo;

import com.example.demo.common.AhovRdTask;
import com.example.demo.webservice.KamiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.xml.datatype.DatatypeConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

@Component
public class MainForm {
    private JButton btnAhov;
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JTextField txtHeight;
    private JTextField txtArea;
    private JTextField txtChemicalAgentName;
    private JTextField txtChemLatitude;
    private JTextField txtChemLongitude;
    private JTextField txtQuantity;
    private JTextField txtQuantityUnit;
    private JTextField txtPredictionDateTime;
    private JTextField txtStartNeutralizationTime;
    private JTextField txtTimeToNeutralization;
    private JTextField txtFillingTime;
    private JTextField txtTravelTime;
    private JTextField txtVolume;
    private JTextField txtWorkTime;
    private JTextArea txtAhovResult;
    private JCheckBox cbxIsPopulationInformed;
    private JTextField txtBeginningRescueOperation;
    private JTextField txtDurationRescueOperation;
    private JTextField txtExplosiveAgent;
    private JTextField txtBlastLatitude;
    private JTextField txtBlastLongitude;
    private JTextField txtMassOfAgent;
    private JTextArea txtBlastResult;
    private JButton btnBlast;
    private JComboBox cbBund;
    private final KamiClient kamiClient;


    public JPanel getPanel() {
        return panel;
    }

    @Autowired
    public MainForm(KamiClient kamiClient) {
        this.kamiClient = kamiClient;
        btnAhov.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AhovRdTask ahovRdTask = (AhovRdTask) MainForm.this.kamiClient.getAhov().getExecuteResult();
                    txtAhovResult.setText(ahovRdTask.getResults().toString());
                } catch (DatatypeConfigurationException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
        cbBund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbBund.getSelectedIndex()==0) {
                    txtArea.setEnabled(false);
                    txtHeight.setEnabled(false);
                } else if (cbBund.getSelectedIndex()==1) {
                    txtArea.setEnabled(false);
                    txtHeight.setEnabled(true);
                } else {
                    txtArea.setEnabled(true);
                    txtHeight.setEnabled(false);
                }
            }
        });
        cbxIsPopulationInformed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbxIsPopulationInformed.isSelected())
                    cbxIsPopulationInformed.setText("Да");
                else
                    cbxIsPopulationInformed.setText("Нет");
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
