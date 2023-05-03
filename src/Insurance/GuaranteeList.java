package Insurance;

import java.util.ArrayList;

public interface GuaranteeList {

	public GuaranteeListImpl m_GuaranteeListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<Guarantee> retrieve();

	public boolean update();

}