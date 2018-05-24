package com.example.demo;

import com.example.demo.common.AhovRdForces;
import com.example.demo.common.AhovRdTask;
import com.example.demo.common.BlastTask;
import com.example.demo.webservice.KamiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextArea txtResult;
    private JCheckBox cbxIsPopulationInformed;
    private JTextField txtBeginningRescueOperation;
    private JTextField txtDurationRescueOperation;
    private JTextField txtExplosiveAgent;
    private JTextField txtBlastLatitude;
    private JTextField txtBlastLongitude;
    private JTextField txtMassOfAgent;
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
    private JPanel resultPanel;
    private final KamiClient kamiClient;

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getTxtResult() {
        return txtResult;
    }

    public JPanel getResultPanel() {
        return resultPanel;
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

                    final String[] zones = {"Зоны, границы которой определены моментом времени и значением параметра" + "\n"};
                    if (ahovRdTask.getResults().getZones() != null) {
                        ahovRdTask.getResults().getZones().getZone().forEach(v -> {
                            zones[0] = zones[0] + "Наименование: " + v.getName() + "; \n" +
                                    "Дата/время: " + v.getDateTime().toString() + "; \n" +
                                    "Наименование группы: " + v.getGroup() + "; \n" +
                                    "Значение определяющее границы зоны: " + v.getValue() + "; \n" +
                                    "Единицы измерения: " + v.getUnits() + "; \n" +
                                    "Зона, представленная в формате WKT: " + v.getWkt() + "; \n" +
                                    "Порядок отображения зоны на карте: " + v.getZOrder() + "; \n";
                        });
                    }
                    final String[] populationLoss = {"Население в зоне ЧС" + "\n"};
                    if (ahovRdTask.getResults().getPopulationLoss() != null) {
                        ahovRdTask.getResults().getPopulationLoss().getAccidentZonePopulation().forEach(v -> {
                            populationLoss[0] = populationLoss[0] + "Наименование: " + v.getName() + "; \n" +
                                    "Доля площади поражения, %: " + v.getAffectedArea() + "; \n" +
                                    "Население в зоне ЧС: " + v.getPopulationInAccidentZone() + "; \n";
                            if (v.getAffectedPeopleInfo() != null) {
                                populationLoss[0] = populationLoss[0] + "Количество пострадавших: " + v.getAffectedPeopleInfo().getAffectedPeople() + "; \n" +
                                        "Дата/время : " + v.getAffectedPeopleInfo().getDateTime().toString() + "; \n";
                                if (v.getAffectedPeopleInfo().getCasualties() != null) {
                                    populationLoss[0] = populationLoss[0] + "Тяжелые повреждения: " + v.getAffectedPeopleInfo().getCasualties().getSeriouslyInjured() + "; \n" +
                                            "Легкие повреждения: " + v.getAffectedPeopleInfo().getCasualties().getSlightlyInjured() + "; \n" +
                                            "Ранено: " + v.getAffectedPeopleInfo().getCasualties().getInjured() + "; \n" +
                                            "Убито: " + v.getAffectedPeopleInfo().getCasualties().getKilled() + "; \n";
                                }
                            }
                        });
                    }
                    final String[] warningZone = {"Зона оповещения" + "\n" +
                            "Код системы координат зоны оповещения: " + ahovRdTask.getResults().getWarningZone().getCoordSystemCode() + "; \n" +
                            "Тип задействованной системы оповещения оповещения: " + ahovRdTask.getResults().getWarningZone().getWarningSystemType() + "; \n" +
                            "Зона оповещения в формате wkt: " + ahovRdTask.getResults().getWarningZone().getWkt() + "; \n" +
                            "Список муниципальных образований, которые должны быть оповещены, в случае, если задействованы системы оповещения уровней местная или территориальная: " + "\n"};
                    if (ahovRdTask.getResults().getWarningZone() != null) {
                        ahovRdTask.getResults().getWarningZone().getMuniсipalityNames().getString().forEach(v -> {
                            warningZone[0] = warningZone[0] + v + "; \n";
                        });
                    }

                    AhovRdForces ahovRdForces = (AhovRdForces) ahovRdTask.getResults().getForces();
                    String forces = "";
                    if (ahovRdForces != null) {
                        forces = "Количество необходимых противогазов: " + ahovRdForces.getGasMaskQuantity() + "; \n" +
                                "Марки противогазов, используемых для защиты: " + ahovRdForces.getMarkGasMask() + "; \n";
                        if (ahovRdForces.getNeutralization() != null) {
                            forces = "Силы и средства, необходимые для обезвреживания АХОВ: \n" +
                                    "Количество машин для нейтрализации, шт: " + ahovRdForces.getNeutralization().getCisternQuantity() + "; \n" +
                                    "Количество машинорейсов с растворами, шт: " + ahovRdForces.getNeutralization().getCisternRoutQuantity() + "; \n" +
                                    "Наименование нейтрализатора : " + ahovRdForces.getNeutralization().getNeutralizerName() + "; \n" +
                                    "Единицы измерения нейтрализатора: " + ahovRdForces.getNeutralization().getNeutralizerUnit() + "; \n" +
                                    "Количество раствора, т: " + ahovRdForces.getNeutralization().getNeutralizerVolume() + "; \n" +
                                    "Время цикла машины для нейтрализации, мин." + ahovRdForces.getNeutralization().getTimeOfCisternWorkCycle() + "; \n";
                        }
                        if (ahovRdForces.getWaterCurtain() != null) {
                            forces = "Силы и средства, необходимые для постановки водяной завесы: \n" +
                                    "Количество машин в смене: " + ahovRdForces.getWaterCurtain().getMachinesQuantityInShift() + "; \n" +
                                    "Общее количество машин: " + ahovRdForces.getWaterCurtain().getMachinesTotalQuantity() + "; \n" +
                                    "Интенсивность подачи нейтрализатора, т/мин: " + ahovRdForces.getWaterCurtain().getNeutralizerFlowRate() + "; \n" +
                                    "Количество нейтрализатора, т: " + ahovRdForces.getWaterCurtain().getNeutralizerQuantity() + "; \n" +
                                    "Продолжительность завесы, мин: " + ahovRdForces.getWaterCurtain().getTimeOfCurtain() + "; \n";
                        }
                    }
                    txtResult.setText("Химическое поражение:" + "\n" +
                            "Глубина зоны заражения для первичного облака, км: " + ahovRdTask.getResults().getDepthContaminationZone1() + "; \n" +
                            "Глубина зоны заражения для вторичного облака, км: " + ahovRdTask.getResults().getDepthContaminationZone2() + "; \n" +
                            "Общая глубина зоны заражения, км: " + ahovRdTask.getResults().getDepthContaminationZoneAll() + "; \n" +
                            "Время испарения АХОВ с площади разлива, ч.: " + ahovRdTask.getResults().getDurationExposureSource() + "; \n" +
                            "Эквивалентное количество вещества во первичном облаке, т.: " + ahovRdTask.getResults().getQuantity1() + "; \n" +
                            "Эквивалентное количество вещества во вторичном облаке, т.: " + ahovRdTask.getResults().getQuantity2() + "; \n" +
                            "Площадь зоны фактического заражения первичного облака, кв. км: " + ahovRdTask.getResults().getSActual1() + "; \n" +
                            "Площадь зоны фактического заражения вторичного облака, кв. км: " + ahovRdTask.getResults().getSActual2() + "; \n" +
                            "Площадь зоны фактического заражения, кв. км Общая: " + ahovRdTask.getResults().getSActualAll() + "; \n" +
                            "Площадь зоны возможного заражения для первичного облака, кв. км: " + ahovRdTask.getResults().getSPossible1() + "; \n" +
                            "Площадь зоны возможного заражения для вторичного облака, кв. км: " + ahovRdTask.getResults().getSPossible2() + "; \n" +
                            "Результат аварии:\n" +
                            "Масштаб ЧС: " + ahovRdTask.getResults().getCSScale() + "; \n" +
                            "Площадь зоны поражения: " + ahovRdTask.getResults().getDamageZoneArea() + "; \n" +
                            "Периметр зоны поражения: " + ahovRdTask.getResults().getDamageZonePerimeter() + "; \n" +
                            "ЧС имеет место: " + ahovRdTask.getResults().isIsEmergencySituation() + "; \n" +
                            populationLoss[0] +
                            zones[0] +
                            warningZone[0] +
                            "Силы и средства:\n" +
                            forces);
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
                    txtResult.setText(blastTask.getResult().toString());
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
