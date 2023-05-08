package Insurance;

import java.util.ArrayList;

public interface TermsList {

	public TermsListImpl m_TermsListImpl();

	public boolean add();

	public ArrayList<Terms> retrieve();


}