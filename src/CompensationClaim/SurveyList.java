package CompensationClaim;

import java.util.ArrayList;


public interface SurveyList {

    public SurveyListImpl m_SurveyListImpl();

    public void add();

    public void delete();

    public ArrayList<Survey> retrieve();

    public void update();

}