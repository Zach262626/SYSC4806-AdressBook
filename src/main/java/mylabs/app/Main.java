package mylabs.app;

public class Main {
    public static void main(String[] args) {
        AddressBook book = new AddressBook("Personal");

        BuddyInfo buddy1 = new BuddyInfo("Zach", "50 ave Blue, Campbellton, NB", 5069874000L);
        BuddyInfo buddy2 = new BuddyInfo("Alice", "123 King St", 5551111L);

        // Correct: adding BuddyInfo objects
        book.addBuddy(buddy1);
        book.addBuddy(buddy2);

        System.out.println("Address book has " + book.getBuddies().size() + " buddies.");
        book.getBuddies().forEach(System.out::println);
    }
}