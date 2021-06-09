package com.ms.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ms.config.XmlRunner;

public class ExcelUtils {

	public List<Map<String, Object>> findRequiredDataForTestCase(String sheetName, String dataKey) throws IOException {
		Sheet datatypeSheet = XmlRunner.sheetsInMap.get(sheetName);
		CellAddress startCell = null;
		CellAddress endCell = null;

		for (int iterator = 0; iterator <= datatypeSheet.getLastRowNum(); iterator++) {
			Row currentRow = datatypeSheet.getRow(iterator);
			Iterator<Cell> celllterator;
			try {
				celllterator = currentRow.iterator();
			} catch (Exception e) {
				iterator++;
				currentRow = datatypeSheet.getRow(iterator);
				celllterator = currentRow.iterator();
			}

			while (celllterator.hasNext()) {
				Cell currentCell = celllterator.next();
				Object cellValue = "";
				if (currentCell.getCellTypeEnum() == CellType.STRING) {
					cellValue = currentCell.getStringCellValue();
				} else {
					cellValue = "Nothing to read";
				}

				if (cellValue.toString().trim().equals(dataKey) && startCell == null) {
					startCell = currentCell.getAddress();
					break;
				} else if (cellValue.toString().trim().equals(dataKey)) {
					endCell = currentCell.getAddress();
					iterator = datatypeSheet.getLastRowNum();
					break;
				}

			}
		}

		List<Map<String, Object>> dataProvider = new ArrayList<>();
		for (int row = startCell.getRow() + 1; row <= endCell.getRow(); row++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int col = startCell.getColumn() + 1; col < endCell.getColumn(); col++) {
				Cell cell = datatypeSheet.getRow(row).getCell(col);
				Object cellValue = " ";
				CellType cellType;
				try {
					cellType = cell.getCellTypeEnum();
				} catch (NullPointerException e) {
					cellType = CellType.BLANK;
				}

				if (cellType == CellType.STRING) {
					cellValue = cell.getStringCellValue();
				} else if (cellType == CellType.NUMERIC) {
					try {
						DataFormatter df = new DataFormatter();
						cellValue = df.formatCellValue(cell);
					} catch (IllegalStateException e) {
						double val = cell.getNumericCellValue();
						cellValue = (int) val;
					}

				} else if (cellType == CellType.FORMULA) {
					switch (cell.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println("Last evaluated as : " + cell.getNumericCellValue());
						cellValue = cell.getNumericCellValue();
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.println("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
						cellValue = cell.getRichStringCellValue();
						break;
					}

				} else if (cellType == CellType.BLANK) {
					cellValue = "";
				}

				map.put(datatypeSheet.getRow(startCell.getRow()).getCell(col).getStringCellValue().trim(),
						cellValue.toString().trim());
			}
			dataProvider.add(map);
		}
		return dataProvider;

	}

	public Map<String, Sheet> sheetsInMap(String workbookName) throws IOException {
		Workbook workbook = null;
		FileInputStream excelFile = new FileInputStream(new File(workbookName));
		workbook = new XSSFWorkbook(excelFile);
		Map<String, Sheet> wbData = new HashMap<String, Sheet>();

		Iterator<Sheet> sheeIterator = workbook.sheetIterator();
		while (sheeIterator.hasNext()) {
			Sheet currentSheet = sheeIterator.next();
			wbData.put(currentSheet.getSheetName(), currentSheet);
		}
		workbook.close();
		return wbData;
	}

	public void readAllSheetDataFromWorkbook(String workbookName) throws IOException {

		Workbook workbook = null;
		FileInputStream excelFile = new FileInputStream(new File(workbookName));
		workbook = new XSSFWorkbook(excelFile);
		Map<String, List<List<String>>> wbData = new HashMap<String, List<List<String>>>();

		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		while (sheetIterator.hasNext()) {
			Sheet currentSheet = sheetIterator.next();
			Iterator<Row> rowIterator = currentSheet.iterator();

			List<List<String>> sheetData = new ArrayList<List<String>>();
			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				List<String> rowData = new ArrayList<String>();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					String cellValue = "";
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						cellValue = currentCell.getStringCellValue();
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						cellValue = String.valueOf(currentCell.getNumericCellValue());
					}
					rowData.add(cellValue);

				}
				sheetData.add(rowData);
			}
			wbData.put(currentSheet.getSheetName(), sheetData);
		}
		workbook.close();

	}
	
	

	

}
