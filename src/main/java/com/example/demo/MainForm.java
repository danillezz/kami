package com.example.demo;

import com.example.demo.common.AhovRdTask;
import com.example.demo.common.BlastTask;
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
    private JCheckBox cbxVehicle;
    private JCheckBox cbxWeather;
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
                            txtHeight.getText(),
                            txtArea.getText(),
                            txtChemicalAgentName.getText(),
                            txtChemLatitude.getText(),
                            txtChemLongitude.getText(),
                            txtQuantity.getText(),
                            txtQuantityUnit.getText(),
                            cbxIsPopulationInformed.isSelected(),
                            txtPredictionDateTime.getText(),
                            txtStartNeutralizationTime.getText(),
                            txtTimeToNeutralization.getText(),
                            cbxVehicle.isSelected(),
                            txtFillingTime.getText(),
                            txtTravelTime.getText(),
                            txtVolume.getText(),
                            txtWorkTime.getText(),
                            txtId.getText(),
                            txtTitle.getText(),
                            txtDateTime.getText(),
                            txtAdvancedInfo.getText(),
                            cbxWeather.isSelected(),
                            txtAirPressure.getText(),
                            txtAirTemperature.getText(),
                            cbAtmosphereState.getSelectedItem().toString(),
                            txtCloudiness.getText(),
                            txtWeatherDateTime.getText(),
                            txtPrecipitation.getText(),
                            txtWindSpeed.getText(),
                            txtWindDirection.getText(),
                            cbxSnowCover.isSelected()).getExecuteResult();
                    txtAhovResult.setText(ahovRdTask.getResults().toString());
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        btnBlast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BlastTask blastTask = (BlastTask) kamiClient.getBlast(txtBeginningRescueOperation.getText(),
                            txtDurationRescueOperation.getText(),
                            txtExplosiveAgent.getText(),
                            txtBlastLatitude.getText(),
                            txtBlastLongitude.getText(),
                            txtMassOfAgent.getText(),
                            txtId.getText(),
                            txtTitle.getText(),
                            txtDateTime.getText(),
                            txtAdvancedInfo.getText(),
                            cbxWeather.isSelected(),
                            txtAirPressure.getText(),
                            txtAirTemperature.getText(),
                            cbAtmosphereState.getSelectedItem().toString(),
                            txtCloudiness.getText(),
                            txtWeatherDateTime.getText(),
                            txtPrecipitation.getText(),
                            txtWindSpeed.getText(),
                            txtWindDirection.getText(),
                            cbxSnowCover.isSelected()).getExecuteResult();
                    txtAhovResult.setText(blastTask.getResult().toString());
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        cbBund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbBund.getSelectedIndex() == 0) {
                    txtArea.setEnabled(false);
                    txtHeight.setEnabled(false);
                } else if (cbBund.getSelectedIndex() == 1) {
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

        cbxSnowCover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbxSnowCover.isSelected())
                    cbxSnowCover.setText("Есть");
                else
                    cbxSnowCover.setText("Нет");
            }
        });

        cbxVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbxVehicle.isSelected()) {
                    cbxVehicle.setText("Учитывать");
                    txtFillingTime.setEnabled(true);
                    txtTravelTime.setEnabled(true);
                    txtVolume.setEnabled(true);
                    txtWorkTime.setEnabled(true);
                } else {
                    cbxVehicle.setText("Не учитывать");
                    txtFillingTime.setEnabled(false);
                    txtTravelTime.setEnabled(false);
                    txtVolume.setEnabled(false);
                    txtWorkTime.setEnabled(false);
                }
            }
        });
        cbxWeather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbxWeather.isSelected()) {
                    cbxWeather.setText("Учитывать");
                    txtAirPressure.setEnabled(true);
                    txtAirTemperature.setEnabled(true);
                    cbAtmosphereState.setEnabled(true);
                    txtCloudiness.setEnabled(true);
                    txtWeatherDateTime.setEnabled(true);
                    txtPrecipitation.setEnabled(true);
                    txtWindSpeed.setEnabled(true);
                    txtWindDirection.setEnabled(true);
                    cbxSnowCover.setEnabled(true);
                } else {
                    cbxWeather.setText("Не учитывать");
                    txtAirPressure.setEnabled(false);
                    txtAirTemperature.setEnabled(false);
                    cbAtmosphereState.setEnabled(false);
                    txtCloudiness.setEnabled(false);
                    txtWeatherDateTime.setEnabled(false);
                    txtPrecipitation.setEnabled(false);
                    txtWindSpeed.setEnabled(false);
                    txtWindDirection.setEnabled(false);
                    cbxSnowCover.setEnabled(false);
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
