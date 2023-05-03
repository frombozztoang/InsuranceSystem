package Insurance;

import java.util.ArrayList;

public interface InsuranceApplicationList {

	public InsuranceApplicationListImpl m_InsuranceApplicationListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<InsuranceApplication> retrieve();

	public boolean update();

}