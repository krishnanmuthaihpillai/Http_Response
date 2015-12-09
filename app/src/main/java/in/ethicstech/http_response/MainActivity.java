package in.ethicstech.http_response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {
Button b1;
    private String url = "http://www.momsdhaba.com/mobileapp/uploadfile/";
    String url2="http://www.momsdhaba.com/mobileapp/chefaddfood/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param1 ="/storage/emulated/0/bluetooth/white-tiger.jpg";
                String param2 = "Beef Chops";
                String param3 = "NON-VEG";
                String param4 = "40";
                String param5 = "beef with masala";
                String param6 = "22";
                String param7 = "2015-11-25";
                String param8= "10";

                SendHttpRequestTask t = new SendHttpRequestTask();
                String[] params = new String[]{url2, param1, param2,param3,param4,param5,param6,param7,param8};
                t.execute(params);

            }
        });
    }




    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String path = params[1];
            String f_name = params[2];
            String f_type = params[3];
            String f_price = params[4];
            String f_desc = params[5];
            String f_chef_id = params[6];
            String f_avail = params[7];
            String f_quant = params[8];
            try {
                HttpClient client = new DefaultHttpClient();
                FileBody bin1 = new FileBody(new File(path));
                HttpPost post = new HttpPost(url);
                MultipartEntity multiPart = new MultipartEntity();
                multiPart.addPart("food_image_by_chef", bin1);
                multiPart.addPart("food_name", new StringBody(f_name));
                multiPart.addPart("food_type", new StringBody(f_type));
                multiPart.addPart("food_price", new StringBody(f_price));
                multiPart.addPart("description", new StringBody(f_desc));
                multiPart.addPart("id", new StringBody(f_chef_id));
                multiPart.addPart("available_date", new StringBody(f_avail));
                multiPart.addPart("food_quantity", new StringBody(f_quant));
                post.setEntity(multiPart);
                client.execute(post);


                HttpResponse response;
                response = client.execute(post);
                HttpEntity httpEntity = response.getEntity();
                String _response= EntityUtils.toString(httpEntity); // content will be consume only once
                final JSONObject jObject=new JSONObject(_response);
                Log.e("XXX", "" + _response);

//                for(int i=0;i<1000000000;i++){
//                    Log.e("XXX",""+_response);
//                }
            }
            catch(Throwable t) {
                // Handle error here
                t.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String data) {
//            item.setActionView(null);

        }



    }





}
