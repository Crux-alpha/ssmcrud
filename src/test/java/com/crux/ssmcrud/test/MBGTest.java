package com.crux.ssmcrud.test;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MBGTest{
	public static void main(String[] args){
		List<String> warnings = new ArrayList<>();
		try{
			URL mbgXML = MBGTest.class.getClassLoader().getResource("mbg.xml");
			Objects.requireNonNull(mbgXML, "没有找到mbg.xml");
			String path = java.net.URLDecoder.decode(mbgXML.getPath(), StandardCharsets.UTF_8);
			var config = new ConfigurationParser(warnings).parseConfiguration(new File(path));
			var callback = new DefaultShellCallback(true);
			var myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		}catch(IOException | XMLParserException | InvalidConfigurationException | SQLException | InterruptedException e){
			e.printStackTrace();
		}
	}
}
