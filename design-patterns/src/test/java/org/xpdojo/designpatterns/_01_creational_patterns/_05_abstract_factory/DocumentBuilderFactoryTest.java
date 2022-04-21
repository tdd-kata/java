package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentBuilderFactoryTest {

    @Test
    void sut_html_document_builder_factory() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("src/test/resources/documentBuilder.html"));

        Element documentElement = document.getDocumentElement();
        NodeList beans = documentElement.getElementsByTagName("li");
        List<String> elements = new ArrayList<>(beans.getLength());

        for (int index = 0; index < beans.getLength(); index++) {
            Node node = beans.item(index);
            Node child = node.getFirstChild();
            String value = child.getNodeValue();
            elements.add(value);
        }

        assertThat(elements).containsExactlyInAnyOrder("test1", "test2");
    }

    @Test
    void sut_xml_document_builder_factory() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xml = "src/test/resources/config.xml";
        Document document = builder.parse(new File(xml));
        NodeList beans = document.getElementsByTagName("bean");
        Node bean = beans.item(0);
        NamedNodeMap attributes = bean.getAttributes();

        Node beanId = attributes.getNamedItem("id");
        String beanIdValue = beanId.getNodeValue();
        assertThat(beanIdValue).isEqualTo("hello");

        Node className = attributes.getNamedItem("class");
        String classNameValue = className.getNodeValue();
        assertThat(classNameValue).isEqualTo(String.class.getName());
    }
}
