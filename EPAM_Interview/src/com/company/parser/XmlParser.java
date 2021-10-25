package com.company.parser;

import com.company.constants.Constants;
import com.company.model.FreightCar;
import com.company.model.PassengerCar;
import com.company.model.Train;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    public static Train parse() {
        Train train;
        Document document = documentBuilder();
        Node cars = document.getFirstChild();
        NodeList carsNodes = cars.getChildNodes();
        Node passengerCars = null;
        Node freightCars = null;
        for (int i = 0; i < carsNodes.getLength(); i++) {
//            System.out.println(cars_nodes.item(i).getNodeName());
            if (carsNodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (carsNodes.item(i).getNodeName()) {
                case Constants.PASSENGER_CARS_TAG:
                    passengerCars = carsNodes.item(i);
                    break;
                case Constants.FREIGHT_CARS_TAG:
                    freightCars = carsNodes.item(i);
                    break;
            }

        }
        if (passengerCars == null) {
            return null;
        }
        if (freightCars == null) {
            return null;
        }
        List<PassengerCar> passengerCarsList = parsePassengerCars(passengerCars);
        List<FreightCar> freightCarsList = parseFreightCars(freightCars);
        train = new Train(passengerCarsList, freightCarsList);
        return train;
    }

    private static Document documentBuilder() {
        File file = new File("cars.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            document = documentBuilderFactory.newDocumentBuilder().parse(file);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static List<PassengerCar> parsePassengerCars(Node passengerCarsNode) {
        int passengerCapacity = 0;
        List<PassengerCar> passengerCarsList = new ArrayList<>();
        NodeList passengerCarsNodes = passengerCarsNode.getChildNodes();
        for (int i = 0; i < passengerCarsNodes.getLength(); i++) {
            if (!passengerCarsNodes.item(i).getNodeName().equals(Constants.ELEMENT_TAG)) {
                continue;
            }
            NodeList elementNodes = passengerCarsNodes.item(i).getChildNodes();
            for (int j = 0; j < elementNodes.getLength(); j++) {
                if (elementNodes.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if (!elementNodes.item(j).getNodeName().equals(Constants.PASSENGER_CAPACITY_TAG)) {
                    continue;
                }
                passengerCapacity = Integer.parseInt(elementNodes.item(j).getTextContent());

            }
            PassengerCar passengerCar = new PassengerCar(passengerCapacity);
            passengerCarsList.add(passengerCar);
        }
        return passengerCarsList;
    }

    private static List<FreightCar> parseFreightCars(Node freightCarsNode) {
        int carryingCapacity = 0;
        List<FreightCar> freightCarsList = new ArrayList<>();
        NodeList freightCarsNodes = freightCarsNode.getChildNodes();
        for (int i = 0; i < freightCarsNodes.getLength(); i++) {
            if (!freightCarsNodes.item(i).getNodeName().equals(Constants.ELEMENT_TAG)) {
                continue;
            }
            NodeList element_nodes = freightCarsNodes.item(i).getChildNodes();
            for (int j = 0; j < element_nodes.getLength(); j++) {
                if (element_nodes.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if (!element_nodes.item(j).getNodeName().equals(Constants.CARRYING_CAPACITY_TAG)) {
                    continue;
                }
                carryingCapacity = Integer.parseInt(element_nodes.item(j).getTextContent());
//                System.out.println(carrying_capacity);

            }
            FreightCar freightCar = new FreightCar(carryingCapacity);
            freightCarsList.add(freightCar);
        }
        return freightCarsList;
    }
}
