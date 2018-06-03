package packt.addressbook;

import packt.addressbook.model.Contact;
import packt.addressbook.util.ContactUtil;
import packt.util.SortUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ContactUtil contactUtil = new ContactUtil();
        SortUtil sortUtil = new SortUtil();
        List<Contact> contacts = contactUtil.getContacts();
        sortUtil.sortList(contacts);
        System.out.println(contacts);
    }
}
