<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>天気予報</title>
<script type="text/javascript">
const prefmapJson = `${prefmapJson}`;

window.addEventListener("load", function () {
  let prefmap = JSON.parse(prefmapJson);
  let pref = document.getElementById("pref");
  pref.addEventListener("change", function(){
	  let city = document.getElementById("city");
	  city.textContent = null;

	  let opNone = document.createElement("option");
	  opNone.value = '';
	  opNone.text = '----';
      city.appendChild(opNone);
	  
	  prefmap[pref.value].forEach(element => {
          let op = document.createElement("option");
          op.value = element.id;
          op.text = element.title;
          city.appendChild(op);
	  });
  });
});
</script>
</head>
<body>
	<form action="/Example2/ExampleServlet" method="POST">
		都道府県：
		<select id="pref" name="pref">
			<option value="">----</option>
			<c:forEach var="prefTitle" items="${prefmap.keySet()}" varStatus="st">
				<option value="${prefTitle}">${prefTitle}</option>
			</c:forEach>
		</select>
		地域：
		<select id="city" name="city">
			<option value="">----</option>
		</select>
		<button type="submit">送信</button>
	</form>
	
	<c:if test="${errorMessage != null}">
		<p style="color:red;">${errorMessage}</p>
	</c:if>

	<c:if test="${weatherForecast != null}">
		<h3>${weatherForecast.title}</h3>
		<p>${weatherForecast.description.bodyText}</p>
		<table border="1">
			<thead>
				<tr>
					<th>予報日</th>
					<th>天気</th>
					<th>天気詳細</th>
					<th>気温（℃）</th>
					<th>降水確率</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="forecast" items="${weatherForecast.forecasts}" varStatus="st">
					<tr>
						<td>${forecast.date}</td>
						<td>
							<img style="width:${forecast.image.width}px; height:${forecast.image.height}px;" src="${forecast.image.url}">
						</td>
						<td>
							天気情報：${forecast.detail.weather}
							<br />
							風の強さ：${forecast.detail.wind}
							<br />
							波の高さ：${forecast.detail.wave}
						</td>
						<td>
							最高気温：${forecast.temperature.max.celsius}
							<br />
							最低気温：${forecast.temperature.min.celsius}
						</td>
						<td>
							0時-6時：${forecast.chanceOfRain.t0006}
							<br />
							6時-12時：${forecast.chanceOfRain.t0612}
							<br />
							12時-18時：${forecast.chanceOfRain.t1218}
							<br />
							18時-24時：${forecast.chanceOfRain.t1824}
							<br />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>