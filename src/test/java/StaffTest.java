import com.my.classes.Staff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {
    private Staff staff = new Staff.Builder()
            .setId(1)
            .setName("Some Name")
            .setPosition("Some Position")
            .build();

    @Test
    void staffAsserts(){
        assertEquals(1,staff.getId());
        assertEquals("Some Name",staff.getName());
        assertEquals("Some Position", staff.getPosition());
    }
}
