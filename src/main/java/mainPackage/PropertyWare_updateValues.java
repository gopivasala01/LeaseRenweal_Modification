package mainPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PropertyWare_updateValues 
{
	/*public static String proratedRent_StartDate = "";
	public static String proratedRent_EndDate = "";
	public static String monthlyRent_StartDate = "";
	public static String increasedRent_StartDate = "";
	public static String ResidentBenefitPackage_StartDate = "";
	public static String HVACAirFilterFee_StartDate = "";
	public static String petRent_StartDate = "";
	*/
	
	public static String startDate_MoveInCharge = "";
	public static String startDate_AutoCharge = "";
	public static String autoCharge_startDate_MonthlyRent = ""; //For other portfolios, it should be added as second full month in Auto Charges 
	public static String increasedRent_previousRentStartDate ="";
	public static String endDate_ProrateRent = "";
	public static String endDate_MonthlyRent_WhenIncreasedRentAvailable = "";
	
	//ConfigureValues
		public static boolean configureValues() throws Exception
		{
			//Clear all values Configuration table first
			String query1 = "update  automation.LeaseCloseOutsChargeChargesConfiguration Set Amount=NULL, StartDate=NUll, EndDate=NUll, MoveInCharge=NULL, AutoCharge=NULL, autoCharge_StartDate=NULL";
			DataBase.updateTable(query1);
			
			//If Concession Addendum Available, mention that in the comments
			if(PDFReader.concessionAddendumFlag == true) 
			{
				RunnerClass.failedReason = RunnerClass.failedReason+",Concession Addendum is available";
				//DataBase.notAutomatedFields(RunnerClass.buildingAbbreviation, "Consession Addendum is available"+'\n');
			}
					
			//Get all Required dates converted
			PDFReader.startDate = RunnerClass.convertDate(PDFReader.commencementDate);
			PDFReader.endDate = RunnerClass.convertDate(PDFReader.expirationDate);
			PDFReader.lastDayOfTheStartDate = RunnerClass.lastDateOfTheMonth(PDFReader.startDate);
			PDFReader.firstFullMonth = RunnerClass.firstDayOfMonth(PDFReader.startDate,1);
			PDFReader.secondFullMonth = RunnerClass.firstDayOfMonth(PDFReader.startDate,2);
			
			//Check if Move In Date is less than 5 days to the End Of the month, if yes, remove prepayment charge from IAG portfolios
			try
			{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDate date1 = LocalDate.parse(PDFReader.lastDayOfTheStartDate, dtf);
			    LocalDate date2 = LocalDate.parse( PDFReader.startDate, dtf);
				long daysBetween = ChronoUnit.DAYS.between(date2, date1);
				if(daysBetween<=5)
					PDFReader.checkifMoveInDateIsLessThan5DaysToEOM = true;
			}
			catch(Exception e)
			{
				PDFReader.checkifMoveInDateIsLessThan5DaysToEOM = false;
			}
			//Compare Start and end Dates in PW with Lease Agreement
			try
			{
				if(PDFReader.startDate.trim().equals(RunnerClass.startDateInPW.trim()))
				System.out.println("Start is matched");
				else 
				{
					System.out.println("Start is not matched");
					RunnerClass.failedReason = RunnerClass.failedReason+",Start is not matched";
				}
				
				if(PDFReader.endDate.trim().equals(RunnerClass.endDateInPW.trim()))
					System.out.println("End is matched");
					else 
					{
						System.out.println("End is not matched");
						RunnerClass.failedReason = RunnerClass.failedReason+",End is not matched";
					}
			}
			catch(Exception e)
			{}
			
			//Update dates as per Move and Auto Charges
			PropertyWare_updateValues.updateDates();
			PropertyWare_updateValues.decideMoveInAndAutoCharges();
			PropertyWare_updateValues.addingValuesToTable();
			return true;
			}

		public static void updateDates() throws Exception
		{
			startDate_MoveInCharge  = PDFReader.startDate;
			endDate_ProrateRent =  RunnerClass.lastDateOfTheMonth(PDFReader.firstFullMonth);
			startDate_AutoCharge = PDFReader.firstFullMonth;
			if(RunnerClass.portfolioType=="MCH"||PDFReader.proratedRent.trim().equals("0.00")||PDFReader.proratedRent.trim().equals("Error"))
				autoCharge_startDate_MonthlyRent = PDFReader.firstFullMonth;
			else 
				autoCharge_startDate_MonthlyRent = PDFReader.secondFullMonth;
			if(PDFReader.incrementRentFlag==true)
			{
				endDate_MonthlyRent_WhenIncreasedRentAvailable = RunnerClass.convertDate(PDFReader.increasedRent_previousRentEndDate);
			}
			
		}
		
		public static boolean addingValuesToTable()
		{
			try
			{
			String query =null;
			for(int i=1;i<=20;i++)
			{
				switch(i)
				{
				case 1:
					query = "Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProrateRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='"+endDate_ProrateRent+"',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=1";
					break;
				case 2:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.monthlyRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='"+endDate_MonthlyRent_WhenIncreasedRentAvailable+"',AutoCharge_StartDate='"+autoCharge_startDate_MonthlyRent+"' where ID=2";
					break;
				case 3:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getTenentAdminReveueChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.adminFee+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=3";
					break;
				case 4: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProratePetRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedPetRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',Description = '"+PDFReader.proratePetRentDescription+"' where ID=4";	
					break;
				case 5:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getpetSecurityDepositChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.petSecurityDeposit+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=5";
					break;
				case 6:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getpetOneTimeNonRefundableChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.petOneTimeNonRefundableFee+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=6";
					break;
				case 7: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getHVACAirFilterFeeChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.airFilterFee+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=7";
					break;
				case 8: 
					String chargeCode=AppConfig.getPetRentChargeCode(RunnerClass.company);
					if(RunnerClass.company.equals("Idaho Falls")||RunnerClass.company.equals("Utah"))
					{
						if(PDFReader.petInspectionFeeFlag==true)
						chargeCode = AppConfig.getPetRentChargeCode(RunnerClass.company).split(",")[1];
						else
						chargeCode = AppConfig.getPetRentChargeCode(RunnerClass.company).split(",")[0];
					}
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+chargeCode+"',Amount = '"+PDFReader.petRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=8";
					break;
				case 9: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getPrepaymentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.prepaymentCharge+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=9";
					break;
				case 10: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getIncreasedRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.increasedRent_amount+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+PDFReader.increasedRent_newStartDate+"' where ID=10";
					break;
				case 11: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.residentBenefitsPackage+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=11";
					break;
				case 12: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getPrepaymentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.monthlyRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=12";
					break;	
				case 13: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.prorateRUBS+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=13";
					break;
				case 14: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.RUBS+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=14";
				    break;
				case 15: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentTaxCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=15";
					break;
				case 16: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentTaxCode(RunnerClass.company)+"',Amount = '"+PDFReader.monthlyRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=16";
					break;
				case 17: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getPetRentTaxCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedPetRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=17";
					break;
				case 18: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProratePetRentTaxCode(RunnerClass.company)+"',Amount = '"+PDFReader.petRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=18";
					break;
				case 19: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProrateRentGETCode(RunnerClass.company)+"',Amount = '"+PDFReader.prorateRentGET+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=19";
					break;
				case 20: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentGETCode(RunnerClass.company)+"',Amount = '"+PDFReader.monthlyRentTaxAmount+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=20";
					break;
				}
			}
			DataBase.updateTable(query);
			return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Issue in adding values to Auto charges table");
				RunnerClass.failedReason =  RunnerClass.failedReason+","+"Internal Error - consolidating auto charges";
				return false;
			}
		}
		
		public static void decideMoveInAndAutoCharges()
		{
			String moveInCharges ="";
			String autoCharges = "";
			
			//Decide Prepayment Charge
			PDFReader.proratePetRentDescription = "Prorate Pet Rent";
			String prepaymentChargeOrMonthlyRent;
			if(PropertyWare_updateValues.checkProratedRentDateIsInMoveInMonth()==true)
			{
				PDFReader.proratedRentDateIsInMoveInMonthFlag =true;
				if(PDFReader.proratedRentDate.equalsIgnoreCase("n/a")||PDFReader.proratedRentDate.equalsIgnoreCase("na")||PDFReader.proratedRentDate.equalsIgnoreCase("N/A")||PDFReader.proratedRentDate.equalsIgnoreCase("NA")||PDFReader.proratedRentDate.equalsIgnoreCase("n/a."))
				{
					prepaymentChargeOrMonthlyRent = "2";
					PDFReader.proratedPetRent = PDFReader.petRent;
					PDFReader.proratePetRentDescription = "Pet Rent";
					PDFReader.prorateRUBS = PDFReader.RUBS;
				}
				else
				{
					prepaymentChargeOrMonthlyRent = "12";
					PDFReader.proratePetRentDescription = "Prorate Pet Rent";
				}
				
			}
			else 
				prepaymentChargeOrMonthlyRent = "9";
			/*
			//If Market is Boise, Utah, Idaho falls
			if(RunnerClass.company.equals("Boise"))
			PropertyWare_updateValues.specificMarketMoveInAndAutoChargesAssignment(moveInCharges, autoCharges, prepaymentChargeOrMonthlyRent);
			else
			{*/
			if(RunnerClass.portfolioType=="MCH")
			{
				
				if(PDFReader.petFlag==false)
				{
					if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
					{
						moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,11";
						if(PDFReader.incrementRentFlag == true)
						autoCharges = "2,11,10";
						else autoCharges = "2,11";
					}
					else
					{
					moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3";
					if(PDFReader.incrementRentFlag == true)
					autoCharges = "2,7,10";
					else autoCharges = "2,7";
					}
					//DataBase.assignChargeCodes(moveInCharges, autoCharges);	
				}
				else
				{
					if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==false)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
						moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,6,11";
						if(PDFReader.incrementRentFlag == true)
						autoCharges = "2,11,8,10";
						else autoCharges = "2,11,8";
						}
						else
						{
							moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,6";
							if(PDFReader.incrementRentFlag == true)
							autoCharges = "2,7,8,10";
							else autoCharges = "2,7,8";
						}
						//DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
				    else
				    {
						if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==true)
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,5,11";
								if(PDFReader.incrementRentFlag == true)
								autoCharges = "2,11,8,10";
								else autoCharges = "2,11,8";
							}
							else
							{
							moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,5";
							if(PDFReader.incrementRentFlag == true)
							autoCharges = "2,7,8,10";
							else autoCharges = "2,7,8";
							}
							//DataBase.assignChargeCodes(moveInCharges, autoCharges);
						}
				    }
				}
				
			}
			//Other Portfolios
			else 
			{
				
				if(RunnerClass.portfolioType=="Others"&&PDFReader.petFlag==false)
				{
					if(PDFReader.proratedRentDateIsInMoveInMonthFlag == true)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
							moveInCharges = "1,2,3,11";
							autoCharges = "2,11";	
						}
						else
						{
						moveInCharges = "1,2,3";
						autoCharges = "2,7";
						}
					}
					else
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
					     moveInCharges = "2,3,11";
					     autoCharges = "1,2,11";
						}
						else
						{
							moveInCharges = "2,3";
						     autoCharges = "1,2,7";
						}
					}
					//DataBase.assignChargeCodes(moveInCharges, autoCharges);
					//DataBase.assignChargeCodes(moveInCharges, autoCharges);
				}
				else
				{
					if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==false)
					{
						if(PDFReader.proratedRentDateIsInMoveInMonthFlag == true)
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "1,2,3,4,6,11";
								autoCharges = "2,11,8";
							}
							else
							{
							moveInCharges = "1,2,3,4,6";
							autoCharges = "2,7,8";
							}
						}
						else
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "2,3,4,6,11";
								autoCharges = "1,2,11,8";
							}
							else
							{
						      moveInCharges = "2,3,4,6";
						      autoCharges = "1,2,7,8";
							}
						}
						//DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
					else//(PDFReader.portfolioType=="Others"&&PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==true)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
							moveInCharges = "2,3,4,5,11";
							autoCharges = "1,2,11,8";
						}
						else
						{
						moveInCharges = "2,3,4,5";
						autoCharges = "1,2,7,8";
						}
						//DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
				}
				
			}
			
			//If Move In Date is less than 5 days to the end of the month, remove prepayments charge from the move in charges
			if(RunnerClass.portfolioType=="MCH"&&PDFReader.checkifMoveInDateIsLessThan5DaysToEOM==true)
			{
				if(moveInCharges.contains(",9"))
				moveInCharges = moveInCharges.replace(",9", "");
				if(moveInCharges.contains(",12"))
					moveInCharges = moveInCharges.replace(",12", "");
			}
			
			//If Company is Boise,Idaho Falls
			if((RunnerClass.company.equals("Boise")||RunnerClass.company.equals("Idaho Falls")||RunnerClass.company.equals("Utah"))&&PDFReader.residentUtilityBillFlag==true&&(!PDFReader.prorateRUBS.equals("Error")&&!PDFReader.RUBS.equals("Error")))
			{
				moveInCharges = moveInCharges+",13";
				autoCharges = autoCharges+",14";
			}
			//Alabama Monthly Rent tax Charge changing
			if(RunnerClass.company.equals("Alabama")&&PDFReader.monthlyRentTaxFlag==true)
			{
				moveInCharges = moveInCharges.replace("1,", "15,").replace("2,", "16,");
				autoCharges = autoCharges.replace("2,", "16,");
			}
			
			//Alabama Pet Rent tax Charge changing
			if(RunnerClass.company.equals("Alabama")&&PDFReader.petFlag==true&&PDFReader.petRentTaxFlag==true)
			{
				moveInCharges = moveInCharges.replace(",4", ",17");
				autoCharges = autoCharges.replace(",8", ",18");
			}
			
			//Hawaii Monthly Rent tax Charge changing
			if(RunnerClass.company.equals("Hawaii")&&PDFReader.monthlyRentTaxFlag==true)
			{
				String[] moveInCodes = moveInCharges.split(",");
				for(int i=0;i<moveInCodes.length;i++)
				{
					String code = moveInCodes[i];
					switch(code)
					{
					case "1":
						moveInCharges = moveInCharges.replace("1,", "1,19,");
						break;
					case "2":
						moveInCharges = moveInCharges.replace("2,", "2,20,");
					}
				}
				String[] autoCodes = autoCharges.split(",");
				for(int i=0;i<autoCodes.length;i++)
				{
					String code = autoCodes[i];
					switch(code)
					{
					case "1":
						autoCharges = autoCharges.replace("1,", "1,19,");
						break;
					case "2":
						autoCharges = autoCharges.replace("2,", "2,20,");
						break;
					}
				}
			}
			
			DataBase.assignChargeCodes(moveInCharges, autoCharges);
		}
		
		public static boolean checkProratedRentDateIsInMoveInMonth()
		{
			try
			{
			if(PDFReader.proratedRentDate.equalsIgnoreCase("n/a")||PDFReader.proratedRentDate.equalsIgnoreCase("na")||PDFReader.proratedRentDate.equalsIgnoreCase("n/a."))
				return true;
			if(PDFReader.proratedRentDate==null||PDFReader.proratedRentDate.equalsIgnoreCase("n/a")||PDFReader.proratedRentDate=="Error")
				return false;
			String proratedDate = RunnerClass.convertDate(PDFReader.proratedRentDate);
			String proratedMonth = proratedDate.split("/")[0];
			String moveInDate = RunnerClass.convertDate(PDFReader.commencementDate);
			String moveInMonth = moveInDate.split("/")[0];
			if(proratedMonth.equalsIgnoreCase(moveInMonth)||Double.parseDouble(PDFReader.proratedRent)<=200.00)
			{
				return true;
			}
			else return false;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		public static void specificMarketMoveInAndAutoChargesAssignment(String moveInCharges,String autoCharges,String prepaymentChargeOrMonthlyRent)
		{
			if(RunnerClass.portfolioType=="MCH")
			{
				
				if(PDFReader.petFlag==false)
				{
					if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
					{
						moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,11,13";
						if(PDFReader.incrementRentFlag == true)
						autoCharges = "2,11,10,14";
						else autoCharges = "2,11,14";
					}
					else
					{
					moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3";
					if(PDFReader.incrementRentFlag == true)
					autoCharges = "2,7,10";
					else autoCharges = "2,7";
					}
					DataBase.assignChargeCodes(moveInCharges, autoCharges);	
				}
				else
				{
					if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==false)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
						moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,6,11";
						if(PDFReader.incrementRentFlag == true)
						autoCharges = "2,11,8,10";
						else autoCharges = "2,11,8";
						}
						else
						{
							moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,6";
							if(PDFReader.incrementRentFlag == true)
							autoCharges = "2,7,8,10";
							else autoCharges = "2,7,8";
						}
						DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
				    else
				    {
						if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==true)
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,5,11";
								if(PDFReader.incrementRentFlag == true)
								autoCharges = "2,11,8,10";
								else autoCharges = "2,11,8";
							}
							else
							{
							moveInCharges = "1,"+prepaymentChargeOrMonthlyRent+",3,4,5";
							if(PDFReader.incrementRentFlag == true)
							autoCharges = "2,7,8,10";
							else autoCharges = "2,7,8";
							}
							DataBase.assignChargeCodes(moveInCharges, autoCharges);
						}
				    }
				}
				
			}
			//Other Portfolios
			else 
			{
				
				if(RunnerClass.portfolioType=="Others"&&PDFReader.petFlag==false)
				{
					if(PDFReader.proratedRentDateIsInMoveInMonthFlag == true)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
							moveInCharges = "1,2,3,11";
							autoCharges = "2,11";	
						}
						else
						{
						moveInCharges = "1,2,3";
						autoCharges = "2,7";
						}
					}
					else
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
					     moveInCharges = "2,3,11";
					     autoCharges = "1,2,11";
						}
						else
						{
							moveInCharges = "2,3";
						     autoCharges = "1,2,7";
						}
					}
					//DataBase.assignChargeCodes(moveInCharges, autoCharges);
					DataBase.assignChargeCodes(moveInCharges, autoCharges);
				}
				else
				{
					if(PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==false)
					{
						if(PDFReader.proratedRentDateIsInMoveInMonthFlag == true)
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "1,2,3,4,6,11";
								autoCharges = "2,11,8";
							}
							else
							{
							moveInCharges = "1,2,3,4,6";
							autoCharges = "2,7,8";
							}
						}
						else
						{
							if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
							{
								moveInCharges = "2,3,4,6,11";
								autoCharges = "1,2,11,8";
							}
							else
							{
						      moveInCharges = "2,3,4,6";
						      autoCharges = "1,2,7,8";
							}
						}
						DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
					else//(PDFReader.portfolioType=="Others"&&PDFReader.petFlag==true&&PDFReader.petSecurityDepositFlag==true)
					{
						if(PDFReader.residentBenefitsPackageAvailabilityCheck==true)
						{
							moveInCharges = "2,3,4,5,11";
							autoCharges = "1,2,11,8";
						}
						else
						{
						moveInCharges = "2,3,4,5";
						autoCharges = "1,2,7,8";
						}
						DataBase.assignChargeCodes(moveInCharges, autoCharges);
					}
				}
				
			}
		}
		
}
