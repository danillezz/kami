package com.example.demo;

import com.example.demo.common.*;
import com.example.demo.webservice.KamiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MainForm extends JFrame {
    private JButton btnAhov;
    private JPanel panel;
    private JTabbedPane tabbedPane;
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
    private JCheckBox cbxWeather;
    private JPanel resultPanel;
    private KamiClient kamiClient;

    @Autowired
    public MainForm(KamiClient kamiClient) {
        this.kamiClient = kamiClient;
    }

    JPanel getPanel() {
        return panel;
    }

    JTextArea getTxtResult() {
        return txtResult;
    }

    JPanel getResultPanel() {
        return resultPanel;
    }

    void setActions() {
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
                    VehicleCharacteristics vc = ahovRdTask.getVehicleCharacteristics();
                    String vehicle = "";
                    if (vc != null) {
                        vehicle = "Время заправки цистерны, мин: " + vc.getFillingTime() + "; \n" +
                                "Время цистерны в пути, мин: " + vc.getTravelTime() + "; \n" +
                                "Объем цистерны, т: " + vc.getVolume() + "; \n" +
                                "Время работы цистерны, мин: " + vc.getWorkTime() + "; \n" +
                                "Единицы измерения: " + vc.getUnit() + "; \n";
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
                            forces +
                            "Характеристики автоцистерны:\n" +
                            vehicle);
                    tabbedPane.setSelectedIndex(3);
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
                    final String[] zones = {"Зоны, границы которой определены моментом времени и значением параметра" + "\n"};
                    if (blastTask.getResult().getZones() != null) {
                        blastTask.getResult().getZones().getZone().forEach(v -> {
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
                    if (blastTask.getResult().getPopulationLoss() != null) {
                        blastTask.getResult().getPopulationLoss().getAccidentZonePopulation().forEach(v -> {
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
                            "Код системы координат зоны оповещения: " + blastTask.getResult().getWarningZone().getCoordSystemCode() + "; \n" +
                            "Тип задействованной системы оповещения оповещения: " + blastTask.getResult().getWarningZone().getWarningSystemType() + "; \n" +
                            "Зона оповещения в формате wkt: " + blastTask.getResult().getWarningZone().getWkt() + "; \n" +
                            "Список муниципальных образований, которые должны быть оповещены, в случае, если задействованы системы оповещения уровней местная или территориальная: " + "\n"};
                    if (blastTask.getResult().getWarningZone() != null) {
                        blastTask.getResult().getWarningZone().getMuniсipalityNames().getString().forEach(v -> {
                            warningZone[0] = warningZone[0] + v + "; \n";
                        });
                    }

                    ExplosiveForces explosiveForces = (ExplosiveForces) blastTask.getResult().getForces();
                    String forces = "";
                    if (explosiveForces != null) {
                        forces = "Количество отрядов первой медицинской помощи, ед: " + explosiveForces.getBrigadesFirstMedicalAid() + "; \n" +
                                "Количество расчётов, необходимые для вскрытия защитных сооружений, ед: " + explosiveForces.getBrigadesForOpeningProtectiveConstruction() + "; \n" +
                                "Бульдозеры, шт: " + explosiveForces.getBulldozerQuantity() + "; \n" +
                                "Количество врачей отрядов первой медицинской помощи, чел: " + explosiveForces.getDoctors() + "; \n" +
                                "Количество инженерной техники для ликвидации аварий на КЭС, шт: " + explosiveForces.getEngineeringEquipments() + "; \n" +
                                "Количество пожарных отделений для локализации и тушения пожаров, ед: " + explosiveForces.getFireBrigades() + "; \n" +
                                "Количество патрульно постовых звеньев для охраны общественного порядка, ед: " + explosiveForces.getLinkOfPublicOrderProtection() + "; \n" +
                                "Количество звеньев ручной разборки завалов разрушенных зданий, ед: " + explosiveForces.getManualDeblockingGroups() + "; \n" +
                                "Количество других членов отрядов первой медицинской помощи, чел: " + explosiveForces.getMemberOfMedicalAidBrigade() + "; \n" +
                                "Количество членов патрульно постовых звеньев для охраны общественного порядка, чел: " + explosiveForces.getMemberOfPublicOrderProtection() + "; \n" +
                                "Количество участников расчистки подъездных путей, чел: " + explosiveForces.getMembersOfClearingDriveways() + "; \n" +
                                "Количество членов аварийно-технической команды по ликвидации аварий на коммунально-энергетических сетях, чел: " + explosiveForces.getMembersOfEmergencyTechnicalTeam() + "; \n" +
                                "Численность пожарных, чел: " + explosiveForces.getNumberOfFirefighters() + "; \n" +
                                "Количество среднего медицинского персонала отрядов первой медицинской помощи, чел: " + explosiveForces.getParamedicalPersonnel() + "; \n" +
                                "Охрана общественного порядка во время АСДНР при разрушении зданий, чел: " + explosiveForces.getPublicOrderProtection() + "; \n" +
                                "Количество разведчиков, при деблокирования пострадавших из-под завалов, чел: " + explosiveForces.getReconnaissanceMemberForDeblock() + "; \n" +
                                "Количество спасателей звена ручной разборки завалов разрушенных зданий, чел: " + explosiveForces.getRescuersOfManualDeblockingGroup() + "; \n";
                    }
                    txtResult.setText(
                            "Результат аварии:\n" +
                                    "Масштаб ЧС: " + blastTask.getResult().getCSScale() + "; \n" +
                                    "Площадь зоны поражения: " + blastTask.getResult().getDamageZoneArea() + "; \n" +
                                    "Периметр зоны поражения: " + blastTask.getResult().getDamageZonePerimeter() + "; \n" +
                                    "ЧС имеет место: " + blastTask.getResult().isIsEmergencySituation() + "; \n" +
                                    populationLoss[0] +
                                    zones[0] +
                                    warningZone[0] +
                                    "Силы и средства:\n" +
                                    forces);
                    tabbedPane.setSelectedIndex(3);
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

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane = new JTabbedPane();
        panel.add(tabbedPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(15, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Общее", panel1);
        final JLabel label1 = new JLabel();
        label1.setText("Уникальный идентификатор задачи");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(14, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtId = new JTextField();
        panel1.add(txtId, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Краткий заголовок задачи");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtTitle = new JTextField();
        panel1.add(txtTitle, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Дата и время аварии");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDateTime = new JTextField();
        txtDateTime.setText("2018-06-19 11:32:50");
        panel1.add(txtDateTime, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Доп. информация по ЧС");
        panel1.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtAdvancedInfo = new JTextField();
        panel1.add(txtAdvancedInfo, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Атмосферное давление, мм.рт.ст");
        panel1.add(label5, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtAirTemperature = new JTextField();
        txtAirTemperature.setText("25");
        panel1.add(txtAirTemperature, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtAirPressure = new JTextField();
        txtAirPressure.setText("768");
        panel1.add(txtAirPressure, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Температура воздуха, С");
        panel1.add(label6, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Степень вертикальной устойчивости атмосферы");
        panel1.add(label7, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAtmosphereState = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("Изотермия");
        defaultComboBoxModel1.addElement("Инверсия");
        defaultComboBoxModel1.addElement("Конвекция");
        cbAtmosphereState.setModel(defaultComboBoxModel1);
        panel1.add(cbAtmosphereState, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Облачность");
        panel1.add(label8, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtCloudiness = new JTextField();
        txtCloudiness.setText("2");
        panel1.add(txtCloudiness, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Время измерения погодных показателей");
        panel1.add(label9, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtWeatherDateTime = new JTextField();
        txtWeatherDateTime.setText("2018-06-19 12:00:00");
        panel1.add(txtWeatherDateTime, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Осадки, мм");
        panel1.add(label10, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPrecipitation = new JTextField();
        txtPrecipitation.setText("5");
        panel1.add(txtPrecipitation, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Направление ветра");
        panel1.add(label11, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtWindDirection = new JTextField();
        txtWindDirection.setText("168");
        panel1.add(txtWindDirection, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Скорость ветра, м/с");
        panel1.add(label12, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtWindSpeed = new JTextField();
        txtWindSpeed.setText("10");
        panel1.add(txtWindSpeed, new com.intellij.uiDesigner.core.GridConstraints(12, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Снежный покров");
        panel1.add(label13, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        Font label14Font = this.$$$getFont$$$(null, Font.BOLD, -1, label14.getFont());
        if (label14Font != null) label14.setFont(label14Font);
        label14.setText("Погодные условия");
        panel1.add(label14, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbxSnowCover = new JCheckBox();
        cbxSnowCover.setSelected(true);
        cbxSnowCover.setText("Есть");
        panel1.add(cbxSnowCover, new com.intellij.uiDesigner.core.GridConstraints(13, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbxWeather = new JCheckBox();
        cbxWeather.setSelected(true);
        cbxWeather.setText("Учитывать");
        panel1.add(cbxWeather, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(16, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("АХОВ", panel2);
        final JLabel label15 = new JLabel();
        label15.setText("Тип поддона обваловки");
        panel2.add(label15, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtChemicalAgentName = new JTextField();
        txtChemicalAgentName.setText("Хлор");
        panel2.add(txtChemicalAgentName, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtChemLatitude = new JTextField();
        txtChemLatitude.setText("59.2206979943052");
        panel2.add(txtChemLatitude, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtChemLongitude = new JTextField();
        txtChemLongitude.setText("39.854026436805725");
        panel2.add(txtChemLongitude, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtQuantity = new JTextField();
        txtQuantity.setText("3");
        panel2.add(txtQuantity, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtQuantityUnit = new JTextField();
        txtQuantityUnit.setText("т");
        panel2.add(txtQuantityUnit, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Химический элемент*");
        panel2.add(label16, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Широта места хранения*");
        panel2.add(label17, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Долгота места хранения*");
        panel2.add(label18, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Количество вещества*");
        panel2.add(label19, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPredictionDateTime = new JTextField();
        txtPredictionDateTime.setText("2018-06-20 11:32:50");
        panel2.add(txtPredictionDateTime, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtStartNeutralizationTime = new JTextField();
        txtStartNeutralizationTime.setText("10");
        panel2.add(txtStartNeutralizationTime, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtTimeToNeutralization = new JTextField();
        txtTimeToNeutralization.setText("30");
        panel2.add(txtTimeToNeutralization, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Дата/время прогнозирования*");
        panel2.add(label20, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Время с начала аварии до обезвр.,мин");
        panel2.add(label21, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Время с начала обезвреживания,мин");
        panel2.add(label22, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbBund = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Нет");
        defaultComboBoxModel2.addElement("Отдельный");
        defaultComboBoxModel2.addElement("Общий");
        cbBund.setModel(defaultComboBoxModel2);
        panel2.add(cbBund, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Единицы измерения");
        panel2.add(label23, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Население оповещено?");
        panel2.add(label24, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbxIsPopulationInformed = new JCheckBox();
        cbxIsPopulationInformed.setSelected(true);
        cbxIsPopulationInformed.setText("Да");
        panel2.add(cbxIsPopulationInformed, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAhov = new JButton();
        btnAhov.setText("Расчитать");
        panel2.add(btnAhov, new com.intellij.uiDesigner.core.GridConstraints(15, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("* - обязательные поля для заполнения");
        panel2.add(label25, new com.intellij.uiDesigner.core.GridConstraints(12, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtHeight = new JTextField();
        txtHeight.setEnabled(false);
        txtHeight.setText("1");
        panel2.add(txtHeight, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("Высота поддона, м");
        panel2.add(label26, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtArea = new JTextField();
        txtArea.setEnabled(false);
        txtArea.setText("1");
        panel2.add(txtArea, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Площадь разлива в поддон, м2");
        panel2.add(label27, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Взрыв", panel3);
        final JLabel label28 = new JLabel();
        label28.setText("Дата/время начала операции");
        panel3.add(label28, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtBeginningRescueOperation = new JTextField();
        txtBeginningRescueOperation.setText("2018-06-20 11:00:00");
        panel3.add(txtBeginningRescueOperation, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label29 = new JLabel();
        label29.setText("Предполагаемая длит. операции,ч*");
        panel3.add(label29, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDurationRescueOperation = new JTextField();
        txtDurationRescueOperation.setText("2");
        panel3.add(txtDurationRescueOperation, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtExplosiveAgent = new JTextField();
        txtExplosiveAgent.setText("Гексоген");
        panel3.add(txtExplosiveAgent, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtBlastLatitude = new JTextField();
        txtBlastLatitude.setText("59.2206979943052");
        panel3.add(txtBlastLatitude, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtBlastLongitude = new JTextField();
        txtBlastLongitude.setText("39.854026436805725");
        panel3.add(txtBlastLongitude, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtMassOfAgent = new JTextField();
        txtMassOfAgent.setText("2");
        panel3.add(txtMassOfAgent, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label30 = new JLabel();
        label30.setText("Наимен. взрывчатого вещества*");
        panel3.add(label30, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label31 = new JLabel();
        label31.setText("Широта места взрыва*");
        panel3.add(label31, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label32 = new JLabel();
        label32.setText("Долгота места взрыва*");
        panel3.add(label32, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label33 = new JLabel();
        label33.setText("Масса вещества, кг");
        panel3.add(label33, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnBlast = new JButton();
        btnBlast.setText("Расчитать");
        panel3.add(btnBlast, new com.intellij.uiDesigner.core.GridConstraints(7, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label34 = new JLabel();
        label34.setText("* - обязательные поля для заполнения");
        panel3.add(label34, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultPanel = new JPanel();
        resultPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Результат", resultPanel);
        txtResult = new JTextArea();
        txtResult.setLineWrap(true);
        resultPanel.add(txtResult, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
