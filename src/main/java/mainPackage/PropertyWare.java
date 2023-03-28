package mainPackage;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PropertyWare 
{
	public static boolean login()
	{
		try
		{
		RunnerClass.downloadFilePath = AppConfig.downloadFilePath;
		Map<String, Object> prefs = new HashMap<String, Object>();
	    // Use File.separator as it will work on any OS
	    prefs.put("download.default_directory",
	    		RunnerClass.downloadFilePath);
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
        RunnerClass.driver= new ChromeDriver(options);
        RunnerClass.driver.manage().window().maximize();
        RunnerClass.driver.get(AppConfig.URL);
        RunnerClass.driver.findElement(Locators.userName).sendKeys(AppConfig.username); 
        RunnerClass.driver.findElement(Locators.password).sendKeys(AppConfig.password);
        RunnerClass.driver.findElement(Locators.signMeIn).click();
        RunnerClass.actions = new Actions(RunnerClass.driver);
        RunnerClass.js = (JavascriptExecutor)RunnerClass.driver;
        RunnerClass.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(2));
        try
        {
        if(RunnerClass.driver.findElement(Locators.loginError).isDisplayed())
        {
        	System.out.println("Login failed");
		    RunnerClass.failedReason = RunnerClass.failedReason+","+ "Login failed";
			return false;
        }
        }
        catch(Exception e) {}
        RunnerClass.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(100));
        return true;
		}
		catch(Exception e)
		{
			System.out.println("Login failed");
		    RunnerClass.failedReason = RunnerClass.failedReason+","+ "Login failed";
			return false;
		}
	}
	
	public static boolean searchBuilding(String company, String building)
	{
		try
		{
	    RunnerClass.driver.findElement(Locators.dashboardsTab).click();
		RunnerClass.driver.findElement(Locators.searchbox).clear();
		RunnerClass.driver.findElement(Locators.searchbox).sendKeys(building);
			try
			{
			RunnerClass.wait.until(ExpectedConditions.invisibilityOf(RunnerClass.driver.findElement(Locators.searchingLoader)));
			}
			catch(Exception e)
			{}
			try
			{
			RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			if(RunnerClass.driver.findElement(Locators.noItemsFoundMessagewhenLeaseNotFound).isDisplayed())
			{
				System.out.println("Building Not Found");
			    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Building Not Found";
				return false;
			}
			}
			catch(Exception e2)
			{
			}
			RunnerClass.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
			Thread.sleep(1000);
			System.out.println(building);
		// Select Lease from multiple leases
			List<WebElement> displayedCompanies =null;
			try
			{
				displayedCompanies = RunnerClass.driver.findElements(Locators.searchedLeaseCompanyHeadings);
			}
			catch(Exception e)
			{
				
			}
				boolean leaseSelected = false;
				for(int i =0;i<displayedCompanies.size();i++)
				{
					String companyName = displayedCompanies.get(i).getText();
					if(companyName.toLowerCase().contains(company.toLowerCase())&&!companyName.contains("Legacy"))
					{
						
						List<WebElement> leaseList = RunnerClass.driver.findElements(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li/a"));
						System.out.println(leaseList.size());
						for(int j=0;j<leaseList.size();j++)
						{
							String lease = leaseList.get(j).getText();
							if(lease.toLowerCase().contains(building.toLowerCase()))
							{
								try
								{
								RunnerClass.portfolioType = RunnerClass.driver.findElement(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li["+(j+1)+"]/a")).getText().trim().split(":")[0];
								System.out.println("Portfolio type = "+RunnerClass.portfolioType);
								}
								catch(Exception e) 
								{}
								
								//RunnerClass.driver.findElement(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li["+(j+1)+"]/a")).click();
								leaseSelected = true;
								break;
							}
						}
					}
					if(leaseSelected==true)
					{
						//Decide Portfolio Type
						int portfolioFlag =0;
						for(int k=0;k<mainPackage.AppConfig.IAGClientList.length;k++)
						{
							String portfolioStarting = mainPackage.AppConfig.IAGClientList[k].toLowerCase();
							if(RunnerClass.portfolioType.toLowerCase().startsWith(portfolioStarting))
							{
								portfolioFlag =1;
								break;
								//PDFReader.portfolioType = "MCH";
							}
						}
						
						if(portfolioFlag==1)
							RunnerClass.portfolioType = "MCH";
						else RunnerClass.portfolioType = "Others";
					     return true;
					}
				}
				if(leaseSelected==false)
				{
				    RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Building Not Found";
					return false;
				}
	         } catch(Exception e) 
		     {
	         RunnerClass.failedReason = RunnerClass.failedReason+","+  "Issue in selecting Building";
		     return false;
		     }
		return true;
	}
	
	public static boolean downloadLeaseAgreement(String building, String ownerName) throws Exception
	{
		try
		{
			RunnerClass.portfolioType = RunnerClass.driver.findElement(Locators.checkPortfolioType).getText();
			System.out.println("Portfolio Type = "+RunnerClass.portfolioType);
		
		int portfolioFlag =0;
		for(int i=0;i<AppConfig.IAGClientList.length;i++)
		{
			if(RunnerClass.portfolioType.contains(mainPackage.AppConfig.IAGClientList[i]))
			{
				portfolioFlag =1;
				break;
			}
		}
		
		if(portfolioFlag==1)
			RunnerClass.portfolioType = "MCH";
		else RunnerClass.portfolioType = "Others";
	    System.out.println("Portfolio Type = "+RunnerClass.portfolioType);
		}
	
		catch(Exception e) 
		{
			System.out.println("Unable to fetch Portfolio Type");
			 RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Unable to fetch Portfolio Type";
		   // return false;  -- Commented this as we are not using Portfolio condition anywhere in the process
		}
		
		//RC details
		
		try
		{
		PDFReader.RCDetails = RunnerClass.driver.findElement(Locators.RCDetails).getText();
		}
		catch(Exception e)
		{
			PDFReader.RCDetails = "Error";
		}
		System.out.println("RC Details = "+PDFReader.RCDetails);
		
		try
		{
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		RunnerClass.driver.findElement(Locators.leasesTab).click();
		RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(5));
		try
		{
		RunnerClass.driver.findElement(By.partialLinkText(ownerName.trim())).click();
		}
		catch(Exception e)
		{
			
			System.out.println("Unable to Click Lease Owner Name");
		    RunnerClass.failedReason =  RunnerClass.failedReason+","+  "Unable to Click Lease Onwer Name";
			return false;
		}
		RunnerClass.driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(15));
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		RunnerClass.driver.findElement(Locators.notesAndDocs).click();
		
		List<WebElement> documents = RunnerClass.driver.findElements(Locators.documentsList);
		boolean checkLeaseAgreementAvailable = false;
		 
		for(int i =0;i<documents.size();i++)
		{
			for(int j=0;j<AppConfig.LeaseAgreementFileNames.length;j++)
			{
			 if(documents.get(i).getText().startsWith(AppConfig.LeaseAgreementFileNames[j]))
			 {
			 	documents.get(i).click();
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
		Thread.sleep(10000);
		File file = RunnerClass.getLastModified();
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(RunnerClass.driver).withTimeout(Duration.ofSeconds(25)).pollingEvery(Duration.ofMillis(100));
		wait.until( x -> file.exists());
		Thread.sleep(5000);
		return true;
		}
		catch(Exception e)
		{
			System.out.println("Unable to download Lease Agreement");
		    RunnerClass.failedReason =  RunnerClass.failedReason+","+"Unable to download Lease Agreement";
			return false;
		}
	}
	


}
