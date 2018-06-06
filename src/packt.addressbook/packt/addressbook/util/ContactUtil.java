package packt.addressbook.util;

import packt.contact.model.Contact;

import java.util.Arrays;
import java.util.List;

public class ContactUtil {

    public List<Contact> getContacts() {
        return Arrays.asList(
          new Contact("Edsger", "Dijkstra", "345-678-9012"),
          new Contact("Alan", "Turing", "456-789-0123"),
          new Contact("Ada", "Lovelace", "234-567-8901"),
          new Contact("Charles", "Babbage", "123-456-7890"),
          new Contact("Tim", "Berners-Lee", "456-789-0123")
        );
    }
}
