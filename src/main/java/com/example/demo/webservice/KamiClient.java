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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class KamiClient extends WebServiceGatewaySupport {

    public ExecuteResponse getAhov() throws DatatypeConfigurationException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse("2014-04-24 11:15:00");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        ChemicalStorage cs = new ChemicalStorage();
        cs.setBund(null);
        cs.setChemicalAgentName("Хлор");
        cs.setLatitude(57.526592);
        cs.setLongitude(38.319372);
        cs.setQuantity(100);
        VehicleCharacteristics vc = new VehicleCharacteristics();
        vc.setFillingTime((double) 10);
        vc.setTravelTime((double) 15);
        vc.setVolume((double) 10);
        vc.setWorkTime((double) 30);
        AhovRdTask request = new AhovRdTask();
        request.setIsPopulationInformed(true);
        request.setPredictionDateTime(xmlGregCal);
        request.setChemicalStorage(cs);
        //request.setVehicleCharacteristics(new JAXBElement<VehicleCharacteristics>(new QName(VehicleCharacteristics.class.getSimpleName()),VehicleCharacteristics.class,vc));

        Execute e = new Execute();
        e.setTask(request);

        return (ExecuteResponse) getWebServiceTemplate()
                .marshalSendAndReceive(e, new SoapActionCallback("http://kami.ru/ITaskOperationsContract/Execute"));
    }
}