package Insurance;

import java.util.ArrayList;
import Dao.TermsDao;

public class TermsListImpl implements TermsList{

	private ArrayList<Terms> termsList;
	private TermsDao termsDao;
	
	public TermsListImpl()throws Exception {
		this.termsDao = new TermsDao();
		this.termsList = termsDao.retrieveAll();
	}
	
	@Override
	public boolean createTerms(Terms terms) throws Exception{
		if(this.termsList.add(terms)) {
			termsDao.create(terms);
			return true;}
		else return false;
	}
	
	@Override
	public ArrayList<Terms> retrieveAllTerms(){
		return termsList;
	}
	
	public Terms getTermsByID(String termsID) {
		for(int i=0;i<this.termsList.size();i++) {
			Terms terms = (Terms) this.termsList.get(i);
			if(terms.matchId(termsID))
				return terms;
		}
		return null;
	}

}