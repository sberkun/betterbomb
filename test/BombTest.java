import org.junit.jupiter.api.*;
import java.util.List;
import static com.google.common.truth.Truth.assertWithMessage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BombTest {
    // You won't be able to find any passwords here, sorry!
    @Test
    @Tag("phase0")
    @DisplayName("Bomb Phase 0")
    public void testBombPhase0() {
        List<String> passwords = Bomb.readFile(Bomb.PASSWORD_FILE);
        String pass = passwords.get(0);
        assertWithMessage("Phase 0 incorrect").that(pass.hashCode()).isEqualTo(96481132);
    }

    @Test
    @Tag("phase1")
    @DisplayName("Bomb Phase 1")
    public void testBombPhase1() {
        List<String> passwords = Bomb.readFile(Bomb.PASSWORD_FILE);
        String pass = String.join("", passwords.get(1).split(" "));
        assertWithMessage("Phase 0 incorrect").that(pass.hashCode()).isEqualTo(-392154569);
    }

    @Test
    @Tag("phase2")
    @DisplayName("Bomb Phase 2")
    public void testBombPhase2() {
        List<String> passwords = Bomb.readFile(Bomb.PASSWORD_FILE);
        String pass = passwords.get(2).split(" ")[1337];
        assertWithMessage("Phase 0 incorrect").that(pass.hashCode()).isEqualTo(680369076);
    }

}

