package mainPackage;

public class AppConfig 
{
		public static boolean saveButtonOnAndOff= true;
		
		public static String username= "mds0418@gmail.com";
		public static String password="HomeRiver1#";
		public static String URL="https://app.propertyware.com/pw/login.jsp";
		public static String[] Buildings= {"SABA2399"};
		public static String[] Names= {"Baxter - Hernandez"};
		
	   public static String pendingRenewalLeases = "Select  Company,buildingName,OwnerName from Automation.LeaseInfo where Status = 'In Progress'";
			   //"Select  Company,buildingabbreviation,LeaseName from LeaseFact_dashboard where DATEDIFF(month, StartDate, GETDATE()) = 1 and Company ='Florida'  order by id asc";
	   //public static String lastMonthLeases1 = "Select  Company,buildingabbreviation,LeaseName from LeaseFact_dashboard where DATEDIFF(month, StartDate, GETDATE()) = 1 and Company ='Alabama'  order by id asc";
		//public static String lastMonthLeases = "Select  Company,buildingabbreviation,LeaseName from [Automation].[leaseAuditAutomation] where notes = 'Values did not match'";
		public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
	    public static String downloadFilePath = "C:\\SantoshMurthyP\\Lease Audit Automation";
	    public static String[] LeaseAgreementFileNames = {"REVISED_Lease_","Lease_"};
	    
	    public static  String PDFFormatConfirmationText = "The parties to this lease are:";
		public static  String PDFFormat2ConfirmationText = "THIS RESIDENTIAL LEASE AGREEMENT";
		
		//Mail credentials
		   public static String fromEmail = "bireports@beetlerim.com";
		   public static String fromEmailPassword = "Welcome@123";
		   
		   public static String toEmail ="gopi.v@beetlerim.com,Santosh.p@beetlerim.com";
		   public static String CCEmail = "santosh.t@beetlerim.com";
		   
		   public static String mailSubject = "Lease Audit for the Month of   ";
		   
		   public static String excelFileLocation = "E:\\Automation\\Gopi\\Lease Audit Automation";
		   
		   public static String getAutoCharges = "Select ChargeCode, Amount, autoCharge_StartDate,EndDate,Description from automation.LeaseCloseOutsChargeChargesConfiguration Where  AutoCharge=1";
		   
		   public static String getMoveInCharges = "Select ChargeCode, Amount, StartDate,EndDate,Description from automation.LeaseCloseOutsChargeChargesConfiguration Where MoveInCharge =1";
		   
		   public static String[] IAGClientList = {"510","AVE","BTH","CAP","FOR","HRG","HS","MAN","MCH","OFF","PIN","RF","SFR3","TH","HH","Lofty.Ai"};
		
	   public static String getMonthlyRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4000 - Rent";
		   case "California":
			   return "40010 - Rent Income";
		   case "Alabama":
			   return "4000 - Rent";
		   case "North Carolina":
			   return "4000 - Rent";
		   case "Dallas/Fort Worth":
			   return "4000 - Rent";
		   case "Chattanooga":
			   return "40010 - Rent Income";
		   case "Chicago PFW":
			   return "40010 - Rent Income";
		   case "Colorado Springs":
			   return "40010 - Rent Income";
			   
		   }
		   return "";
	   }
	   
	   public static String getPetRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4311 - Pet Rent";
		   case "Florida":
			   return "4311 - Pet Rent";
		   case "Alabama":
			   return "4311 - Pet Rent";
		   case "North Carolina":
			   return "4311 - Pet Rent";
		   case "Dallas/Fort Worth":
			   return "4311 - Pet Rent";
		   case "Chattanooga":
			   return "40230 - Pet Rent";
		   case "Chicago PFW":
			   return "40230 - Pet Rent";
		   case "California":
			   return "40230 - Pet Rent";
		   case "Colorado Springs":
			   return "40230 - Pet Rent";
			   
		   }
		   return "";
	   }
	   public static String getTenentAdminReveueChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4303 - Tenant Admin Revenue";
		   case "Florida":
			   return "4303 - Tenant Admin Revenue";
		   case "Alabama":
			   return "4303 - Tenant Admin Revenue";
		   case "North Carolina":
			   return "4303 - Tenant Admin Revenue";
		   case "Dallas/Fort Worth":
			   return "4303 - Tenant Admin Revenue";
		   case "Chattanooga":
			   return "43030 - Tenant Admin Fee";
		   case "Chicago PFW":
			   return "43030 - Tenant Admin Fee";
		   case "California":
			   return "43030 - Tenant Admin Fee";
		   case "Colorado Springs":
			   return "43030 - Tenant Admin Fee";
			   
		   }
		   return "";
	   }
	   
	   public static String getProrateRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4000 - Rent";
		   case "California":
			   return "40010 - Rent Income";
		   case "Alabama":
			   return "4000 - Rent";
		   case "North Carolina":
			   return "4000 - Rent";
		   case "Dallas/Fort Worth":
			   return "4000 - Rent";
		   case "Chattanooga":
			   return "40010 - Rent Income";
		   case "Chicago PFW":
			   return "40010 - Rent Income";
		   case "Colorado Springs":
			   return "40010 - Rent Income";
		   }
		   return "";
	   }
	   public static String getProratePetRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4311 - Pet Rent";
		   case "Florida":
			   return "4311 - Pet Rent";
		   case "Alabama":
			   return "4311 - Pet Rent";
		   case "North Carolina":
			   return "4311 - Pet Rent";
		   case "Dallas/Fort Worth":
			   return "4311 - Pet Rent";
		   case "Chattanooga":
			   return "40230 - Pet Rent";
		   case "Chicago PFW":
			   return "40230 - Pet Rent";
		   case "California":
			   return "40230 - Pet Rent";
		   case "Colorado Springs":
			   return "40230 - Pet Rent";
		   }
		   return "";
	   }
	   public static String getIncreasedRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4000 - Rent";
		   case "California":
			   return "40010 - Rent Income";
		   case "Alabama":
			   return "4000 - Rent";
		   case "North Carolina":
			   return "4000 - Rent";
		   case "Dallas/Fort Worth":
			   return "4000 - Rent";
		   case "Chattanooga":
			   return "40010 - Rent Income";
		   case "Chicago PFW":
			   return "40010 - Rent Income";
		   case "Colorado Springs":
			   return "40010 - Rent Income";
		   }
		   return "";
	   }
	   public static String getHVACAirFilterFeeChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4102 - Air Filter Fee";
		   case "Florida":
			   return "4102 - Air Filter Fee";
		   case "Alabama":
			   return "4102 - Air Filter Fee";
		   case "North Carolina":
			   return "4102 - Air Filter Fee";
		   case "Dallas/Fort Worth":
			   return "4102 - Air Filter Fee";
		   case "Chattanooga":
			   return "43060 - Filter Fee";
		   case "Chicago PFW":
			   return "43060 - Filter Fee";
		   case "California":
			   return "43060 - Filter Fee";
		   case "Colorado Springs":
			   return "43060 - Filter Fee";
		   }
		   return "";
	   }
	   
	   public static String getpetOneTimeNonRefundableChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4311 - Pet Rent";
		   case "Florida":
			   return "4311 - Pet Rent";
		   case "Alabama":
			   return "4311 - Pet Rent";
		   case "North Carolina":
			   return "4311 - Pet Rent";
		   case "Dallas/Fort Worth":
			   return "4311 - Pet Rent";
		   case "Chattanooga":
			   return "44010 - Non - Refundable Pet Fee";
		   case "Chicago PFW":
			   return "44010 - Non-Refundable Pet Fee";
		   case "California":
			   return "44010 - Non-Refundable Pet Fee";
		   case "Colorado Springs":
			   return "44010 - Non-Refundable Pet Fee";
			   
		   }
		   return "";
	   }
	   public static String getPrepaymentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "2017 - Prepayments";
		   case "Florida":
			   return "2017 - Prepayments";
		   case "Alabama":
			   return "2017 - Prepayments";
		   case "North Carolina":
			   return "2017 - Prepayments";
		   case "Dallas/Fort Worth":
			   return "2017 - Prepayments";
		   case "Chattanooga":
			   return "20030 - Prepayments";
		   case "Chicago PFW":
			   return "20030 - Prepayments";
		   case "California":
			   return "20030 - Prepayments";
		   case "Colorado Springs":
			   return "20030 - Prepayments";
		   }
		   return "";
	   }
	   
	   public static String getResidentBenefitsPackageChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "4318 - Resident Benefits Package";
		   case "California":
			   return "43070 - Resident Benefit Package Fee";
		   case "Alabama":
			   return "4318 - Resident Benefits Package";
		   case "North Carolina":
			   return "4318 - Resident Benefits Package";
		   case "Dallas/Fort Worth":
			   return "4318 - Resident Benefits Package";
		   case "Chattanooga":
			   return "43070 - Resident Benefit Package Fee";
		   case "Chicago PFW":
			   return "43070 - Resident Benefit Package Fee";
		   case "Colorado Springs":
			   return "43070 - Resident Benefit Package Fee";
		   }
		   return "";
	   }
	   public static String getpetSecurityDepositChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "2050 - Security Deposit";
		   case "Florida":
			   return "2050 - Security Deposit";
		   case "Alabama":
			   return "2050 - Security Deposit";
		   case "North Carolina":
			   return "2050 - Security Deposit";
		   case "Dallas/Fort Worth":
			   return "2050 - Security Deposit";
		   case "Chattanooga":
			   return "20020 - Security Deposit";
		   case "California":
			   return "20020 - Security Deposit";
		   case "Chicago PFW":
			   return "20020 - Security Deposit";
		   case "Colorado Springs":
			   return "20020 - Security Deposit";
		   }
		   return "";
	   }
	   public static String getEnrolledINRBPForPMUse(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "YES";
		   case "Florida":
			   return "YES";
		   case "Alabama":
			   return "YES";
		   case "North Carolina":
			   return "YES";
		   case "Dallas/Fort Worth":
			   return "YES"; 
		   case "California":
			   return "YES";
		   case "Chattanooga":
			   return "YES";
		   case "Chicago PFW":
			   return "YES";
		   case "Colorado Springs":
			   return "YES";
		   }
		   return "";
	   }
	   public static String getRBPenrollmentCompleteForSNUseOnly(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "Yes";
		   case "Florida":
			   return "Yes";
		   case "Alabama":
			   return "Yes";
		   case "North Carolina":
			   return "Yes";
		   case "Dallas/Fort Worth":
			   return "Yes"; 
		   case "California":
			   return "Yes";
		   case "Chattanooga":
			   return "Yes";
		   case "Chicago PFW":
			   return "Yes";
		   case "Colorado Springs":
			   return "Yes";
		   }
		   return "";
	   }
	   public static String getNeedsNewLease(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "NO";
		   case "California":
			   return "NO";
		   case "Alabama":
			   return "NO";
		   case "North Carolina":
			   return "NO";
		   case "Dallas/Fort Worth":
			   return "NO"; 
		   case "Chattanooga":
			   return "NO";
		   case "Chicago PFW":
			   return "No";
		   case "Colorado Springs":
			   return "NO";
		   }
		   return "";
	   }
	   
	   public static String getCompanyCode(String company)
		{
			switch(company)
			{
			case "Austin":
				return "Irvin";
			case "Alabama":
				return "AL";
			case "Arizona":
				return "AZ";
			case "Arkansas":
				return "AR";
			case "Dallas/Fort Worth":
				return "DFW";
			case "Florida":
				return "FL";
			case "Georgia":
				return "GA";
			case "Indiana":
				return "IN";
			case "Little Rock":
				return "LR";
			case "North Carolina":
				return "NC";
			case "OKC":
				return "OKC";
			case "San Antonio":
				return "SATX";
			case "South Carolina":
				return "SC";
			case "Tennessee":
				return "TN";
			case "Tulsa":
				return "TUL";
			case "Chattanooga":
				return "CHAT";
			case "Colorado Springs":
				   return "";
				
			}
			return "";
		}

	}


