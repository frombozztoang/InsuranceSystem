package Insurance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GuaranteeListImpl {

	private ArrayList<Guarantee> guaranteeList;

	public GuaranteeListImpl(String guaranteeFileName)throws FileNotFoundException, IOException {
		BufferedReader guaranteeFile = new BufferedReader(new FileReader(guaranteeFileName));
		this.guaranteeList = new ArrayList<Guarantee>();
		while (guaranteeFile.ready()) {		
			Guarantee guarantee = makeStringToGuarantee(guaranteeFile.readLine());
			if (guarantee!=null) this.guaranteeList.add(guarantee);
		}
		guaranteeFile.close();
	}

	private Guarantee makeStringToGuarantee(String guaranteeInfo) throws FileNotFoundException, IOException {
		Guarantee guarantee = new Guarantee();
		StringTokenizer stringTokenizer = new StringTokenizer(guaranteeInfo);
		guarantee.setInsuranceID(stringTokenizer.nextToken());
		guarantee.setTermsID(stringTokenizer.nextToken());
		return guarantee;
	}
	
	public boolean create(Guarantee newguarantee){
		if(this.guaranteeList.add(newguarantee)) {
			updateFile("Guarantee.txt");
			return true;
		}
		else return false;
	}
	
	public boolean alreadyExistInsurance(String insuranceId){
		for(int i=0;i<this.guaranteeList.size();i++) {
			if(this.guaranteeList.get(i).matchInsuranceId(insuranceId)) 
				return true;
		}
		return false;
	}
	
	private void updateFile(String string) {
		try {
			File file = new File(string);
			if (!file.exists()) 
			file.createNewFile();
			String guaranteeInfo = "";
			BufferedWriter guaranteeFileWriter = new BufferedWriter(new FileWriter(file));
			guaranteeInfo = guaranteeList.get(0).toString();
			for (int i = 1; i < this.guaranteeList.size(); i++) 
				guaranteeInfo = guaranteeInfo + "\r\n" + guaranteeList.get(i).toString();
			guaranteeFileWriter.write(guaranteeInfo);
			guaranteeFileWriter.flush();
			guaranteeFileWriter.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
		
	
	public boolean delete(String insuranceId){
		boolean deleteOkay = false;
		for(int i=0;i<this.guaranteeList.size();i++) {
			Guarantee guarantee = (Guarantee) this.guaranteeList.get(i);
			if(guarantee.matchInsuranceId(insuranceId)) {
				this.guaranteeList.remove(guarantee);			
				deleteOkay = true;
			}}
		updateFile("Guarantee.txt");
		return deleteOkay;
	}
}