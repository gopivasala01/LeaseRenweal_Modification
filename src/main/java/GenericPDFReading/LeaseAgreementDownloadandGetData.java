package GenericPDFReading;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import mainPackage.Locators;
import mainPackage.PDFReader;
import mainPackage.PropertyWare;
import mainPackage.RunnerClass;

public class LeaseAgreementDownloadandGetData {
	
	public static String text="";
	public static String modifiedtext="";
	public static String commencementDate ="";
	public static String expirationDate ="";
	public static String monthlyRent = "";
	public static String monthlyTaxRent = "";
	public static String totalMonthlyRentWithTax = "";
	public static String proratedRent = "";
	public static String petRent = "";
	public static String rbpAmount = "";
	public static boolean petRentFlag=false;
	public static boolean monthlyTaxAmountFlag=false;
	public static boolean rbpFlag=false;
	
	public static boolean downloadLeaseAgreement() {
		try {
			PropertyWare.intermittentPopUp();
			RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(2));
			RunnerClass.driver.findElement(Locators.notesAndDocs).click();
			Thread.sleep(2000);
			List<WebElement> documents = RunnerClass.driver.findElements(Locators.documentsList);
			boolean checkLeaseAgreementAvailable = false;
			 
			for(int i =0;i<documents.size();i++)
			{
				for(int j=0;j<GenericPDFReading.AppConfig.renewalLeaseAgreementFileNames.length;j++)
				{
				 if(documents.get(i).getText().startsWith(GenericPDFReading.AppConfig.renewalLeaseAgreementFileNames[j])&&!documents.get(i).getText().contains("Termination")&&!documents.get(i).getText().contains("_Mod"))//&&documents.get(i).getText().contains(AppConfig.getCompanyCode(RunnerClass.company)))
				 {
				 	documents.get(i).click();
				 	System.out.println(documents.get(i));
					checkLeaseAgreementAvailable = true;
					break;
				 }
				}
				if(checkLeaseAgreementAvailable == true)
					break;
			}
			if(checkLeaseAgreementAvailable==false)
			{
				System.out.println("Unable to download Lease Agreement");
			    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Unable to download Lease Agreement";
				return false;
			}
		 
		}
		catch(Exception e) {
			System.out.println("Unable to download Lease Agreement");
		    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Unable to download Lease Agreement";
			return false;
		}
		
			 
		
		return true;
		
	}
	
	public static boolean getDataFromLeaseAgreement() throws Exception {
	 	
		File file;
		Thread.sleep(10000);
		if(RunnerClass.getLastModified() !=null) {
			while (true) {
		 	    file = RunnerClass.getLastModified();
		 	    if (file.getName().endsWith(".crdownload")) {
		 	        try {
		 	            Thread.sleep(5000);
		 	        } catch (InterruptedException e) {
		 	            
		 	        	e.printStackTrace();
		 	            // Handle the InterruptedException if needed
		 	        }
		 	    } else {
		 	        // Break the loop if the file name does not end with ".crdownload"
		 	        break;
		 	    }
		 	}
		}
		file = RunnerClass.getLastModified();
		
	 	if(file !=null) {
	 	 	FileInputStream fis = new FileInputStream(file);
			PDDocument document = PDDocument.load(fis);
		    text = new PDFTextStripper().getText(document);
		    text = text.replaceAll(System.lineSeparator(), " ");
		    text = text.trim().replaceAll(" +", " ");
		    text = text.toLowerCase();
		    //System.out.println(text);
		    System.out.println("------------------------------------------------------------------");
		    
		    // Monthly Rent
		    
		    
		    commencementDate = LeaseAgreementDownloadandGetData.getDates(AppConfig.commencementDateFromPDF);
		    System.out.println("Start date = "+ commencementDate);
		    expirationDate = LeaseAgreementDownloadandGetData.getDates(AppConfig.expirationDateFromPDF);
		    System.out.println("End date = "+ expirationDate);
		    monthlyRent = LeaseAgreementDownloadandGetData.getValues(AppConfig.monthlyRentFromPDF);
		    System.out.println("Monthly Rent Amount = "+ monthlyRent);
		    monthlyTaxAmountFlag = LeaseAgreementDownloadandGetData.getFlags(AppConfig.monthlyRentTaxAmountAvailablityCheck);
		    if(monthlyTaxAmountFlag == true) {
		    	monthlyTaxRent = LeaseAgreementDownloadandGetData.getValues(AppConfig.monthlyRentTaxAmountFromPDF);
		    	System.out.println("Monthly Tax Amount = "+ monthlyTaxRent);
		    	if(!monthlyRent.equals("Error") || !monthlyTaxRent.equals("Error")) {
		    		Float totalMonthlyRentWithTaxRaw = Float.parseFloat(monthlyRent)+Float.parseFloat(monthlyTaxRent);
		    		totalMonthlyRentWithTax = String.valueOf(totalMonthlyRentWithTaxRaw);
		    		System.out.println("Total Monthly rent with Tax Amount = "+ totalMonthlyRentWithTax);
		    	}
		    	
		    }
		    proratedRent = LeaseAgreementDownloadandGetData.getValues(AppConfig.proratedRentFromPDF);
		    System.out.println("Prorated Amount = "+ proratedRent);
		    petRentFlag = LeaseAgreementDownloadandGetData.getFlags(AppConfig.petRentAvailablityCheck);
		    if(petRentFlag == true) {
		    	petRent = LeaseAgreementDownloadandGetData.getValues(AppConfig.petRentFromPDF);
		    	System.out.println("Pet Rent Amount = "+ petRent);
		    }
		    rbpFlag = LeaseAgreementDownloadandGetData.getFlags(AppConfig.rbpAvailabilityCheck);
		    if(rbpFlag == true) {
		    	rbpAmount = LeaseAgreementDownloadandGetData.getValues(AppConfig.rbpFromPDF);
		    	System.out.println("RBP Amount = "+ rbpAmount);
		    }
		    
		   /* for(int i =0;i<GenericPDFReading.AppConfig.monthlyRentFromPDF.length;i++)
			{
		    	GenericPDFReading.AppConfig.monthlyRentFromPDF[i] = GenericPDFReading.AppConfig.monthlyRentFromPDF[i].toLowerCase();
		    	try {
		    		if(text.contains(GenericPDFReading.AppConfig.monthlyRentFromPDF[i])) {
		    			monthlyRent = text.substring(text.indexOf(GenericPDFReading.AppConfig.monthlyRentFromPDF[i])+GenericPDFReading.AppConfig.monthlyRentFromPDF[i].length()).trim().split(" ")[0].trim();
		    			    if(monthlyRent.contains("*"))
		    			    {
		    			    	monthlyRent = monthlyRent.replace("*","");
		    			    }
		    			    if(monthlyRent.endsWith(","))
		    			    {
		    			    	monthlyRent = monthlyRent.substring(0,monthlyRent.length()-1);
		    			    }
		    			    if(monthlyRent.matches(".*[a-zA-Z]+.*"))
						    {
						    	monthlyRent = "Error";
						    }
		    			    break;
		    		}
		    	}
		    	catch(Exception e) {
		    		 monthlyRent = "Error";
			    	 e.printStackTrace();
		    	}
		    	
			}
		    System.out.println("Monthly Rent = "+monthlyRent.trim()); */
		    
		    /*for(int i =0;i<GenericPDFReading.AppConfig.proratedRentFromPDF.length;i++)
			{
		    	GenericPDFReading.AppConfig.proratedRentFromPDF[i] = GenericPDFReading.AppConfig.proratedRentFromPDF[i].toLowerCase();
		    	try {
		    		if(text.contains(GenericPDFReading.AppConfig.proratedRentFromPDF[i])) {
		    			
		    			modifiedtext = text.substring(text.indexOf(GenericPDFReading.AppConfig.proratedRentFromPDF[i].split("|")[0])+GenericPDFReading.AppConfig.proratedRentFromPDF[i].split("|")[0].length()).trim();
		    			String proratedRentRaw = modifiedtext.substring(text.indexOf(GenericPDFReading.AppConfig.proratedRentFromPDF[i].split("|")[1])+GenericPDFReading.AppConfig.proratedRentFromPDF[i].split("|")[1].length()).trim();
		    			proratedRent = proratedRentRaw.split(" ")[0].trim();
		    			    if(proratedRent.contains("*"))
		    			    {
		    			    	proratedRent = proratedRent.replace("*","");
		    			    }
		    			    if(proratedRent.endsWith(","))
		    			    {
		    			    	proratedRent = proratedRent.substring(0,proratedRent.length()-1);
		    			    }
		    			    if(proratedRent.matches(".*[a-zA-Z]+.*"))
						    {
		    			    	proratedRent = "Error";
						    }
		    			    break;
		    		}
		    	}
		    	catch(Exception e) {
		    		proratedRent = "Error";
			    	 e.printStackTrace();
		    	}
		    	
			}
		    System.out.println("Prorated Rent = "+proratedRent.trim()); */
		    
		    
		    
	 	}
	
		return true;
		
	}
	
	public static String getValues(String[] naming) 
	{
		for(int i=0;i<naming.length;i++)
		{
			String rent ="";
			String subStringValue  = naming[i].split("\\^")[0].toLowerCase();
			String priorText  = naming[i].split("\\^")[1].toLowerCase();
			String splitBy  = naming[i].split("\\^")[2].toLowerCase();
			try {
				String monthlyRent = text.substring(text.indexOf(subStringValue));
				rent = monthlyRent.substring(monthlyRent.indexOf(priorText)+priorText.length()).trim().split(splitBy)[0].trim();//.replaceAll("[a-ZA-Z,]", "");
				if(rent.contains(",")) {
					rent = rent.replaceAll(",", "");
				}
				if(hasSpecialCharacters(rent) == true)
				{
					if(rent.equalsIgnoreCase("n/a")) {
						//System.out.println("Rent Value= n/a");
						return "n/a";
					}
					else {
						continue;
					}
					
				}
				else {
					//System.out.println("Rent Value= "+rent);
					return rent;
				}
			}
			catch(Exception e) {
				//continue;n/a
			}
				
		}
			
		return "Error";
	}
	
	
	public static String getDates(String[] data) 
	{
		try {
			for(int i=0;i<data.length;i++)
			{
				String date ="";
				String subStringValue  = data[i].split("\\^")[0].toLowerCase();
				String priorText  = data[i].split("\\^")[1].toLowerCase();
				String afterText  = data[i].split("\\^")[2].toLowerCase();
				//String splitBy  = data[i].split("\\^")[3].toLowerCase();
				try {
					String modifiedtext = text.substring(text.indexOf(subStringValue));
					date = modifiedtext.substring(modifiedtext.indexOf(priorText)+priorText.length()).trim();
					date = date.substring(0, date.indexOf(afterText)).trim();//.replaceAll("[a-ZA-Z,]", "");
			
					return date;
				}
				catch(Exception e) {
					continue;
					//continue;n/a
				}
					
			}
		}
		catch(Exception e) {
			return "Error";
		}
		
		return "Error";
			
		
	}
	
	
	
	public static boolean getFlags(String[] getChecks) {
		for(int i=0;i<getChecks.length;i++)
		{
			String subStringValue  = getChecks[i].toLowerCase();
			try {
				if(text.contains(subStringValue)) {
					
					return true;
				}
			}
			catch(Exception e) {
				return false;
			}
		}
		
		return false;
		
	}
	
	public static boolean hasSpecialCharacters(String inputString) 
    {
        // Define a regular expression pattern to match characters other than digits, dots, and commas
        Pattern pattern = Pattern.compile("[^0-9.,]");

        // Use a Matcher to find any match in the input string
        Matcher matcher = pattern.matcher(inputString);

        return matcher.find();
    }
	
}
