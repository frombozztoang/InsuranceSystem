package Insurance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface InsuranceList{

	boolean createInsurance(Insurance insurance) throws FileNotFoundException, IOException, Exception;

	boolean updateinsurance(Insurance updateInsurance) throws Exception;

	ArrayList<Insurance> retrieve();

	boolean deleteInsurance(String insuranceId) throws Exception;

}