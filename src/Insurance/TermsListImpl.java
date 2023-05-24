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

public class TermsListImpl {

	private ArrayList<Terms> termsList;

	public TermsListImpl(String termsFileName)throws FileNotFoundException, IOException {
		BufferedReader termsFile = new BufferedReader(new FileReader(termsFileName));
		this.termsList = new ArrayList<Terms>();
		while (termsFile.ready()) {		
			Terms terms = makeStringToTerms(termsFile.readLine());
			if (terms!=null) this.termsList.add(terms);
		}
		termsFile.close();
	}
	
	private Terms makeStringToTerms(String termsInfo) {
		Terms terms = new Terms();
		StringTokenizer stringTokenizer = new StringTokenizer(termsInfo,",");
		terms.setTermsID(stringTokenizer.nextToken());
		terms.setTermsName(stringTokenizer.nextToken());		
		terms.setTermsContent(stringTokenizer.nextToken());
		terms.setCalculatedMoneyMethod(stringTokenizer.nextToken());
		return terms;
	}

	public boolean createTerms(Terms terms){
		if(this.termsList.add(terms)) {
			updateFile("Terms.txt");
			return true;}
		else return false;
	}
	
	public ArrayList<Terms> retrieveAllTerms(){
		return termsList;
	}
	
	private void updateFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists()) 
			file.createNewFile();
			String termsInfo = termsList.get(0).toString();
			BufferedWriter termsFileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 1; i < this.termsList.size(); i++) 
				termsInfo = termsInfo + "\r\n" + termsList.get(i).toString();
			termsFileWriter.write(termsInfo);
			termsFileWriter.flush();
			termsFileWriter.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}