package mainPackage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PropertyWare_OtherInformation 
{
	public static boolean addOtherInformation()
	{
		RunnerClass.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(15));
		RunnerClass.driver.navigate().refresh();
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		RunnerClass.driver.findElement(Locators.summaryEditButton).click();
		
		try
		{
		//Other Fields
        Thread.sleep(2000);
	
		// RC Field
		try
		{
			if(PDFReader.RCDetails.equalsIgnoreCase("Error"))
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "RC Details"+'\n');
				//temp=1;
			}
			else
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.RCDetails)).build().perform();
			RunnerClass.driver.findElement(Locators.rcField).clear();
			Thread.sleep(1000);
			RunnerClass.driver.findElement(Locators.rcField).sendKeys(PDFReader.RCDetails);
			}
		}
		catch(Exception e)
		{
			try
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.APMField)).build().perform();
				RunnerClass.driver.findElement(Locators.APMField).clear();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.APMField).sendKeys(PDFReader.RCDetails);
			}
			catch(Exception e2)
			{
				try
				{
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.RC)).build().perform();
					RunnerClass.driver.findElement(Locators.RC).clear();
					Thread.sleep(1000);
					RunnerClass.driver.findElement(Locators.RC).sendKeys(PDFReader.RCDetails);
				}
				catch(Exception e3)
				{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "RC Details"+'\n');
				//temp=1;
				}
			}
		}
		
        //Early Termination
		try
		{
			if(PDFReader.earlyTermination.equalsIgnoreCase("Error"))
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Early Termination"+'\n');
				//temp=1;
			}
			else
			{
			if(PDFReader.earlyTermination.contains("2"))
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.earlyTermFee2x)).build().perform();
				RunnerClass.driver.findElement(Locators.earlyTermFee2x).click();
				Select earlyTermination_List = new Select(RunnerClass.driver.findElement(Locators.earlyTermination_List));
				earlyTermination_List.selectByVisibleText("YES");
			}
			else
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Early Termination"+'\n');
				//temp=1;
			}
			}
		}
		catch(Exception e)
		{
			DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Early Termination"+'\n');
			e.printStackTrace();
			//temp=1;
		}
		
		if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
		{
			if(PDFReader.residentBenefitsPackage!="Error")
			{
			//Thread.sleep(2000);
			try
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.residentBenefitsPackage)).build().perform();
			RunnerClass.driver.findElement(Locators.residentBenefitsPackage).click();
			Select residentBenefitsPackageList = new Select(RunnerClass.driver.findElement(Locators.residentBenefitsPackage));
			//if(OKC_PropertyWare.HVACFilterFlag==false)
			residentBenefitsPackageList.selectByVisibleText("YES");
			//else enrolledInFilterEasyList.selectByVisibleText("NO");
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Resident Benefits Package"+'\n');
				//temp=1;
				e.printStackTrace();
			}
			}
		}
		else
		{
			//Enrolled in FilterEasy
			if(PDFReader.airFilterFee!="Error")
			{
			//Thread.sleep(2000);
			try
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.enrolledInFilterEasy)).build().perform();
			RunnerClass.driver.findElement(Locators.enrolledInFilterEasy).click();
			Select enrolledInFilterEasyList = new Select(RunnerClass.driver.findElement(Locators.enrolledInFilterEasy_List));
			if(PDFReader.HVACFilterFlag==false)
			enrolledInFilterEasyList.selectByVisibleText("YES");
			else enrolledInFilterEasyList.selectByVisibleText("NO");
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Enrolled in FilterEasy"+'\n');
				//temp=1;
				e.printStackTrace();
			}
			}
		}
		//Needs New Lease - No by default
		//Thread.sleep(2000);
		try
		{
		RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.needsNewLease)).build().perform();
		RunnerClass.driver.findElement(Locators.needsNewLease).click();
		Select needsNewLease_List = new Select(RunnerClass.driver.findElement(Locators.needsNewLease_List));
		needsNewLease_List.selectByVisibleText(AppConfig.getNeedsNewLease(RunnerClass.company));
		}
		catch(Exception e)
		{
			DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Needs New Lease"+'\n');
			//temp=1;
		}
		//Lease Occupants
		//Thread.sleep(2000);
		try
		{
			if(PDFReader.occupants.equalsIgnoreCase("Error"))
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Lease Occupants"+'\n');
				//temp=1;
			}
			else
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.leaseOccupants)).build().perform();
			RunnerClass.driver.findElement(Locators.leaseOccupants).clear();
			Thread.sleep(1000);
			RunnerClass.driver.findElement(Locators.leaseOccupants).sendKeys(PDFReader.occupants);
			}
		}
		catch(Exception e)
		{
			try
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.otherOccupants)).build().perform();
				RunnerClass.driver.findElement(Locators.otherOccupants).clear();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.otherOccupants).sendKeys(PDFReader.occupants);
			}
			catch(Exception e2)
			{
			DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Lease Occupants"+'\n');
			}
			//temp=1;
		}
		if(PDFReader.petFlag==true)
		{
		//pet information
			//Thread.sleep(2000);
			
			//Pet Type
			String petType = String.join(",", PDFReader.petType);
			try
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.pet1Type)).build().perform();
				RunnerClass.driver.findElement(Locators.pet1Type).clear();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.pet1Type).sendKeys(petType);
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet Types"+'\n');
				//temp=1;
			}
			//Thread.sleep(2000);
			//Pet Breed
			String petBreed = String.join(",", PDFReader.petBreed);
			try
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.pet1Breed)).build().perform();
				RunnerClass.driver.findElement(Locators.pet1Breed).clear();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.pet1Breed).sendKeys(petBreed);
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet Breed"+'\n');
				//temp=1;
			}
			
			//Pet Weight
			String petWeight = String.join(",", PDFReader.petWeight);
			try
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.pet1Weight)).build().perform();
				RunnerClass.driver.findElement(Locators.pet1Weight).clear();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.pet1Weight).sendKeys(petWeight);
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet 2 Weight"+'\n');
				//temp=1;
			}
			//Pet Rent
			//Thread.sleep(2000);
			try
			{
				if(PDFReader.petRent.equalsIgnoreCase("Error"))
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "pet Rent"+'\n');
					//temp=1;
				}
				else
				{
					try
					{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petAmount)).build().perform();
				//RunnerClass.driver.findElement(Locators.petAmount).clear();
				RunnerClass.driver.findElement(Locators.petAmount).click();
				//OKC_PropertyWare.clearTextField();
				RunnerClass.driver.findElement(Locators.petAmount).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
				Thread.sleep(1000);
				//RunnerClass.actions.click(RunnerClass.driver.findElement(Locators.petAmount)).sendKeys(Keys.SHIFT).sendKeys(Keys.HOME).sendKeys(Keys.BACK_SPACE).build().perform();
				RunnerClass.driver.findElement(Locators.petAmount).sendKeys(PDFReader.petRent);
					}
					catch(Exception e)
					{
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petAmount2)).build().perform();
						//RunnerClass.driver.findElement(Locators.petAmount).clear();
						RunnerClass.driver.findElement(Locators.petAmount2).click();
						//OKC_PropertyWare.clearTextField();
						RunnerClass.driver.findElement(Locators.petAmount2).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
						Thread.sleep(1000);
						//RunnerClass.actions.click(RunnerClass.driver.findElement(Locators.petAmount)).sendKeys(Keys.SHIFT).sendKeys(Keys.HOME).sendKeys(Keys.BACK_SPACE).build().perform();
						RunnerClass.driver.findElement(Locators.petAmount2).sendKeys(PDFReader.petRent);
					}
				}
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "pet Rent"+'\n');
				//temp=1;
			}
			try
			{
				if(PDFReader.petOneTimeNonRefundableFee.equalsIgnoreCase("Error"))
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "pet One Time Non-Refundable Fee"+'\n');
					//temp=1;
				}
				else
				{
					try
					{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.tenantOneTimePetFee)).build().perform();
				RunnerClass.driver.findElement(Locators.tenantOneTimePetFee).click();
				Thread.sleep(1000);
				RunnerClass.driver.findElement(Locators.tenantOneTimePetFee).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
				//OKC_PropertyWare.clearTextField();
				//RunnerClass.actions.click(RunnerClass.driver.findElement(Locators.tenantOneTimePetFee)).sendKeys(Keys.SHIFT).sendKeys(Keys.HOME).sendKeys(Keys.BACK_SPACE).build().perform();
				RunnerClass.driver.findElement(Locators.tenantOneTimePetFee).sendKeys(PDFReader.petOneTimeNonRefundableFee);
					}
					catch(Exception e)
					{
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petDepositAmount)).build().perform();
						RunnerClass.driver.findElement(Locators.petDepositAmount).click();
						Thread.sleep(1000);
						RunnerClass.driver.findElement(Locators.petDepositAmount).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
						//OKC_PropertyWare.clearTextField();
						//RunnerClass.actions.click(RunnerClass.driver.findElement(Locators.tenantOneTimePetFee)).sendKeys(Keys.SHIFT).sendKeys(Keys.HOME).sendKeys(Keys.BACK_SPACE).build().perform();
						RunnerClass.driver.findElement(Locators.petDepositAmount).sendKeys(PDFReader.petOneTimeNonRefundableFee);
					}
				}
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "pet One Time Non-Refundable Fee"+'\n');
				//temp=1;
			}
		
		}
			//Service Animal Information
			if(PDFReader.serviceAnimalFlag==true)
			{
				//Thread.sleep(2000);
				
				//Pet Type
				String ServiceAnimal_petType = String.join(",", PDFReader.serviceAnimalPetType);
				try
				{
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Type)).build().perform();
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Type).clear();
					Thread.sleep(1000);
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Type).sendKeys("Service "+ServiceAnimal_petType);
				}
				catch(Exception e)
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet 2 Types"+'\n');
					//temp=1;
				}
				//Thread.sleep(2000);
				//Pet Breed
				String serviceAnimal_petBreed = String.join(",", PDFReader.serviceAnimalPetBreed);
				try
				{
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Breed)).build().perform();
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Breed).clear();
					Thread.sleep(1000);
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Breed).sendKeys(serviceAnimal_petBreed);
				}
				catch(Exception e)
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet 2 Breed"+'\n');
					//temp=1;
				}
				
				
				//Thread.sleep(2000);
				//Pet Weight
				String serviceAnimal_petWeight = String.join(",", PDFReader.serviceAnimalPetWeight);
				try
				{
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Weight)).build().perform();
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Weight).clear();
					Thread.sleep(1000);
					RunnerClass.driver.findElement(Locators.serviceAnimal_pet2Weight).sendKeys(serviceAnimal_petWeight);
				}
				catch(Exception e)
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet 2 Weight"+'\n');
					//temp=1;
				}
				
				//Pet Special Provisions
				try
				{
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petSpecialProvisions)).build().perform();
					RunnerClass.driver.findElement(Locators.petSpecialProvisions).clear();
					Thread.sleep(1000);
					RunnerClass.driver.findElement(Locators.petSpecialProvisions).sendKeys("Service animals, no deposit required");
				}
				catch(Exception e)
				{
					DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet Special Provisions"+'\n');
					//temp=1;
				}
				
			} //Service Animal block
			else
			{
			//Pet Security Deposit
			//Thread.sleep(2000);
			try
			{
				//if(!OKC_PropertyWare.petSecurityDeposit.equalsIgnoreCase("Error")||!OKC_PropertyWare.petSecurityDeposit.trim().equalsIgnoreCase(" ")||!OKC_PropertyWare.petSecurityDeposit.trim().equalsIgnoreCase(""))
				if(RunnerClass.onlyDigits(PDFReader.petSecurityDeposit.trim())==true)
				{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petDepositAmount)).build().perform();
				//RunnerClass.driver.findElement(Locators.petAmount).clear();
				RunnerClass.driver.findElement(Locators.petDepositAmount).click();
				RunnerClass.driver.findElement(Locators.petDepositAmount).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
				//OKC_PropertyWare.clearTextField();
				Thread.sleep(1000);
				//RunnerClass.actions.click(RunnerClass.driver.findElement(Locators.petAmount)).sendKeys(Keys.SHIFT).sendKeys(Keys.HOME).sendKeys(Keys.BACK_SPACE).build().perform();
				RunnerClass.driver.findElement(Locators.petDepositAmount).sendKeys(PDFReader.petSecurityDeposit);
				}
			}
			catch(Exception e)
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Pet Security Deposit"+'\n');
				//temp=1;
			}
			}
			

		//Initial Monthly Payment
		try
		{
			if(PDFReader.monthlyRent.equalsIgnoreCase("Error"))
			{
				DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Intial Monthly Rent"+'\n');
				//temp=1;
			}
			else
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.initialMonthlyRent)).build().perform();
			//RunnerClass.driver.findElement(Locators.initialMonthlyRent).clear();
			RunnerClass.driver.findElement(Locators.initialMonthlyRent).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			RunnerClass.driver.findElement(Locators.initialMonthlyRent).sendKeys(PDFReader.monthlyRent);
			
			}
		}
		catch(Exception e)
		{
			DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Intial Monthly Rent"+'\n');
			//temp=1;
		}
		
		//Late Fee Rule
		//OKC_InsertDataIntoPropertyWare.lateFeeRuleMethod(OKC_PropertyWare.lateFeeType);
		mainPackage.LateFeeRule.lateFeeRule(PDFReader.lateFeeRuleType);
		
		//Thread.sleep(2000);
		RunnerClass.js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		try
		{
			Thread.sleep(2000);
			if(AppConfig.saveButtonOnAndOff==true)
			{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
			Thread.sleep(2000);
			if(RunnerClass.driver.findElement(Locators.saveLease).isDisplayed())
			{
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.leaseOccupants)).build().perform();
				RunnerClass.driver.findElement(Locators.leaseOccupants).clear();
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
				Thread.sleep(2000);
			}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//Thread.sleep(2000);
		/*
		if(temp==0)
		RunnerClass.leaseCompletedStatus = 1;
		else RunnerClass.leaseCompletedStatus = 3;
		*/
		return true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		//RunnerClass.leaseCompletedStatus = 2;
		//Thread.sleep(2000);
		if(AppConfig.saveButtonOnAndOff==true)
		{
		RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
		if(RunnerClass.driver.findElement(Locators.saveLease).isDisplayed())
		{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.leaseOccupants)).build().perform();
			RunnerClass.driver.findElement(Locators.leaseOccupants).clear();
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
			//Thread.sleep(2000);
		}
		}
		return false;
	}
		
	}
}
