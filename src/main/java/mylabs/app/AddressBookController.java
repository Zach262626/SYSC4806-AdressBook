package mylabs.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class AddressBookController {

    private final AddressBookRepository bookRepo;

    public AddressBookController(AddressBookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "index";
    }

    @PostMapping("/addressbooks")
    public String createBook(@RequestParam String name) {
        AddressBook book = new AddressBook(name);
        bookRepo.save(book);
        return "redirect:/";
    }

    @GetMapping("/addressbooks/{id}")
    public String showBook(@PathVariable Long id, Model model) {
        Optional<AddressBook> ob = bookRepo.findById(id);
        if (ob.isEmpty()) return "notfound";
        model.addAttribute("book", ob.get());
        return "book";
    }

    //Add buddy with regular form
    @PostMapping("/addressbooks/{id}/buddies")
    public String addBuddyForm(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String address,
                               @RequestParam String phone) {
        AddressBook book = bookRepo.findById(id).orElseThrow();
        book.addBuddy(new BuddyInfo(name, address, phone));
        bookRepo.save(book);
        return "redirect:/addressbooks/" + id;
    }
}


