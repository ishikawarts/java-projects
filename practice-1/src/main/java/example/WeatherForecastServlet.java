package example;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import com.example.model.primaryarea.Pref;
import com.example.model.primaryarea.Rss;
import com.example.model.weatherforecast.WeatherForecast;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/WeatherForecastServlet")
public class WeatherForecastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WeatherForecastServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		var area = getArea();

		if (Objects.nonNull(area)) {
			var prefmap = area.getChannel().getLdWeatherSource().getPref().stream()
					.collect(Collectors.toMap(Pref::getTitle, Pref::getCity, (e1, e2) -> e2, LinkedHashMap::new));

			var prefmapJson = new ObjectMapper().writeValueAsString(prefmap);

			request.setAttribute("prefmap", prefmap);
			request.setAttribute("prefmapJson", prefmapJson);
		} else {
			request.setAttribute("errorMessage", "都道府県・地域が取得できませんでした。");
		}

		var dispacher = request.getRequestDispatcher("/WeatherForecast.jsp");

		dispacher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		var cityId = request.getParameter("city");

		var weatherForecast = getWeatherForecast(cityId);

		if (Objects.nonNull(weatherForecast)) {
			request.setAttribute("weatherForecast", weatherForecast);
		} else {
			request.setAttribute("errorMessage", "天気予報を取得できませんでした。");
		}

		doGet(request, response);
	}

	private WeatherForecast getWeatherForecast(String cityId) {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest
				.newBuilder(URI.create("https://weather.tsukumijima.net/api/forecast/city/" + cityId))
				.header("accept", "application/json")
				.build();

		try {
			var response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (200 == response.statusCode()) {
				return new ObjectMapper().readValue(response.body(), WeatherForecast.class);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

	private static Rss getArea() {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest
				.newBuilder(URI.create("https://weather.tsukumijima.net/primary_area.xml"))
				.header("accept", "application/xml")
				.build();

		try {
			var response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (200 == response.statusCode()) {
				return JAXB.unmarshal(new StringReader(response.body()), Rss.class);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

}
