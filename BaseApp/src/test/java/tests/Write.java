package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.testng.annotations.Test;
 

public class Write {
	
	public static XSSFWorkbook workbook;
    public static XSSFSheet worksheet;
    public String ColName="Result";
    public int col_num;
	@Test
	public void WriteResult(String Ress, int DR) throws Exception
	{
	    FileInputStream file_input_stream= new FileInputStream("F:\\Vimala\\Appian_Automation\\BaseApp\\Files\\Input\\DataFile.xlsx");
	    workbook=new XSSFWorkbook(file_input_stream);
	    worksheet=workbook.getSheet("Sheet1");
	    Row Row=worksheet.getRow(0);
	
	    int sheetIndex=workbook.getSheetIndex("Sheet1");
	    DataFormatter formatter = new DataFormatter();
	    if(sheetIndex==-1)
	    {
	        System.out.println("No such sheet in file exists");
	    } else      {
	        col_num=-1;
	            for(int i=0;i<Row.getLastCellNum();i++)
	            {
	                Cell cols=Row.getCell(i);
	                String colsval=formatter.formatCellValue(cols);
	                System.out.println(colsval.trim());
	                if(colsval.trim().equalsIgnoreCase(ColName.trim()))
	                {
	                    col_num=i;
	                    break;
	                }
	            }
	//          
	            Row= worksheet.getRow(DR);
	            try
	                {
	            	
	                //get my Row which is equal to Data  Result and that colNum
	                    Cell cell=worksheet.getRow(DR).getCell(col_num);
	                    // if no cell found then it create cell
	                    if(cell==null) {
	                        cell=Row.createCell(col_num);                           
	                    }
	                    //Set Result is pass in that cell number
	                    cell.setCellValue(Ress);
	                      
	                     
	                }
	            catch (Exception e)
	            {
	                System.out.println(e.getMessage()); 
	            } 
	
	    }
	        FileOutputStream file_output_stream=new FileOutputStream("F:\\Vimala\\Appian_Automation\\BaseApp\\Files\\Input\\DataFile.xlsx");
	        workbook.write(file_output_stream);
	        file_output_stream.close();
	        if(col_num==-1) 
	        {
	            System.out.println("Column you are searching for does not exist");
	        }
	}
	
}

