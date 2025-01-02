// Include the CryptoJS library
const CryptoJS = require("crypto-js");

// Base64 encoded 16-byte key from Java
var encryptedBase64Key = 'bXVzdGJlMTZieXRlc2tleQ==';
var parsedBase64Key = CryptoJS.enc.Base64.parse(encryptedBase64Key);

// Encrypted cipher text from Java
var encryptedCipherText = '/qd/vKnPvjIZPrJyNZeCCw==';

// Decrypt the data
var decryptedData = CryptoJS.AES.decrypt(encryptedCipherText, parsedBase64Key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
});

// Convert decrypted data to UTF-8 string
var decryptedText = decryptedData.toString(CryptoJS.enc.Utf8);
console.log("Decrypted Text: " + decryptedText);
