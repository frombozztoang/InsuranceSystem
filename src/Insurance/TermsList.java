package Insurance;

import java.util.ArrayList;

public interface TermsList {
	boolean createTerms(Terms terms) throws Exception;
	ArrayList<Terms> retrieveAllTerms();
}