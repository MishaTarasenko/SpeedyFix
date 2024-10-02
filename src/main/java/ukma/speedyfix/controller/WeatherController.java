package ukma.speedyfix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukma.speedyfix.domain.Weather;
import ukma.speedyfix.service.weather.WeatherService;

@RestController
@RequestMapping(path = "/api")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/temperature")
    public String getTemperature() {
        Weather weather = weatherService.getTodayTemperature();
        if (weather.getTemp() > 7) {
            return "Ми рекомендуємо літню гуму";
        } else {
            return "Ми рекомендуємо зимню гуму";
        }
    }
}
