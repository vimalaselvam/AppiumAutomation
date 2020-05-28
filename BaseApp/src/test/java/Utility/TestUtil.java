package Utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtil {
	
	
	
	
	static XSSFWorkbook wbook; 
	static XSSFSheet sheet;
	
	
	public static Object[][] getExcelData(String sheetName,String filePath)
	{
		String var_filepath=filePath;
		FileInputStream fis=null;
		try
		{
			fis=new FileInputStream(var_filepath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try {
			wbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sheet= wbook.getSheet(sheetName);
        Row row;
        System.out.println(sheet.getLastRowNum());
        Object data[][]=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for(int i=0;i<sheet.getLastRowNum();i++)
        {
        	for(int j=0;j<sheet.getRow(0).getLastCellNum();j++)
        	{
        		row =sheet.getRow(i+1);
        		try
        		{
        			if(row.getCell(j)==null)
            		{
            			data[i][j]="";
            		}
            		else
            		{
            			data[i][j]=row.getCell(j).toString();
            		}
        			
        		}
        		catch(NullPointerException e)
        		{
        			e.getMessage();
        			e.getStackTrace();
        		}
        		System.out.println(data[i][j]);
        		
        	}
        	
        }
        return data;
	}

}
