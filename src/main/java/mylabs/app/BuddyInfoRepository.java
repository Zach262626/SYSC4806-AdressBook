package mylabs.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByName(String Name);
    List<BuddyInfo> findByAddress(String Address);
    List<BuddyInfo> findByPhone(String phone);

    BuddyInfo findById(long id);
}