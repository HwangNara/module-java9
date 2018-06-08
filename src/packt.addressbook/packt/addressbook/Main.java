package packt.addressbook;

import packt.contact.model.Contact;
import packt.util.SortUtil;
import packt.contact.util.ContactLoader;
import packt.contact.util.ContactLoadException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info(">> Address book viewer application: Started");
        List<Contact> contacts = new ArrayList<>();
        ContactLoader contactLoader = new ContactLoader();
        try {
            contacts = contactLoader.loadContacts("input.xml");
        } catch (ContactLoadException e) {
            logger.warning(e.getMessage());
            System.exit(0);
        }
        SortUtil.getProviderInstanceLazy().sortList(contacts);
        System.out.println(contacts);
        logger.info(">> Address book viewer application: Completed");
    }
}
