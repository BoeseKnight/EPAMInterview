package com.company.builder;

import com.company.constants.Constants;
import com.company.model.Train;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlBuilder {
    public static void build(Train train) {
        if (train == null) {
            return;
        }
        Document document = documentBuilder();
        document.appendChild(tagsCreation(document, train));
        Transformer transformer = xmlTransformer(document);
    }

    private static Document documentBuilder() {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static Transformer xmlTransformer(Document document) {
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        try {
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("train.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return transformer;
    }

    private static Element tagsCreation(Document document, Train train) {
        Element trainRoot = document.createElement(Constants.TRAIN_TAG);
        Element cars = document.createElement(Constants.CARS_TAG);
        Element trainCarsQuantity = document.createElement(Constants.CARS_QUANTITY_TAG);
        Element passengerCars = document.createElement(Constants.PASSENGER_CARS_TAG);
        Element freightCars = document.createElement(Constants.FREIGHT_CARS_TAG);
        Element trainPassengerCapacity = document.createElement(Constants.TRAIN_PASSENGER_CAPACITY_TAG);
        Element trainCarryingCapacity = document.createElement(Constants.TRAIN_CARRYING_CAPACITY_TAG);
        Text trainPassengerCapacityValue = document.createTextNode(String.valueOf(train.getPassengerCapacity()));
        Text trainCarryingCapacityValue = document.createTextNode(String.valueOf(train.getCarryingCapacity()));
        Text trainCarsQuantityValue = document.createTextNode(String.valueOf(train.getCarsQuantity()));
        trainRoot.appendChild(trainCarryingCapacity);
        trainCarryingCapacity.appendChild(trainCarryingCapacityValue);
        trainRoot.appendChild(trainPassengerCapacity);
        trainPassengerCapacity.appendChild(trainPassengerCapacityValue);
        trainRoot.appendChild(trainCarsQuantity);
        trainCarsQuantity.appendChild(trainCarsQuantityValue);
        trainRoot.appendChild(cars);
        cars.appendChild(passengerCars);
        cars.appendChild(freightCars);

        for (int i = 0; i < train.getPassengerCarsNumber(); i++) {
            Element element = document.createElement(Constants.ELEMENT_TAG);
            passengerCars.appendChild(element);
            Element passengerCapacity = document.createElement(Constants.PASSENGER_CAPACITY_TAG);
            element.appendChild(passengerCapacity);
            Text passengerCapacityValue = document.createTextNode(String.valueOf(train.getPassengerCarCapacityByIndex(i)));
            passengerCapacity.appendChild(passengerCapacityValue);
        }
        for (int i = 0; i < train.getFreightCarsNumber(); i++) {
            Element element = document.createElement(Constants.ELEMENT_TAG);
            freightCars.appendChild(element);
            Element carryingCapacity = document.createElement(Constants.CARRYING_CAPACITY_TAG);
            element.appendChild(carryingCapacity);
            Text carryingCapacityValue = document.createTextNode(String.valueOf(train.getFreightCarCapacityByIndex(i)));
            carryingCapacity.appendChild(carryingCapacityValue);
        }
        return trainRoot;
    }
}
