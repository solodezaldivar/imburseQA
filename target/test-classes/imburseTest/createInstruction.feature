@functional
Feature: Create Instruction
    # Scenario: Create valid instruction
    #     Given the endpoint "/order-management" exists and accessToken is granted
    #     When creation payload with orderref name "test__ramon" is sent
    #     When instruction "instructionn_3" is created for "test__ramon"
    #     Then response status should be 201
    
    Scenario: Instruction reference is invalid
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "asdferwtfgefgh_adwv_werf_fefsegohltpeadsfg_fklbpppp" for "test__ramon"
        Then response status should be 400

        When trying to create instruction "asdferwtfgefgh_a/dwv_werf!" for "test__ramon"
        Then response status should be 400

        When trying to create instruction "" for "test__ramon"
        Then response status should be 400
        
        When trying to create instruction "awdvefs4!cac/afw." for "test__ramon"
        Then response status should be 400
        
        When trying to create instruction "awdvefs4!cac/afw." for "test__ramon"
        Then response status should be 400
        # When pass this array
    # Scenario: Instruction reference has invalid chars
    #     Given the endpoint "/order-management" exists and accessToken is granted
    #     When trying to create instruction "asdferwtfgefgh_a/dwv_werf!" for "test__ramon"
    #     Then response status should be 400
    # Scenario: Instruction reference is empty
    #     Given the endpoint "/order-management" exists and accessToken is granted
    #     When trying to create instruction "" for "test__ramon"
    #     Then response status should be 400

    # Scenario: Instruction has invalid chars
    #     Given the endpoint "/order-management" exists and accessToken is granted
    #     When trying to create instruction "awdvefs4!cac/afw." for "test__ramon"
    #     Then response status should be 400
    
    Scenario: amount has an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" for "test__ramon" with invalid amount "100..1"
        Then response status should be 400
        When trying to create instruction "abcd" for "test__ramon" with invalid amount "abaaH"
        Then response status should be 400

    Scenario: currency has an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" for "test__ramon" with invalid currency "euro"
        Then response status should be 400
        When trying to create instruction "abcd" for "test__ramon" with invalid currency "CHF;"
        Then response status should be 400
        When trying to create instruction "abcd" for "test__ramon" with invalid currency "a10"
        Then response status should be 400

    Scenario: country code is in an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" for "test__ramon" with invalid country code "iee"
        Then response status should be 400
        When trying to create instruction "abcd" for "test__ramon" with invalid country code "F."
        Then response status should be 400

    Scenario: directon is in an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        # When trying to create instruction "abcd" for "test__ramon" with invalid direction "debit"
        # Then response status should be 400
        When trying to create instruction "abcd" for "test__ramon" with invalid direction "d0abit"
        Then response status should be 400

    Scenario: schemeId is invalid
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create and instruction "abcd" for "test__ramon" with invalid schemeId "654EB81FF7F17F7CF5A1EE3FF6972E90"
        Then response status should be 400 



