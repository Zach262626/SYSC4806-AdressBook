package mylabs.app.web;

import mylabs.app.AddressBook;
import mylabs.app.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addressbooks")
public class AddressBookViewController {

    private final AddressBookRepository books;

    public AddressBookViewController(AddressBookRepository books) {
        this.books = books;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("addressBooks", books.findAll());
        return "home";
    }

    // Example: http://localhost:8080/addressbooks/1/view
    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("book", books.findById(id).orElse(null));
        return "addressbook";
    }

    @PostMapping
    public String create(String name) {
        books.save(new AddressBook(name));
        return "redirect:/addressbooks";
    }

}
