@functional
Feature: Create Instruction
    Scenario: Create valid instruction
        Given the endpoint "/order-management" exists and accessToken is granted
        When creation payload with orderref name is sent
        When trying to create instruction "instructionn_3"
        Then response status should be 201
    
    Scenario: Instruction reference is longer than 50 chars
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "asdferwtfgefgh_adwv_werf_fefsegohltpeadsfg_fklbpppp"
        Then response status should be 400
   
    Scenario: Instruction reference has invalid chars
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "asdferwtfgefgh_a/dwv_werf!"
        Then response status should be 400
   
    Scenario: Instruction reference is empty
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction ""
        Then response status should be 400

    Scenario: Instruction has invalid chars
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "awdvefs4!cac/afw."
        Then response status should be 400
    
    Scenario: Amount has an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" with invalid amount "100..1"
        Then response status should be 400
        When trying to create instruction "abcd" with invalid amount "abaaH"
        Then response status should be 400

    Scenario: Currency has an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" with invalid currency "euro"
        Then response status should be 400
        When trying to create instruction "abcd" with invalid currency "CHF;"
        Then response status should be 400
        When trying to create instruction "abcd" with invalid currency "a10"
        Then response status should be 400

    Scenario: Country code is in an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" with invalid country code "iee"
        Then response status should be 400
        When trying to create instruction "abcd" with invalid country code "F."
        Then response status should be 400

    Scenario: Directon is in an invalid format
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create instruction "abcd" with invalid direction "debit"
        Then response status should be 400
        When trying to create instruction "abcd" with invalid direction "d0abit"
        Then response status should be 400

    Scenario: SchemeId is invalid
        Given the endpoint "/order-management" exists and accessToken is granted
        When trying to create and instruction "abcd" with invalid schemeId "654EB81FF7F17F7CF5A1EE3FF6972E90"
        Then response status should be 400 



