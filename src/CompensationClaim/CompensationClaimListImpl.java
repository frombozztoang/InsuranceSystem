package CompensationClaim;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CompensationClaimListImpl {

	private ArrayList<CompensationClaim> compensationClaimList;
	public CompensationClaim m_CompensationClaim;

	public CompensationClaimListImpl(String compensationClaimFileName) throws IOException {
		BufferedReader compensationClaimFile = new BufferedReader(new FileReader(compensationClaimFileName));
		this.compensationClaimList = new ArrayList<CompensationClaim>();
		while (compensationClaimFile.ready()) {
			CompensationClaim compensationClaim = stringToCompensationClaim(compensationClaimFile.readLine());
			if (compensationClaim!=null) this.compensationClaimList.add(compensationClaim);
		}
		compensationClaimFile.close();
	}
	public void finalize() throws Throwable {
	}
	public ArrayList<CompensationClaim> retrieve(){
		return compensationClaimList;
	}
	public boolean update(){
		return false;
	}
	private CompensationClaim stringToCompensationClaim(String compensationClaimInfo) {
		CompensationClaim compensationClaim = new CompensationClaim();
		StringTokenizer stringTokenizer = new StringTokenizer(compensationClaimInfo);
		compensationClaim.setInsuranceID(Integer.valueOf(stringTokenizer.nextToken()));
		compensationClaim.setCustomerID(Integer.valueOf(stringTokenizer.nextToken()));
		compensationClaim.setCCID(Integer.valueOf(stringTokenizer.nextToken()));
		compensationClaim.setReceptionistName(stringTokenizer.nextToken());
		compensationClaim.setReceptionistPNumber(Integer.valueOf(stringTokenizer.nextToken()));
		compensationClaim.setRelationshipOfContractor(stringTokenizer.nextToken());
		compensationClaim.setDocumentFilePath(stringTokenizer.nextToken());
		compensationClaim.setBank(stringTokenizer.nextToken());
		compensationClaim.setAccountNumber(Integer.valueOf(stringTokenizer.nextToken()));
		compensationClaim.setAccountHolderName(stringTokenizer.nextToken());
		return compensationClaim;
	}
	public boolean createCompensationClaim(CompensationClaim compensationClaim) {
		if(this.compensationClaimList.add(compensationClaim)) {
			updateFile("data/CompensationClaim.txt");
			return true;}
		else return false;
	}

	private void updateFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			String compensationClaimInfo = "";
			if(compensationClaimList.size()>=1) compensationClaimInfo = compensationClaimList.get(0).toString();
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 1; i < this.compensationClaimList.size(); i++)
				compensationClaimInfo = compensationClaimInfo + "\r\n" + compensationClaimList.get(i).toString();
			fileWriter.write(compensationClaimInfo);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}//end CompensationClaimListImpl