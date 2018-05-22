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
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtDateTime;
    private JTextField txtAdvancedInfo;
    private JTextField txtAirPressure;
    private JTextField txtAirTemperature;
    private JComboBox cbAtmosphereState;
    private JTextField txtCloudiness;
    private JTextField txtWeatherDateTime;
    private JTextField txtPrecipitation;
    private JTextField txtWindDirection;
    private JTextField txtWindSpeed;
    private JCheckBox cbxSnowCover;
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
                    AhovRdTask ahovRdTask = (AhovRdTask) kamiClient.getAhov(cbBund.getSelectedItem().toString(),
                            Double.valueOf(txtHeight.getText()),
                            Double.valueOf(txtArea.getText()),
                            txtChemicalAgentName.getText(),
                            Double.valueOf(txtChemLatitude.getText()),
                            Double.valueOf(txtChemLongitude.getText()),
                            Double.valueOf(txtQuantity.getText()),
                            txtQuantityUnit.getText(),
                            cbxIsPopulationInformed.isSelected(),
                            txtPredictionDateTime.getText(),
                            Long.valueOf(txtStartNeutralizationTime.getText()),
                            Long.valueOf(txtTimeToNeutralization.getText()),
                            Double.valueOf(txtFillingTime.getText()),
                            Double.valueOf(txtTravelTime.getText()),
                            Double.valueOf(txtVolume.getText()),
                            Double.valueOf(txtWorkTime.getText())).getExecuteResult();
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
