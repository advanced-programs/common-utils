package zx.soft.utils.system;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProcessAnalysisTest {

	@Test
	public void testGetCurrentPidByLang() {
		assertTrue(ProcessAnalysis.getCurrentPidByLang() == ProcessAnalysis.getCurrentPidByProcfs());
	}

	@Test
	public void testGetCurrentPidByProcfs() {
		assertTrue(ProcessAnalysis.getCurrentPidByLang() == ProcessAnalysis.getCurrentPidByJNA());
	}

	@Test
	public void testGetCurrentPidByJNA() {
		assertTrue(ProcessAnalysis.getCurrentPidByProcfs() == ProcessAnalysis.getCurrentPidByJNA());
	}

}
