import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.Spaces;

public class SpacesTest {

    @Test
    public void givenNewSpace_WhenInitialized_ThenFieldsAreSetCorrectly() {
        Spaces space = new Spaces("Conference Room", 100.0, true);

        assertEquals(100, space.getPrice());
        assertEquals(true, space.getAvailable());
    }

    @Test
    public void givenAvailableSpace_WhenSetToUnavailable_ThenAvailabilityIsUpdated() {
        Spaces space = new Spaces("Private Office", 150.0, true);

        space.setAvailable(false);
        assertFalse(space.getAvailable());
    }

    @Test
    public void givenSpace_WhenToStringCalled_ThenReturnsFormattedString() {
        Spaces space = new Spaces("Open Desk", 50.0, true);

        String spaceString = space.toString();
    
        assertTrue(spaceString.contains("Type: Open Desk"));
        assertTrue(spaceString.contains("Price: 50.0 AZN"));
    }
}
