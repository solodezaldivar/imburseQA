# @functional
# Feature: Create Order
    
     Scenario: Orderref is valid
        Given the endpoint "/order-management" exists and accessToken is granted
        When creation payload with orderref name "abc_efg.de" is sent
        Then response status should be 201

        When creation payload with orderref name "abcefgde" is sent
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
        When creation payload with orderref name "validOrderRef" is Scenario Outline: 
        Then response status should be 400

    Scenario: Metadata is invalid:

    #  Scenario: Create an order without instructions
#         Given the endpoint "/order-management" exists and accessToken is granted
#         When creation payload with orderref name "Ord_Er_r_ef" is sent
#         Then response status should be 201
    


