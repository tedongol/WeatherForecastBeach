Feature: Beaches with suitable weather


	@Weather
	Scenario: Verify which beach has suitable weather out from the list
		When Get the Weather report with top 10 postcode of Sydney
	    Then Pick best suitable two spots based upon suitable weather

	@uv
	Scenario: Verify which beach has suitable uv out from the list
		When Get the Weather report with top 10 postcode of Sydney
		Then Pick best suitable two spots based upon suitable uv



