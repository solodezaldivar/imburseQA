@functional
Feature: Create Order
    
     Scenario: Orderref is valid
        Given the endpoint "/order-management" exists and accessToken is granted
        When creation payload with orderref name is sent
        Then response status should be 201

    Scenario: Orderref is invalid
        Given the endpoint "/order-management" exists and accessToken is granted
        When creation payload with orderref name "!abcd_dawdf_daw-wda=" is sent
        Then response status should be 400
 
        When creation payload with orderref name "abcdefgafevnsrnvsejvjeanfanwfjanwndakwndkanwdwandawda" is sent 
        Then response status should be 400

        When creation payload with orderref name "ad555%kwndkanwdwandawda" is sent 
        Then response status should be 400

        When creation payload with orderref name "" is sent 
        Then response status should be 400

    Scenario: Orderref is unique
        Given the endpoint "/order-management" exists and accessToken is granted
        When creation payload with orderref name "validOrderRef" is sent 
        When creation payload with orderref name "validOrderRef" is sent 
        Then response status should be 400

    Scenario: Metadata key is too long
        Given the endpoint "/order-management" exists and accessToken is granted
        When creating an order with metadata "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa":"a","dd":"c","cg":"c"
        Then response status should be 400
    
    Scenario: Metadata key is empty
        Given the endpoint "/order-management" exists and accessToken is granted
        When creating an order with metadata "aa":"b","cd":"c","":"c"
        Then response status should be 400

    # Not working, API allows > 100 chars for value?
    # Scenario: Metadata value is too long
    #     Given the endpoint "/order-management" exists and accessToken is granted
    #     When creating an order with metadata "aa":"b","cd":"c","aac":"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    #     Then response status should be 400
    
    # Not working, API accepts duplicated keys?
    # Scenario: Metadata key is duplicated
    #     Given the endpoint "/order-management" exists and accessToken is granted
        # When creating an order with metadata "aa":"cd","aa":"a","aa":"lhm"
        # Then response status should be 400

        

        

    Scenario: Metadata is valid
        Given the endpoint "/order-management" exists and accessToken is granted
        When creating an order with metadata "aa":"a","hh":"c","cfg":"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        Then response status should be 201 


