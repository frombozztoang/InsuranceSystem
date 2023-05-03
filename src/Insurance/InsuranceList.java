package Insurance;

import java.util.ArrayList;

public interface InsuranceList {

	public InsuranceListImpl m_InsuranceListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<Insurance> retrieve();

	public boolean update();

}