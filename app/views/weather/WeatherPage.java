package app.views.weather;

import app.views.MainLayout;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.impl.client.HttpClients.createDefault;

@PageTitle("天気情報")
@Route(value = "天気情報", layout = MainLayout.class)
public class WeatherPage extends VerticalLayout {

    private TextField city;
    private Button set;
    VerticalLayout result=new VerticalLayout();

    public WeatherPage() {
        city = new TextField("City name");
        city.setHelperText("都市名");
        set = new Button("検索");
        VerticalLayout result=new VerticalLayout();
        //remove(result);
        set.addClickListener(e -> {
                    String apiKey = "200eec0822643f66f493719b2f542939";
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city.getValue() + "&appid=" + apiKey + "&units=metric";

                    CloseableHttpClient client = createDefault();
                    HttpGet request = new HttpGet(url);

                    try {
                        result.removeAll();
                        CloseableHttpResponse response = client.execute(request); // リクエストを実⾏
                        String json = EntityUtils.toString(response.getEntity()); // レスポンスを取得

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        WeatherResponse weatherResponse
                                = mapper.readValue(json, WeatherResponse.class);
                       result.add(new Span("都市名: "+city.getValue()));
                        result.add(new Span(String.valueOf("温度: "+weatherResponse.main.temp+"℃")));
                        result.add(new Span(String.valueOf("湿度: "+weatherResponse.main.humidity+"%")));
                        result.add(new Span("天候: "+weatherResponse.weather.get(0).description));
                       city.clear();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });

        setMargin(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(city,set,result);
    }
}
