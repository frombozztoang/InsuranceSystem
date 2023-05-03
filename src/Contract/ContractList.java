package Contract;

import java.util.ArrayList;

public interface ContractList {

	public ContractListImpl m_ContractListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<Contract> retrieve();

	public boolean update();

}