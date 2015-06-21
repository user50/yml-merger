import com.company.config.Config;
import com.company.config.ConfigProvider;
import com.company.merge.MergeService;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * Created by user50 on 21.06.2015.
 */
public class MergeServiceTest {

    @Test
    public void testMerge() throws Exception {
        Config config = new ConfigProvider().get();
        MergeService mergeService = new MergeService(config);

        new FileOutputStream("merged.yml").write(mergeService.getYml());

    }
}
