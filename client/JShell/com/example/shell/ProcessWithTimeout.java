package com.example.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessWithTimeout extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessWithTimeout.class);
	private Process process;
	private int exitCode = Integer.MIN_VALUE;

	public ProcessWithTimeout(Process process) {
		this.process = process;
	}

	public int waitForProcess(int timeoutMilliseconds) {
		this.start();
		try {
			this.join(timeoutMilliseconds);
		} catch (InterruptedException e) {
			this.interrupt();
		}
		return exitCode;
	}

	@Override
	public void run() {
		try {
			exitCode = process.waitFor();
		} catch (InterruptedException ignore) {
			// Do nothing
		} catch (Exception ex) {
			LOGGER.error("", ex);
		}
	}
}
