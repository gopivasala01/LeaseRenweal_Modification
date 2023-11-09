package GenericPDFReading;

public class AppConfig {

	public static String[] renewalLeaseAgreementFileNames = {"RT Renewal Signed","RT - RENEWAL","RT_Full_Lease","Full Lease -","RENEWAL","renewal_","Renewal","Full_Lease","Full"};
	public static String[] monthlyRentFromPDF = {"Monthly Rent:^Monthly Rent due in the amount of $^ ","Monthly Rent:^Tenant will pay Landlord monthly rent in the amount of $^ ","monthly installments,^on or before the 1st day of each month, in the amount of $^ "};
	public static String[] monthlyRentTaxAmountFromPDF = {"Monthly Rent:^plus applicable sales tax and administrative fees of $^ ","Monthly Rent:^plus the additional amount of $^ "};
	public static String[] proratedRentFromPDF = {"Prorated Rent:^Tenant will pay Landlord $^ ","prorated rent,^Tenant will pay Landlord $^ "};
	public static String[] petRentFromPDF = {"THIS PET ADDENDUM^Tenant will pay Landlord monthly pet rent in the amount of $^ ","PET AUTHORIZATION AND PET DESCRIPTION:^Tenant will pay Landlord monthly pet rent in the amount of $^ "};
	public static String[] rbpFromPDF = {"Resident Benefits Package (“RBP”) Program and Fee:^Tenant agrees to pay a Resident Benefits Package Fee of $^ "};
	
	
	
	public static String[] commencementDateFromPDF= {"Initial Term:^shall commence on^(the “Commencement Date”)","Primary Term:^Commencement Date:^Expiration Date:","TERMS, CONDITIONS AND COVENANTS^ commences on^(“Commencement Date”)"};
	public static String[] expirationDateFromPDF= {"Initial Term:^(based on the location of the Premises) on^(the “Expiration Date”)","Primary Term:^Expiration Date:^B. Delay of Occupancy:","TERMS, CONDITIONS AND COVENANTS^and expires on^(“Expiration Date”)"};
	
	public static String[] petRentAvailablityCheck = {"THIS PET ADDENDUM","PET AUTHORIZATION AND PET DESCRIPTION:"};
	public static String[] monthlyRentTaxAmountAvailablityCheck = {"plus the additional amount of $","plus applicable sales tax and administrative fees of $"};
	public static String[] rbpAvailabilityCheck = {"Resident Benefits Package (“RBP”) Program and Fee:","Resident Benefits Package (RBP) Lease Addendum"};

}

