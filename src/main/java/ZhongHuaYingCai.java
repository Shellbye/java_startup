import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class ZhongHuaYingCai {
    final static String LOGIN 			= "http://www.chinahr.com/modules/hmcompanyx/?c=login&m=chklogin";
    final static String NEW_JOB 		= "http://www.chinahr.com/modules/hmrecruit/index.php?c=managejob&m=issue&new=managejob";
    final static String POST_NEW_JOB 	= "http://www.chinahr.com/modules/hmrecruit/index.php?c=managejob&m=insert_hrm";
    final static String PRE_NEW_JOB		= "http://www.chinahr.com/modules/hmrecruit/index.php?";

    Header[] headers = null;
    HttpClient httpclient = null;
    String headerString = "";
    String location = "";

    String job_hope = "1004,1017,1238";
    String jobName = "测试工程师";

    public static void main(String arg[]) throws Exception {
        ZhongHuaYingCai zhongHuaYingCai = new ZhongHuaYingCai();
        zhongHuaYingCai.login();
        zhongHuaYingCai.loginRedirect();
        zhongHuaYingCai.preNewJob();
        zhongHuaYingCai.preNewJob2();
        zhongHuaYingCai.preNewJob3();
        zhongHuaYingCai.newJob();
        System.exit(0);
    }

    public void preNewJob() throws ClientProtocolException, IOException{
        // 1.
        HttpGet httpGet = new HttpGet(PRE_NEW_JOB + "c=managejob&m=check_city&city=27,312");
        httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
        httpGet.setHeader("Cookie", headerString);
        HttpResponse response1 = httpclient.execute(httpGet);
        System.out.println(response1.toString());
//		httpGet.releaseConnection();
    }

    public void preNewJob2() throws ClientProtocolException, IOException{

        // 2.
        HttpPost httppost1 = new HttpPost(PRE_NEW_JOB + "c=catetemplate&m=job_catetmp");
        httppost1.setHeader("X-Requested-With", "XMLHttpRequest");
        httppost1.setHeader("Cookie", headerString);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("", ""));
        params.add(new BasicNameValuePair("job_hope", job_hope));
        httppost1.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse postResponse = httpclient.execute(httppost1);
        System.out.println(postResponse);
    }

    public void preNewJob3() throws ClientProtocolException, IOException{
        // 3.
        HttpPost httppost2 = new HttpPost(PRE_NEW_JOB + "c=managejob&m=insertJobPoints");
        httppost2.setHeader("X-Requested-With", "XMLHttpRequest");
        httppost2.setHeader("Cookie", headerString);
        List<NameValuePair> params2 = new ArrayList<NameValuePair>(2);
        params2.add(new BasicNameValuePair("workPlace", "27,312"));
        params2.add(new BasicNameValuePair("jobName", jobName));
        httppost2.setEntity(new UrlEncodedFormEntity(params2, "UTF-8"));
        HttpResponse postResponse2 = httpclient.execute(httppost2);
        System.out.println(postResponse2);
    }

    public void newJob() throws ClientProtocolException, IOException{
        HttpPost httppost = new HttpPost(POST_NEW_JOB);
        httppost.setHeader("Referer", "http://www.chinahr.com/modules/hmrecruit/index.php?c=managejob&m=issue");
        httppost.setHeader("Origin", "http://www.chinahr.com");
        httppost.setHeader("Host", "www.chinahr.com");
        httppost.setHeader("Cookie", headerString);
        httppost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("oldWorkPlace", "27,312"));
        params.add(new BasicNameValuePair("oldJobName", ""));
        params.add(new BasicNameValuePair("oldClassify", ""));
        params.add(new BasicNameValuePair("company_id", ""));
        params.add(new BasicNameValuePair("comName", ""));
        params.add(new BasicNameValuePair("jobId", ""));
        params.add(new BasicNameValuePair("jobName", jobName));
        params.add(new BasicNameValuePair("workPlace", "27,312"));
        params.add(new BasicNameValuePair("jobType", job_hope));
        params.add(new BasicNameValuePair("endTime", "2014-09-29"));
        params.add(new BasicNameValuePair("depmId", "33dcae848afdd6531da23327j"));
        params.add(new BasicNameValuePair("number", ""));
        params.add(new BasicNameValuePair("workType", "1"));
        params.add(new BasicNameValuePair("jobDesc", "岗位职责"));
        params.add(new BasicNameValuePair("degId", "13"));
        params.add(new BasicNameValuePair("degAbove", "1"));
        params.add(new BasicNameValuePair("minAge", "20"));
        params.add(new BasicNameValuePair("maxAge", "50"));
        params.add(new BasicNameValuePair("gender", "N"));
        params.add(new BasicNameValuePair("expId", "1"));
        params.add(new BasicNameValuePair("expAbove", "1"));
        params.add(new BasicNameValuePair("driverSkill", "0"));
        params.add(new BasicNameValuePair("langSkills[typeId][]", "0"));
        params.add(new BasicNameValuePair("langSkills[langId][]", "0"));
        params.add(new BasicNameValuePair("langSkills[levelId][]", "0"));
        params.add(new BasicNameValuePair("condition", "任职要求"));
        params.add(new BasicNameValuePair("minSalary", "1000"));
        params.add(new BasicNameValuePair("maxSalary", "2000"));
        params.add(new BasicNameValuePair("isNegotiate", "0"));
        params.add(new BasicNameValuePair("welfare[]", "1"));
        params.add(new BasicNameValuePair("welfare[]", "2"));
        params.add(new BasicNameValuePair("welfare[]", "3"));
        params.add(new BasicNameValuePair("welfare[]", "4"));
        params.add(new BasicNameValuePair("welfare[]", "5"));
        params.add(new BasicNameValuePair("welfare[]", "6"));
        params.add(new BasicNameValuePair("welfare[]", "7"));
        params.add(new BasicNameValuePair("benefits", "包住宿"));
        params.add(new BasicNameValuePair("upComContact", "0"));
        params.add(new BasicNameValuePair("contact", "肖琴"));
        params.add(new BasicNameValuePair("jobEmail[]", "hr@cdecube.com"));
        params.add(new BasicNameValuePair("email[]", "resume_test@qq.com"));
        params.add(new BasicNameValuePair("mobile[]", ""));
        params.add(new BasicNameValuePair("phoneArea[]", "028"));
        params.add(new BasicNameValuePair("phoneNo[]", "61837805"));
        params.add(new BasicNameValuePair("phoneExt[]", ""));
        params.add(new BasicNameValuePair("faxArea[]", ""));
        params.add(new BasicNameValuePair("faxNo[]", ""));
        params.add(new BasicNameValuePair("faxExt[]", ""));
        params.add(new BasicNameValuePair("ivAddr", "成都市高新西区合作路89号龙湖时代天街19栋0914"));
        params.add(new BasicNameValuePair("zipCode", ""));
        params.add(new BasicNameValuePair("cal", ""));
        params.add(new BasicNameValuePair("isAutoRep", ""));
        params.add(new BasicNameValuePair("isSendApp", ""));
        params.add(new BasicNameValuePair("isSendCS", ""));
        params.add(new BasicNameValuePair("isSendSys", ""));
        params.add(new BasicNameValuePair("appEmail[]", ""));
        params.add(new BasicNameValuePair("csEmail[]", ""));
        params.add(new BasicNameValuePair("sysEmail[]", ""));
        params.add(new BasicNameValuePair("classify", ""));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse postResponse = httpclient.execute(httppost);
        System.out.println(postResponse);
    }

    public void login() throws ClientProtocolException, IOException{
        httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(LOGIN);
        httppost.setHeader("Referer", "http://www.chinahr.com/modules/hmcompanyx/?c=login&http_referer=");
        httppost.setHeader("Origin", "http://www.chinahr.com");
        httppost.setHeader("Host", "www.chinahr.com");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("redirect", ""));
        params.add(new BasicNameValuePair("uname", "vipcdylf"));
        params.add(new BasicNameValuePair("pass", "longhu123"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse postResponse = httpclient.execute(httppost);
        // 获取相应cookie，准备下次返回给服务器
        headers = postResponse.getHeaders("Set-Cookie");
        for (Header h : headers) {
            headerString += h.getValue();
        }
        Header header = postResponse.getHeaders("Location")[0];
        location = header.getValue();
    }

    public void loginRedirect() throws ClientProtocolException, IOException{
        HttpGet httpget = new HttpGet(location);
        httpget.setHeader("Cookie", headerString);
        httpget.setHeader("Host", "www.chinahr.com");
        httpget.setHeader("Referer", "http://www.chinahr.com/modules/hmcompanyx/?c=login&http_referer=");
        HttpResponse getResponse = httpclient.execute(httpget);
        headers = getResponse.getHeaders("Set-Cookie");
        for (Header h : headers) {
            headerString += h.getValue();
        }
        String responseString = new BasicResponseHandler()
                .handleResponse(getResponse);
        System.out.println(responseString);
    }
}
