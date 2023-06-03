package CompensationClaim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SurveyListImpl {

    private ArrayList<Survey> surveyList;
    public Survey survey;

    public SurveyListImpl(String surveyFileName) throws IOException {
        BufferedReader surveyFile = new BufferedReader(new FileReader(surveyFileName));
        this.surveyList = new ArrayList<Survey>();
        while (surveyFile.ready()) {
            Survey survey = stringToSurvey(surveyFile.readLine());
            if (survey!=null) this.surveyList.add(survey);
        }
        surveyFile.close();
    }

    private Survey stringToSurvey(String surveyInfo) {
        Survey survey = new Survey();
        StringTokenizer stringTokenizer = new StringTokenizer(surveyInfo);
        survey.setCCID(stringTokenizer.nextToken());
        survey.setManagerName(stringTokenizer.nextToken());
        survey.setReportFilePath(stringTokenizer.nextToken());
        survey.setSurveyFee(Integer.parseInt(stringTokenizer.nextToken()));
        survey.setDecisionMoney(Integer.parseInt(stringTokenizer.nextToken()));
        survey.setResponsibility(Boolean.parseBoolean(stringTokenizer.nextToken()));
        survey.setResponsibilityReason(stringTokenizer.nextToken());
        return survey;
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