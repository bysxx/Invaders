package test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import EnginePrime.SoundManager;

public class SoundManagerTest {

	static SoundManager.PlayProp SoundProp;
	static SoundManager sm = SoundManager.getInstance();
	@BeforeAll
    static void testsetup() {
		sm.Initialize();
		SoundProp = sm.new PlayProp(
                "Sound" + File.separator + "BGM" + File.separator +"B_Level1.wav", "TEST");
    }

    @Test
    void testPlayClip() {
        sm.playSound(SoundProp);
		assertNotNull(sm.GetClip("TEST"));
	}

    @Test
    void testStopClip() {
		testPlayClip();
        sm.StopClip("TEST");
		assertNull(sm.GetClip("TEST"));
	}
}
