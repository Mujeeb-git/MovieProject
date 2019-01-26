package utilityLibrary;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {

	// Method to write Excel data
	@SuppressWarnings("deprecation")
	public static void writeExcel(String filePath, String sheetName, int rowNum, int colNum, String dataToWrite)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath);

		// Create an object of fis class to read excel file

		FileInputStream fis = new FileInputStream(file);

		Workbook wb = null;

		String tStr = filePath.substring(25);

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = tStr.substring(tStr.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			wb = new XSSFWorkbook(fis);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			wb = new HSSFWorkbook(fis);

		}

		// Read excel sheet by sheet name

		Sheet sheet = wb.getSheet(sheetName);

		// Create a new row and append it at last of sheet

		// Row row = sheet.createRow(rowNum);

		// Cell cell = row.createCell(colNum);

		Row r = sheet.getRow(rowNum);
		if (r == null) {
			// First cell in the row, create
			r = sheet.createRow(rowNum);
		}

		Cell cell = r.getCell(colNum);
		if (cell == null) {
			// New cell
			cell = r.createCell(colNum, Cell.CELL_TYPE_STRING);
		}

		cell.setCellValue(dataToWrite);

		// Close input stream

		fis.close();

		// Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file

		wb.write(outputStream);

		// close output stream

		outputStream.close();

	}

	// Method to return Excel data
	public static String readExcel(String filePath, String sheetName, int rowNum, int colNum) throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath);

		// Create an object of Filefis class to read excel file

		FileInputStream fis = new FileInputStream(file);

		Workbook wb = null;

		String tStr = filePath.substring(25);

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = tStr.substring(tStr.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			wb = new XSSFWorkbook(fis);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			wb = new HSSFWorkbook(fis);

		}

		// Read sheet inside the workbook by its name

		Sheet sh = wb.getSheet(sheetName);

		Row row = sh.getRow(rowNum);

		return row.getCell(colNum).getStringCellValue();

	}

}