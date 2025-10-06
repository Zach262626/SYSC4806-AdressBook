package mylabs.app;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {

    List<AddressBook> findByName(String name);

    @EntityGraph(attributePaths = "buddies")
    List<AddressBook> findAll();
}
