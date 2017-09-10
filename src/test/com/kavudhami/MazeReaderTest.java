package test.com.kavudhami;

import com.kavudhami.MazeReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kavudhami on 9/4/2017.
 */
public class MazeReaderTest {
    @Test
    public void VerifyAreMazesConstructed() {
        MazeReader mazeReader = new MazeReader();
        mazeReader.process();
        Assert.assertNotNull(mazeReader.getMaze());
    }
}
