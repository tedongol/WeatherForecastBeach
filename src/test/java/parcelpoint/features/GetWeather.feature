Feature: Beaches with suitable weather


	@Weather
	Scenario: Verify which beach has suitable weather out from the list
		When Get the Weather report with top 2 postcode of Sydney
	    Then Pick best suitable two spots based upon suitable weather

	@uv
	Scenario: Verify which beach has suitable weather out from the list
		When Get the Weather report with top 2 postcode of Sydney
		Then Pick best suitable two spots based upon suitable uv



