package com.ms.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cucumber.api.Transformer;
import cucumber.deps.com.thoughtworks.xstream.converters.collections.MapConverter;

@cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter(MapConverter.class)
public class CucMap extends Transformer<Map<String, Object>> {

	@Override
	public Map<String, Object> transform(String arg0) {
		String sheetName = arg0.split(">")[1].trim();
		String dataKey = arg0.split(">")[2].trim();
		int rowNo = Integer.valueOf(arg0.split(">")[3].trim());

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = new ExcelUtils().findRequiredDataForTestCase(sheetName, dataKey).get(rowNo);
		} catch (IOException e) {
			map.put("Error", e);
		}
		return map;
	}

}
