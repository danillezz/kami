package com.example.demo.webservice;

import com.example.demo.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class KamiClient extends WebServiceGatewaySupport {

    public ExecuteResponse getAhov(String bund, double height, double area, String chemicalAgentName, double latitude, double longitude, double quantity, String quantityUnit, boolean isPopulationInformed, String predictionDateTime, long startNeutralizationTime, long timeToNeutralization, double fillingTime, double travelTime, double volume, double workTime) throws DatatypeConfigurationException, ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(predictionDateTime);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        ChemicalStorage cs = new ChemicalStorage();
        switch (bund) {
            case "Отдельный":
                SeparateBund separateBund = new SeparateBund();
                separateBund.setHeight(height);
                cs.setBund(separateBund);
                break;
            case "Общий":
                GroupBund groupBund = new GroupBund();
                groupBund.setArea(area);
                cs.setBund(groupBund);
                break;
            default:
                cs.setBund(null);
                break;
        }
        cs.setChemicalAgentName(chemicalAgentName);
        cs.setLatitude(latitude);
        cs.setLongitude(longitude);
        cs.setQuantity(quantity);
        cs.setQuantityUnit(quantityUnit);

        VehicleCharacteristics vc = new VehicleCharacteristics();
        vc.setFillingTime(fillingTime);
        vc.setTravelTime(travelTime);
        vc.setVolume(volume);
        vc.setWorkTime(workTime);
        vc.setUnit("мин");

        AhovRdTask request = new AhovRdTask();
        request.setIsPopulationInformed(isPopulationInformed);
        request.setPredictionDateTime(xmlGregCal);
        request.setChemicalStorage(cs);
        request.setVehicleCharacteristics(vc);
        request.setStartNeutralizationTime(DatatypeFactory.newInstance().newDuration(Duration.ofMinutes(startNeutralizationTime).toString()));
        request.setTimeToNeutralization(DatatypeFactory.newInstance().newDuration(Duration.ofMinutes(timeToNeutralization).toString()));
        Execute e = new Execute();
        e.setTask(request);

        return (ExecuteResponse) getWebServiceTemplate()
                .marshalSendAndReceive(e, new SoapActionCallback("http://kami.ru/ITaskOperationsContract/Execute"));
    }
}