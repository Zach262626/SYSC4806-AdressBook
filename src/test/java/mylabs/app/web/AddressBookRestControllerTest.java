package mylabs.app.web;

import mylabs.app.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AddressBookRestController.class)
class AddressBookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository books;

    @MockBean
    private BuddyInfoRepository buddies;

    private AddressBook makeBook(String name, long id) {
        AddressBook book = new AddressBook(name);
        book.setId(id);
        return book;
    }

    private BuddyInfo makeBuddy(String name, String address, long phone, long id) {
        BuddyInfo buddy = new BuddyInfo(name, address, phone);
        buddy.setId(id);
        return buddy;
    }

    @Test
    void createAddressBook() throws Exception {
        AddressBook saved = makeBook("MyBook", 1L);
        when(books.save(any(AddressBook.class))).thenReturn(saved);

        String json = "{\"name\":\"MyBook\"}";

        mockMvc.perform(post("/api/addressbooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("MyBook"));
    }

    @Test
    void getAddressBook() throws Exception {
        AddressBook book = makeBook("TestBook", 1L);
        when(books.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/addressbooks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("TestBook"));
    }

    @Test
    void listAddressBooks() throws Exception {
        AddressBook book1 = makeBook("Book1", 1L);
        AddressBook book2 = makeBook("Book2", 2L);

        List<AddressBook> allBooks = List.of(book1, book2);
        when(books.findAll()).thenReturn(allBooks);

        mockMvc.perform(get("/api/addressbooks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Book1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Book2"));
    }

    @Test
    void addBuddy() throws Exception {
        AddressBook book = makeBook("MyBook", 1L);
        when(books.findById(1L)).thenReturn(Optional.of(book));
        when(books.save(any(AddressBook.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String json = "{\"name\":\"Buddy1\",\"address\":\"123 Street\",\"phone\":5555555}";

        mockMvc.perform(post("/api/addressbooks/1/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buddies[0].name").value("Buddy1"))
                .andExpect(jsonPath("$.buddies[0].address").value("123 Street"))
                .andExpect(jsonPath("$.buddies[0].phone").value(5555555));
    }

    @Test
    void removeBuddy() throws Exception {
        AddressBook book = makeBook("MyBook", 1L);
        BuddyInfo buddy = makeBuddy("Buddy1", "123 Street", 5555555L, 1L);
        book.addBuddy(buddy);

        when(books.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(delete("/api/addressbooks/1/buddies/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(buddies, times(1)).deleteById(1L);
        verify(books, times(1)).save(book);
    }
}
