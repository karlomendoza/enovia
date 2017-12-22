package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.agile.api.IAutoNumber;
import com.agile.api.IChange;
import com.agile.api.IWorkflow;

public class AgileConnect {

	private static final String url = "http://a934dev1.kalypsocloud.com:7001/Agile";
	private static final String userId = "kmendoza";
	private static final String password = "Qwer5678";

	public static List<String> getChangeOrders(String userId, String password, String url, String workflowName,
			double changeOrdersToCreate) throws APIException {

		List<String> changeOrderNames = new ArrayList<>();
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(AgileSessionFactory.USERNAME, userId);
		map.put(AgileSessionFactory.PASSWORD, password);

		System.out.println("connecting to agile");
		AgileSessionFactory aFactory = AgileSessionFactory.getInstance(url);
		IAgileSession session = aFactory.createSession(map);

		IAutoNumber[] autoNumberSources = session.getAdminInstance().getAgileClass("ECO").getAutoNumberSources();

		for (int i = 0; i < changeOrdersToCreate; i++) {
			IChange changeOrder = (IChange) session.createObject(session.getAdminInstance().getAgileClass("ECO"),
					autoNumberSources[0].getNextNumber());

			IWorkflow[] workflows = changeOrder.getWorkflows();
			for (IWorkflow iWorkflow : workflows) {
				if (iWorkflow.getName().equals(workflowName)) {
					changeOrder.setWorkflow(iWorkflow);
				}
			}
			changeOrderNames.add(changeOrder.getName());
		}
		return changeOrderNames;
	}
}
