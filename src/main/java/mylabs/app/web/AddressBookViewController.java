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

    @GetMapping("/")
    public String root() {
        return "redirect:/addressbooks";
    }

    @GetMapping("/addressbooks")
    public String home(Model model) {
        model.addAttribute("addressBooks", books.findAll());
        return "home"; // Thymeleaf template
    }

    @GetMapping("/addressbooks/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("book", books.findById(id).orElse(null));
        return "addressbook";
    }
}
