package zx.soft.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

public class ReadFileUtils {

	private static Logger logger = LoggerFactory.getLogger(ReadFileUtils.class);

	public static List<String> getFileToList(String file) {
		List<String> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(file)));) {
			String str = null;
			while ((str = br.readLine()) != null) {
				str = str.trim();
				if (str.length() > 0) {
					result.add(str);
				}
			}
			return result;
		} catch (FileNotFoundException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static List<String> getFileToListFromResources(String file) {
		List<String> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(ReadFileUtils.class.getClassLoader()
				.getResourceAsStream(file)));) {
			String str = null;
			while ((str = br.readLine()) != null) {
				str = str.trim();
				if (str.length() > 0) {
					result.add(str);
				}
			}
			return result;
		} catch (FileNotFoundException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static String getFileNameByPrefix(String dir, String prefix) {
		String result = null;
		File[] files = new File(dir).listFiles();
		for (File file : files) {
			if (file.getName().contains(prefix))
				result = file.getName();
		}
		return result;
	}

}
