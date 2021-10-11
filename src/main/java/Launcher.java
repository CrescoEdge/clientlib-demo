import com.google.gson.Gson;
import crescoclient.CrescoClient;
import io.cresco.library.app.gPayload;

import java.util.List;
import java.util.Map;


public class Launcher {

    private Gson gson = new Gson();

    public static void main(String[] args) {

        String host = "HOSTNAME";
        int port = 8282; //port of WS_API service
        String service_key = "SERVICE_KEY";

        CrescoClient client = new CrescoClient(host,port,service_key);
        client.connect();
        //client.messaging.sendsomething();
        client.connected();
        //System.out.println(client.globalcontroller.get_agent_list(null));

        Testers testers = new Testers(client);

        String dst_region = "global-region";
        String dst_agent = "global-controller";
        String jar_file_path = "cepdemo-1.1-SNAPSHOT.jar";

        //launch pipeline
        testers.launch_apps(dst_region,dst_agent,jar_file_path,1);
        String pipeline_id = client.globalcontroller.get_pipeline_list().get(0).get("pipeline_id");
        System.out.println(pipeline_id);
        gPayload gpay = client.globalcontroller.get_pipeline_info(pipeline_id);
        System.out.println(gpay.pipeline_name);
        System.out.println(client.globalcontroller.get_pipeline_status(gpay.pipeline_id));

        Map<String, List<Map<String,String>>> agentsList = client.globalcontroller.get_agent_list(dst_region);
        System.out.println(agentsList);

        Map<String,List<Map<String,String>>> agentResources = client.globalcontroller.get_agent_resource(dst_region, dst_agent);
        System.out.println(agentResources);

        Map<String,List<Map<String,String>>> pluginResources = client.globalcontroller.get_plugin_list();
        System.out.println(pluginResources);

        Map<String, List<Map<String,String>>> regionList = client.globalcontroller.get_region_resources(dst_region);
        System.out.println(regionList);

        regionList = client.globalcontroller.get_region_list();
        System.out.println(regionList);



        /*
        Map<String,String> results = client.globalcontroller.upload_jar_info(jar_file_path);

        Map<String,String> result_agent = client.agents.repo_pull_plugin_agent(dst_region, dst_agent, jar_file_path);
        System.out.println(result_agent);

        String configParamsString = client.messaging.getCompressedParam(results.get("configparams"));
        Map<String,String> configparams = client.messaging.getMapFromString(configParamsString);

        Map<String,String> add_reply = client.agents.add_plugin_agent(dst_region,dst_agent,configparams,null);

        String dst_plugin = add_reply.get("pluginid");

        Map<String,String> remove_reply = client.agents.remove_plugin_agent(dst_region,dst_agent,dst_plugin);
         */


        client.close();

    }

}
