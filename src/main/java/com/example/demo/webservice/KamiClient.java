package com.example.demo.webservice;

import com.example.demo.common.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.GregorianCalendar;

public class KamiClient extends WebServiceGatewaySupport {

    private XMLGregorianCalendar StrToXMLGregorianCalendar(String dateTime) throws ParseException, DatatypeConfigurationException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(dateTime);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    private Task setCommonInfo(Task request, String id, String title, String dateTime, String advancedInfo, boolean isWeatherEnabled, String airPressure, String airTemperature, String atmosphereState, String cloudiness, String weatherDateTime, String precipitation, String windSpeed, String windDirection, boolean snowCover) {
        if (!id.isEmpty())
            request.setId(id);
        if (!title.isEmpty())
            request.setTitle(title);
        if (!dateTime.isEmpty()) {
            try {
                request.setDateTime(StrToXMLGregorianCalendar(dateTime));
            } catch (Exception ignored) {

            }
        }
        if (!advancedInfo.isEmpty())
            request.setAdvancedInfo(advancedInfo);

        if (isWeatherEnabled) {
            Weather weather = new Weather();
            try {
                weather.setAirPressure(Double.valueOf(airPressure));
            } catch (Exception ignored) {

            }
            try {
                weather.setAirTemperature(Double.valueOf(airTemperature));
            } catch (Exception ignored) {

            }
            switch (atmosphereState) {
                case "Изотермия":
                    weather.setAtmosphereState(AtmosphereState.ISOTHERMAL);
                    break;
                case "Инверсия":
                    weather.setAtmosphereState(AtmosphereState.INVERSION);
                    break;
                case "Конвекция":
                    weather.setAtmosphereState(AtmosphereState.CONVECTION);
                    break;
                default:
                    break;
            }

            try {
                weather.setCloudiness(Integer.valueOf(cloudiness));
            } catch (Exception ignored) {

            }
            try {
                weather.setPrecipitation(Double.valueOf(precipitation));
            } catch (Exception ignored) {

            }
            try {
                weather.setWindSpeed(Double.valueOf(windSpeed));
            } catch (Exception ignored) {

            }
            try {
                weather.setWindDirection(Double.valueOf(windDirection));
            } catch (Exception ignored) {

            }
            try {
                weather.setDateTime(StrToXMLGregorianCalendar(weatherDateTime));
            } catch (Exception ignored) {

            }
            weather.setSnowCover(snowCover);
            request.setWeather(weather);
        }

        return request;
    }

    //-------------расчет задачи АХОВ-----------------------------------------------------------------------------------
    public ExecuteResponse getAhov(String bund, String height, String area, String chemicalAgentName, String latitude, String longitude, String quantity, String quantityUnit, boolean isPopulationInformed, String predictionDateTime, String startNeutralizationTime, String timeToNeutralization, boolean isVehicleEnabled, String fillingTime, String travelTime, String volume, String workTime, String id, String title, String dateTime, String advancedInfo, boolean isWeatherEnabled, String airPressure, String airTemperature, String atmosphereState, String cloudiness, String weatherDateTime, String precipitation, String windSpeed, String windDirection, boolean snowCover) throws Exception {
        ChemicalStorage cs = new ChemicalStorage();
        switch (bund) {
            case "Отдельный":
                SeparateBund separateBund = new SeparateBund();
                try {
                    separateBund.setHeight(Double.valueOf(height));
                } catch (Exception ignored) {
                    separateBund.setHeight((double) 1);
                }
                cs.setBund(separateBund);
                break;
            case "Общий":
                GroupBund groupBund = new GroupBund();
                try {
                    groupBund.setArea(Double.valueOf(area));
                } catch (Exception ignored) {
                    groupBund.setArea((double) 1);
                }
                cs.setBund(groupBund);
                break;
            default:
                cs.setBund(null);
                break;
        }
        cs.setChemicalAgentName(chemicalAgentName);
        cs.setLatitude(Double.valueOf(latitude));
        cs.setLongitude(Double.valueOf(longitude));
        cs.setQuantity(Double.valueOf(quantity));
        cs.setQuantityUnit(quantityUnit);

        AhovRdTask request = new AhovRdTask();
        request.setIsPopulationInformed(isPopulationInformed);

        if (!predictionDateTime.isEmpty()) {
            try {
                request.setPredictionDateTime(StrToXMLGregorianCalendar(predictionDateTime));
            } catch (Exception ignored) {
                throw new Exception("Неверно заполнено поле Дата/время прогнозирования*");
            }
        } else
            throw new Exception("Заполните поле Дата/время прогнозирования*");

        request.setChemicalStorage(cs);
        if (isVehicleEnabled) {
            VehicleCharacteristics vc = new VehicleCharacteristics();
            try {
                vc.setFillingTime(Double.valueOf(fillingTime));
            } catch (Exception ignored) {

            }
            try {
                vc.setTravelTime(Double.valueOf(travelTime));
            } catch (Exception ignored) {

            }
            try {
                vc.setVolume(Double.valueOf(volume));
            } catch (Exception ignored) {

            }
            try {
                vc.setWorkTime(Double.valueOf(workTime));
            } catch (Exception ignored) {

            }
            vc.setUnit("мин");
            request.setVehicleCharacteristics(vc);
        }
        try {
            request.setStartNeutralizationTime(DatatypeFactory.newInstance().newDuration(Duration.ofMinutes(Long.valueOf(startNeutralizationTime)).toString()));
        } catch (Exception ignored) {

        }
        try {
            request.setTimeToNeutralization(DatatypeFactory.newInstance().newDuration(Duration.ofMinutes(Long.valueOf(timeToNeutralization)).toString()));
        } catch (Exception ignored) {

        }

        request = (AhovRdTask) setCommonInfo(request, id, title, dateTime, advancedInfo, isWeatherEnabled, airPressure, airTemperature, atmosphereState, cloudiness, weatherDateTime, precipitation, windSpeed, windDirection, snowCover);
        Execute e = new Execute();
        e.setTask(request);

        return (ExecuteResponse) getWebServiceTemplate()
                .marshalSendAndReceive(e, new SoapActionCallback("http://kami.ru/ITaskOperationsContract/Execute"));
    }

    //-------------расчет задачи ВЗРЫВ----------------------------------------------------------------------------------
    public ExecuteResponse getBlast(String beginningRescueOperation, String durationRescueOperation, String explosiveAgent, String latitude, String longitude, String massOfAgent, String id, String title, String dateTime, String advancedInfo, boolean isWeatherEnabled, String airPressure, String airTemperature, String atmosphereState, String cloudiness, String weatherDateTime, String precipitation, String windSpeed, String windDirection, boolean snowCover) throws Exception {
        BlastTask request = new BlastTask();
        try {
            request.setBeginningRescueOperation(StrToXMLGregorianCalendar(beginningRescueOperation));
        } catch (Exception ignored) {

        }
        try {
            request.setDurationRescueOperation(Integer.valueOf(durationRescueOperation));
        } catch (Exception ignored) {

        }
        if (!explosiveAgent.isEmpty())
            request.setExplosiveAgent(explosiveAgent);
        try {
            request.setLatitude(Double.valueOf(latitude));
        } catch (Exception ignored) {

        }
        try {
            request.setLongitude(Double.valueOf(longitude));
        } catch (Exception ignored) {

        }
        try {
            request.setMassOfAgent(Double.valueOf(massOfAgent));
        } catch (Exception ignored) {

        }

        request = (BlastTask)setCommonInfo(request, id, title, dateTime, advancedInfo, isWeatherEnabled, airPressure, airTemperature, atmosphereState, cloudiness, weatherDateTime, precipitation, windSpeed, windDirection, snowCover);

        Execute e = new Execute();
        e.setTask(request);

        return (ExecuteResponse) getWebServiceTemplate()
                .marshalSendAndReceive(e, new SoapActionCallback("http://kami.ru/ITaskOperationsContract/Execute"));
    }
}