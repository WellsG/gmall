package com.example.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellExecutor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShellExecutor.class);
	private static final int timeoutMilliseconds = 1000 * 60 * 60;
	private static final String LINE_FEED = "\n";

	String executeCommand(String command, String[] envp, String dirPath) {
		LOGGER.info("CMD:>> {}", command);
		StringBuffer output = new StringBuffer();
		StringBuffer errors = new StringBuffer();
		BufferedReader reader = null;
		BufferedReader errorReader = null;
		Process p;
		try {
			File dir = new File(dirPath);
			p = Runtime.getRuntime().exec(command, null, dir);
			ProcessWithTimeout pwt = new ProcessWithTimeout(p);
			int exitCode = pwt.waitForProcess(timeoutMilliseconds);

			if (exitCode == Integer.MIN_VALUE) {
				throw new Exception("Exec command " + command + " timeout in " + timeoutMilliseconds + " milliseconds");
			}
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			errorReader = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line).append(LINE_FEED);
			}
			while ((line = errorReader.readLine()) != null) {
				errors.append(line).append(LINE_FEED);
			}
		} catch (Exception e) {
			LOGGER.error("Execute cmd error.", e);
			return e.getMessage();
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(errorReader);
		}
		LOGGER.info("Result:\n {}",
				StringUtils.isNotBlank(errors.toString()) ? errors.toString()
						: output.toString());
		return StringUtils.isNotBlank(errors.toString()) ? errors.toString() : output.toString();

	}

}
