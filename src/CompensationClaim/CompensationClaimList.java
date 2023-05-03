package CompensationClaim;

import java.util.ArrayList;


public interface CompensationClaimList {

	public CompensationClaimListImpl m_CompensationClaimListImpl();

	public void add();

	public void delete();

	public ArrayList<CompensationClaim> retrieve();

	public void update();

}