package CompensationClaim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class SurveyListImpl {

    private ArrayList<Survey> surveyList;
    public Survey survey;

    public SurveyListImpl() {

    }

    public void finalize() throws Throwable {

    }
    public boolean add(){
        return false;
    }

    public boolean delete(){
        return false;
    }

    public ArrayList<Survey> retrieve(){
        return surveyList;
    }

    public boolean update(){
        return false;
    }
    public boolean createSurvey(Survey survey) {
        if(this.surveyList.add(survey)) {
            updateFile("data/Survey.txt");
            return true;
        } else return false;
    }
    private void updateFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists())
                file.createNewFile();
            String surveyInfo = "";
            if(surveyList.size()>=1) surveyInfo = surveyList.get(0).toString();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            for (int i = 1; i < this.surveyList.size(); i++)
                surveyInfo = surveyInfo + "\r\n" + surveyList.get(i).toString();
            fileWriter.write(surveyInfo);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}//end carAccidentListImpl