#!/bin/bash
# Generate a new keystore for nanacomik app

keytool -genkey -v -keystore app/nanacomik.keystore \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias nanacomik \
  -storepass nanacomik123 \
  -keypass nanacomik123 \
  -dname "CN=Nana Comik,O=NanaComik,C=US"

echo "Keystore generated at: app/nanacomik.keystore"
echo "Store password: nanacomik123"
echo "Key password: nanacomik123"
echo "Alias: nanacomik"
