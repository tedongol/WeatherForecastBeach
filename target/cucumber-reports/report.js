$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("GetWeather.feature");
formatter.feature({
  "line": 1,
  "name": "Beaches with suitable weather",
  "description": "",
  "id": "beaches-with-suitable-weather",
  "keyword": "Feature"
});
formatter.before({
  "duration": 65175579,
  "status": "passed"
});
formatter.scenario({
  "line": 5,
  "name": "Verify which beach has suitable weather out from the list",
  "description": "",
  "id": "beaches-with-suitable-weather;verify-which-beach-has-suitable-weather-out-from-the-list",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 4,
      "name": "@Weather"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "Get the Weather report with top 10 postcode of Sydney",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "Pick best suitable two spots based upon suitable weather",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "10",
      "offset": 32
    }
  ],
  "location": "GetWeather.Get_the_Weather_report_with_top_postcode_of_Sydney(int)"
});
formatter.result({
  "duration": 43548174686,
  "status": "passed"
});
formatter.match({
  "location": "GetWeather.Pick_bests_suitable_two_spots_based_upon_suitable_weather()"
});
formatter.result({
  "duration": 110735108,
  "status": "passed"
});
formatter.after({
  "duration": 220211,
  "status": "passed"
});
});