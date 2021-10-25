package com.company;

import com.company.builder.XmlBuilder;
import com.company.model.Train;
import com.company.parser.XmlParser;

public class Main {

    public static void main(String[] args) {
        Train train = XmlParser.parse();
        XmlBuilder.build(train);
    }
}