package Counsel;

import java.util.ArrayList;

public interface CounselList {

	public CounselListImpl m_CounselListImpl();

	public void add();

	public void delete();

	public ArrayList<Counsel> retrieve();

	public void update();

}