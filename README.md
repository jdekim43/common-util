# common-util
개발할 때 자주 사용하는 함수 라이브러리
* 암호화
    * AES
    * AES with random IV
    * RSA_2048
* Encoder
    * Base64
    * Hex
* Hash functions
    * MD5
    * HMAC_SHA_1
    * HMAC_SHA_512
* Extension Functions
* Util Functions
    * Generate random string
    * shutdownHook (Only JVM)
    * parallel (coroutine async)
    * ...

## Support
* JVM

## Install
### Gradle Project
1. Add dependency
    ```
    build.gradle.kts
   
    repositories {
      maven("https://jadekim.jfrog.io/artifactory/maven")
    }
   
    dependencies {
      implementation("kr.jadekim:common-util:$commonUtilVersion")
    }
    ```

## How to use
### Encryption / Decryption
#### AES
* iv == null 이면 key의 앞 16 byte 사용
```
// Encryption
AES.encrypt(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray

// Decryption
AES.decrypt(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray

// Encryption with Base64 encode
AES.encryptEncoded(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray
AES.encryptString(ByteArray or String, key: ByteArray, iv: ByteArray?): String

// Base64 encoded text Decryption
AES.decryptEncoded(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray
AES.decryptString(ByteArray or String, key: ByteArray, iv: ByteArray?): String
```
#### AES with random IV
* iv == null 이면 iv 랜덤 생성하고 암호화된 텍스트 앞에 추가
```
// Encryption
AES_RANDOM_IV.encrypt(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray

// Decryption
AES_RANDOM_IV.decrypt(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray

// Encryption with Base64 encode
AES_RANDOM_IV.encryptEncoded(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray
AES_RANDOM_IV.encryptString(ByteArray or String, key: ByteArray, iv: ByteArray?): String

// Base64 encoded text Decryption
AES_RANDOM_IV.decryptEncoded(ByteArray or String, key: ByteArray, iv: ByteArray?): ByteArray
AES_RANDOM_IV.decryptString(ByteArray or String, key: ByteArray, iv: ByteArray?): String
```
#### RSA_2048
* iv 파라미터 무시
```
// Encryption
RSA_2048.encrypt(ByteArray or String, key: ByteArray): ByteArray

// Decryption
RSA_2048.decrypt(ByteArray or String, key: ByteArray): ByteArray

// Encryption with Base64 encode
RSA_2048.encryptEncoded(ByteArray or String, key: ByteArray): ByteArray
RSA_2048.encryptString(ByteArray or String, key: ByteArray): String

// Base64 encoded text Decryption
RSA_2048.decryptEncoded(ByteArray or String, key: ByteArray): ByteArray
RSA_2048.decryptString(ByteArray or String, key: ByteArray): String
```
### Encoder
#### Base64
```
//Encode base64
BASE64.encode(ByteArray or String, key: ByteArray): ByteArray
BASE64.encodeToString(ByteArray or String, key: ByteArray): String
byteArrayOf().asBase64
"AnyText".asBase64
byteArrayOf().asBase64String
"AnyText".asBase64String

//Decode base64
BASE64.decode(ByteArray or String, key: ByteArray): ByteArray
BASE64.decodeToString(ByteArray or String, key: ByteArray): String
byteArrayOf().decodedBase64
"EncodedText".decodedBase64
byteArrayOf().decodedBase64String
"EncodedText".decodedBase64String
```
#### Hex
```
//Encode hex
HEX.encode(ByteArray or String, key: ByteArray): ByteArray
HEX.encodeToString(ByteArray or String, key: ByteArray): String
byteArrayOf().asHex
"AnyText".asHex
byteArrayOf().asHexString
"AnyText".asHexString
```
### Hash Functions
#### MD5
```
// hash
MD5.hash(ByteArray or String): ByteArray

// hash with encode hex
MD5.hashHex(ByteArray or String): ByteArray

// hash with encode hex string
MD5.hashString(ByteArray or String): String
```
### Util Functions
#### Generate random string
```
val str = randomString

or

val length = 12
val str = randomString(length)
```
#### Shutdown Hook (Only JVM)
```
shutdownHook {
    ...
}
```