package CompensationClaim;

import java.util.ArrayList;


public interface CarAccidentList {

	public CarAccidentListImpl m_CarAccidentListImpl();

	public void add();

	public void delete();

	public ArrayList<CarAccident> retrieve();

	public void update();

}