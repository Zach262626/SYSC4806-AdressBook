package mylabs.app;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "addressbook_id")
    @JsonBackReference
    private AddressBook addressBook;

    protected BuddyInfo() {}

    public BuddyInfo(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public AddressBook getAddressBook() { return addressBook; }
    public void setAddressBook(AddressBook addressBook) { this.addressBook = addressBook; }

    @Override
    public String toString() {
        return "(Id: " + id + "), (Name: " + name + "), (Address: " + address + "), (Phone: " + phone + ")";
    }
}
