package mylabs.app.web;

import mylabs.app.AddressBook;
import mylabs.app.BuddyInfo;
import mylabs.app.AddressBookRepository;
import mylabs.app.BuddyInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookRestController {

    private final AddressBookRepository books;
    private final BuddyInfoRepository buddies;

    public AddressBookRestController(AddressBookRepository books, BuddyInfoRepository buddies) {
        this.books = books;
        this.buddies = buddies;
    }

    @GetMapping
    public List<AddressBook> list() {
        return books.findAll();
    }

    @PostMapping
    public ResponseEntity<AddressBook> create(@RequestBody AddressBook incoming) {
        AddressBook saved = books.save(new AddressBook(incoming.getName()));
        return ResponseEntity.created(URI.create("/api/addressbooks/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> get(@PathVariable Long id) {
        return books.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{id}/buddies")
    public ResponseEntity<AddressBook> addBuddy(@PathVariable Long id, @RequestBody BuddyInfo incoming) {
        return books.findById(id).map(book -> {
            BuddyInfo b = new BuddyInfo(incoming.getName(), incoming.getAddress(), incoming.getPhone());
            book.addBuddy(b);
            books.save(book);
            return ResponseEntity.created(URI.create("/api/addressbooks/" + id)).body(book);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public ResponseEntity<Object> removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        return books.findById(id).map(book -> {
            book.getBuddies().removeIf(b -> b.getId().equals(buddyId));
            buddies.deleteById(buddyId);
            books.save(book);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}