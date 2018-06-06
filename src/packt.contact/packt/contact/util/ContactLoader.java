package packt.contact.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import packt.contact.util.ContactLoadException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import packt.contact.model.Address;
import packt.contact.model.Contact;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import packt.contact.internal.XmlUtil;

import javax.xml.parsers.ParserConfigurationException;

public class ContactLoader {

    public List<Contact> loadContacts(String fileName) throws ContactLoadException {
        List<Contact> contacts = new ArrayList<>();
        Document doc;
        XmlUtil xmlUtil = new XmlUtil();
        try {
            doc = xmlUtil.loadXmlFile(fileName);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ContactLoadException("Unable to load Contact file");
        }
        NodeList nList = doc.getElementsByTagName("contact");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node contactNode = nList.item(temp);
            if (contactNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) contactNode;

                Contact contact = new Contact();
                contact.setFirstName(xmlUtil.getElement(contactNode, "firstname"));
                contact.setLastName(xmlUtil.getElement(contactNode, "lastname"));
                contact.setPhone(xmlUtil.getElement(contactNode, "phone"));
                Node addressNode = eElement.getElementsByTagName("address").item(0);

                Address address = new Address();
                address.setStreet(xmlUtil.getElement(addressNode, "street"));
                address.setStreet(xmlUtil.getElement(addressNode, "city"));
                address.setStreet(xmlUtil.getElement(addressNode, "state"));
                address.setStreet(xmlUtil.getElement(addressNode, "country"));
                contact.setAddress(address);

                contacts.add(contact);
            }
        }
        return contacts;
    }
}
