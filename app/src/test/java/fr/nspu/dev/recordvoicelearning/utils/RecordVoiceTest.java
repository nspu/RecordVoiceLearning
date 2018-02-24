package fr.nspu.dev.recordvoicelearning.utils;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
@RunWith(RobolectricTestRunner.class)
public class RecordVoiceTest {
    public static String FILE_NAME = "test.mp4";

    RecordVoice mRecordVoice;

    @BeforeEach
    public void setUp() throws Exception {
        String fileName = FILE_NAME;
        String directory = RuntimeEnvironment.application.getExternalCacheDir().getAbsolutePath();

        mRecordVoice = new RecordVoice(fileName, directory, null, null) {
            @Override
            protected void onRecordFinish(String fileName) {
                if(false){
                    assertEquals(fileName, FILE_NAME);
                }
            }
        };
    }

    @org.junit.jupiter.api.Test
    public void defineMax() throws Exception {
        int max0 = mRecordVoice.defineMax(0, 0);
        int maxMaxValue = mRecordVoice.defineMax(0, mRecordVoice.MAX_VALUE_PROGRESSBAR);
        int maxHigherMaxValue = mRecordVoice.defineMax(0, mRecordVoice.MAX_VALUE_PROGRESSBAR + 500);
        int maxDecremente = mRecordVoice.defineMax(1000, 100);
        int maxIncrease = mRecordVoice.defineMax(90, 110);

        assertEquals(max0, 0);
        assertEquals(maxMaxValue, mRecordVoice.MAX_VALUE_PROGRESSBAR);
        assertEquals(maxHigherMaxValue, mRecordVoice.MAX_VALUE_PROGRESSBAR);
        assertEquals(maxDecremente, 999);
        assertEquals(maxIncrease, 110);
    }

    @org.junit.jupiter.api.Test
    public void Record() throws Exception {
        String directory = RuntimeEnvironment.application.getExternalCacheDir().getAbsolutePath();
        mRecordVoice.startRecording();
        Thread.sleep(50);
        mRecordVoice.stopRecording();
    }
}