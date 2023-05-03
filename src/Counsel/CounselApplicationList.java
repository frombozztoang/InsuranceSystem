package Counsel;

import java.util.ArrayList;

public interface CounselApplicationList {

	public CounselApplicationListImpl m_CounselApplicationListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<CounselApplication> retrieve();

	public boolean update();

}