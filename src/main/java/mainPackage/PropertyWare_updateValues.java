package mainPackage;


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
	
	//ConfigureValues
		public static boolean configureValues() throws Exception
		{
			//Clear all values Configuration table first
			String query1 = "update  automation.LeaseCloseOutsChargeChargesConfiguration Set Amount=NULL, StartDate=NUll, EndDate=NUll, MoveInCharge=NULL, AutoCharge=NULL, autoCharge_StartDate=NULL";
			DataBase.updateTable(query1);
					
			//Get all Required dates converted
			PDFReader.startDate = RunnerClass.convertDate(PDFReader.commencementDate);
			PDFReader.endDate = RunnerClass.convertDate(PDFReader.expirationDate);
			PDFReader.lastDayOfTheStartDate = RunnerClass.lastDateOfTheMonth(PDFReader.startDate);
			PDFReader.firstFullMonth = RunnerClass.firstDayOfMonth(PDFReader.startDate,1);
			PDFReader.secondFullMonth = RunnerClass.firstDayOfMonth(PDFReader.startDate,2);
			
			
			//Update dates as per Move and Auto Charges
			PropertyWare_updateValues.updateDates();
			PropertyWare_updateValues.addingValuesToTable();
			PropertyWare_updateValues.decideMoveInAndAutoCharges();
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
			
		}
		
		public static boolean addingValuesToTable()
		{
			try
			{
			String query =null;
			for(int i=1;i<=12;i++)
			{
				switch(i)
				{
				case 1:
					query = "Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProrateRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='"+endDate_ProrateRent+"',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=1";
					break;
				case 2:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.monthlyRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='"+PDFReader.increasedRent_previousRentEndDate+"',AutoCharge_StartDate='"+autoCharge_startDate_MonthlyRent+"' where ID=2";
					break;
				case 3:
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getTenentAdminReveueChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.adminFee+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=3";
					break;
				case 4: 
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getProratePetRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.proratedPetRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='' where ID=4";	
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
					query = query+"\n Update automation.LeaseCloseOutsChargeChargesConfiguration Set ChargeCode = '"+AppConfig.getPetRentChargeCode(RunnerClass.company)+"',Amount = '"+PDFReader.petRent+"',StartDate='"+startDate_MoveInCharge+"',EndDate='',AutoCharge_StartDate='"+startDate_AutoCharge+"' where ID=8";
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
			
			String prepaymentChargeOrMonthlyRent;
			if(PropertyWare_updateValues.checkProratedRentDateIsInMoveInMonth()==true)
			{
				if(PDFReader.proratedRentDate.equalsIgnoreCase("n/a")||PDFReader.proratedRentDate.equalsIgnoreCase("na")||PDFReader.proratedRentDate.equalsIgnoreCase("N/A")||PDFReader.proratedRentDate.equalsIgnoreCase("NA"))
				{
					prepaymentChargeOrMonthlyRent = "2";
					PDFReader.proratedPetRent = PDFReader.petRent;
				}
				else
					prepaymentChargeOrMonthlyRent = "12";
				
			}
			else 
				prepaymentChargeOrMonthlyRent = "9";
			
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
		
		public static boolean checkProratedRentDateIsInMoveInMonth()
		{
			try
			{
			if(PDFReader.proratedRentDate.equalsIgnoreCase("n/a")||PDFReader.proratedRentDate.equalsIgnoreCase("na"))
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
		
}
