package ukma.speedyfix.service.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ukma.speedyfix.domain.Weather;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final String API_KEY = "6f807b41cbe1c8f410398fc4403fdd2c";
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid={apiKey}&units=metric";

    private final ObjectMapper objectMapper;

    @Cacheable(cacheNames = "todayWeather")
    public Weather getTodayWeather() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(BASE_URL, String.class, API_KEY);

        try {

            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode mainNode = rootNode.path("main");
            return objectMapper.treeToValue(mainNode, Weather.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather data", e);
        }
    }

    @CachePut(cacheNames = "todayWeather")
    @Scheduled(cron = "0 0 12 * * *", zone = "Europe/Kiev")
    public Weather refreshTodayWeather() {
        System.out.println("Refreshing today's weather data...");
        return getTodayWeather();
    }
}
