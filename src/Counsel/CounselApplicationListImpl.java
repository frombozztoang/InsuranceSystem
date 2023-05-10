package Counsel;
import java.util.ArrayList;



public class CounselApplicationListImpl implements CounselApplicationList{

	private ArrayList<CounselApplication> CounselApplicationList;
	private CounselList counselList;
	private String content;
	public CounselApplicationListImpl(){
		this.CounselApplicationList = new ArrayList<CounselApplication>();
	}

	@Override
	public boolean add(CounselApplication counselApplication) {
		if(this.CounselApplicationList.add(counselApplication)) return true;	
		else return false;
	}

	@Override
	public boolean delete(int counselID) {
		for(CounselApplication counselApplication : this.CounselApplicationList) {
			if(counselApplication.getCounselID() == counselID) {
				if(((ArrayList<CounselApplication>) this.counselList).remove(content)) return true;
				break;
			}
		}
		return false;
	}

	@Override
	public boolean update(CounselApplication counselApplication, int counselID) {
		for(CounselApplication ucounselApplication : this.CounselApplicationList) {
			if(ucounselApplication.getCounselID() == counselID) {
//				ucounselApplication.setCategory(CounselApplication.getCategory());
//				ucounselApplication.setRequirement(CounselApplication.getRequirement());
			}
		}
		return false;
	}

	@Override
	public ArrayList<CounselApplication> retrieve() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
