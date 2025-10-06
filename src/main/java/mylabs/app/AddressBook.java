package mylabs.app;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BuddyInfo> buddies = new ArrayList<>();

    protected AddressBook() {}
    public AddressBook(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<BuddyInfo> getBuddies() { return buddies; }

    public void addBuddy(BuddyInfo b) {
        buddies.add(b);
        b.setAddressBook(this);
    }

    public void removeBuddy(BuddyInfo b) {
        buddies.remove(b);
        b.setAddressBook(null);
    }
}
