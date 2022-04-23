package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator;

import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class EnumerationTest {

    @Test
    void sut_enumeration() {
        Vector<String> vector = new Vector<>();
        vector.addElement("A");
        vector.addElement("B");
        vector.addElement("C");
        vector.addElement("D");
        vector.addElement("E");

        Enumeration<String> enumeration1 = vector.elements();
        while (enumeration1.hasMoreElements()) {
            System.out.println(enumeration1.nextElement());
        }
        assertThat(enumeration1.hasMoreElements())
                .isFalse();

        Enumeration<String> enumeration2 = vector.elements();
        assertThat(enumeration2.asIterator())
                .toIterable()
                .containsExactly("A", "B", "C", "D", "E");
    }

    @Test
    void sut_stax() throws FileNotFoundException, XMLStreamException {

        // Streaming API for XML(StAX), 이터레이터 기반의 API
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        String xml = "src/test/resources/book.xml";
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xml));
        assertThat(reader.getClass().getName()).isEqualTo("com.sun.xml.internal.stream.XMLEventReaderImpl");

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                QName name = startElement.getName();
                System.out.println(name);
                if (name.getLocalPart().equals("book")) {
                    Attribute title = startElement.getAttributeByName(new QName("title"));
                    System.out.println(title.getValue());
                }
            }
        }
    }
}
