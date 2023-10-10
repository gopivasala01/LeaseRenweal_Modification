package mainPackage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import net.sourceforge.tess4j.Tesseract;

public class TessaractTest 
{
	
	
	public static String pdfScreenShot(File newFile) throws Exception 
	{
		
		
		try
		{
		//File newFile = new File ("C:\\SantoshMurthyP\\Lease Audit Automation\\Lease_923_924_619_W_Sabine_ATX_Cloteaux.pdf");
		 //File newFile = RunnerClass.getLastModified();
		 PDDocument pdfDocument = PDDocument.load(newFile);
		 PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
		 String targetText1 = "x Option 1: Waive Renters Insurance"; //Tenant will pay Landlord monthly rent in the amount of";
		 String targetText2 = "x Option 2: Purchase a Renters Insurance Policy";
		// String targetText2 = "(X)) monthly installments,"; //on or before the 1° day of each month, in the amount";
		 //Rectangle textCoordinates = textStripper.getTextBounds("monthly installments, Tenant will pay Landlord monthly rent in the amount of");
		
		 for (int page = 0; page < pdfDocument.getNumberOfPages(); ++page) {
				 BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
		         // Crop the image based on the specified coordinates
		        // BufferedImage croppedImage = bim.getSubimage(x, y, width, height);
		         File outputFile = new File("C:\\SantoshMurthyP\\Tessaract Images\\Image.jpeg");
		         ImageIO.write(bim, "jpeg", outputFile);
		        // System.out.println( "Image has been extracted successfully");
				  
			     Tesseract tesseract = new Tesseract();

				 tesseract.setDatapath("F:\\Eclipse Workspace\\Gopi\\Lease-Close-Outs-2.0\\tessdata");

				 //image.setLanguage(“eng”);
				 try {
				   String text= tesseract.doOCR(new File(AppConfig.pdfImage+"Image.jpeg"));
				   System.out.print(text);
				   if(text.contains(targetText1)) { //|| text.contains(targetText2)
					   System.out.println("Option 1 is selected");
					   return "Option 1";
				   }
				   else
					   if(text.contains(targetText2)) { //|| text.contains(targetText2)
						   System.out.println("Option 2 is selected");
						   return "Option 2";
					   }
				   }
				 catch(Exception e) 
				 {
					 return "Error";
				    //System.out.println("Exception "+e);
				   }
				      
	        }
		 // Closing the PDF document
	        pdfDocument.close();
		}
		catch(Exception e)
		{
			return "Error";
		}
		return "Error";
		
		       
	 }
	
	
	public static String floridaLiquidizedAddendumOptionCheck(File newFile) throws Exception 
	{
		try
		{
		//File newFile = new File ("C:\\SantoshMurthyP\\Lease Audit Automation\\Lease_923_924_619_W_Sabine_ATX_Cloteaux.pdf");
		 //File newFile = RunnerClass.getLastModified();
		 PDDocument pdfDocument = PDDocument.load(newFile);
		 PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
		 String targetText1 = "[ X]1/We agree"; //Tenant will pay Landlord monthly rent in the amount of";
		 //String targetText2 = "x Option 2: Purchase a Renters Insurance Policy";
		// String targetText2 = "(X)) monthly installments,"; //on or before the 1° day of each month, in the amount";
		 //Rectangle textCoordinates = textStripper.getTextBounds("monthly installments, Tenant will pay Landlord monthly rent in the amount of");
		
		 for (int page = 21; page < pdfDocument.getNumberOfPages(); ++page) {
				 BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
		         // Crop the image based on the specified coordinates
		        // BufferedImage croppedImage = bim.getSubimage(x, y, width, height);
		         File outputFile = new File("C:\\SantoshMurthyP\\Tessaract Images\\Image.jpeg");
		         ImageIO.write(bim, "jpeg", outputFile);
		        // System.out.println( "Image has been extracted successfully");
				  
			     Tesseract tesseract = new Tesseract();

				 tesseract.setDatapath("F:\\Eclipse Workspace\\Gopi\\Lease-Close-Outs-2.0\\tessdata");

				 //image.setLanguage(“eng”);
				 try {
				   String text= tesseract.doOCR(new File(AppConfig.pdfImage+"Image.jpeg"));
				   System.out.print(text);
				   if(text.contains(targetText1)) { //|| text.contains(targetText2)
					   System.out.println("Option 1 is selected");
					   return "Option 1";
				   }
				   //else
					  // return "Error";
				   }
				 catch(Exception e) 
				 {
					 return "Error";
				    //System.out.println("Exception "+e);
				   }
				      
	        }
		 // Closing the PDF document
	        pdfDocument.close();
		}
		catch(Exception e)
		{
			return "Error";
		}
		return "Error";
		
		       
	 }

}
