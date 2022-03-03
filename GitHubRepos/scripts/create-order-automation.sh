#!/bin/bash
echo "_______________________GETTING_AUTH_TOKEN_"

tokenResponse=$(curl --location --request POST 'https://preprod-auth.fairprice.com.sg/oauth/token' \
--header 'Content-Type: application/json' \
--header 'Cookie: did=s%3Av0%3Add7c1040-1814-11eb-8648-03c2a13d77c9.NsX3wAQnHY8TgNy%2BpXqzTnJOchyVoLhX0yBNbLxvQcg; did_compat=s%3Av0%3Add7c1040-1814-11eb-8648-03c2a13d77c9.NsX3wAQnHY8TgNy%2BpXqzTnJOchyVoLhX0yBNbLxvQcg' \
--data-raw '{
    "audience": "https://identity.nedigital.sg",
    "client_id": "71zXmrAvrnQDDNCAhh417AgFwqIm8gDO",
    "grant_type": "http://auth0.com/oauth/grant-type/password-realm",
    "username": "soummya_patil@ntucenterprise.sg",
    "password": "loadTesting4693!@",
    "realm": "NE-Digital-ID-Platform-Connection",
    "scope": "openid email profile"
}')

echo "_______________________RECIEVED_AUTH_TOKEN_"
truncToken=$(echo $tokenResponse | grep -Eo '"access_token":.*?[^\\]",')


size=${#truncToken}
num2=18
accessSize="$(($size-$num2))"
access_token=${truncToken:16:accessSize}

todaysDate=$(date +'%Y-%m-%d')
echo "_______________________PLACING_ORDER_"

orderResponse=$(curl --location --request POST 'https://website-api.zs-uat.fairprice.com.sg/api/order' \
--header 'Content-Type: application/json' \
--header "Authorization: Bearer $access_token" \
--data-raw '{
    "cardId": 39922,
    "preferredDate": "'"$todaysDate"'",
    "preferredSlotId": 29,
    "cart": "[{\"id\":\"1144877\",\"q\":\"1\"}]",
    "type": "DELIVERY",
    "storeId": 395,
    "paymentMethod": "ONLINE",
    "metaData": {},
    "couponCodes": []
}')

referenceNumber=$(echo $orderResponse | grep -Eo '"referenceNumber":.*?[^\\]",')

if [ -z "$referenceNumber" ]
then
    echo "_______________________ORDER_PLACING_FAILED__"
else
    echo "_______________________PLACED_ORDER_WITH__$referenceNumber"
    sleep 5m
fi


echo "_______________________END SCRIPT_"