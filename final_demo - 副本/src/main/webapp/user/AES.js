window.crypto.subtle.generateKey(
    {
        name: "AES-CBC",
        length: 256, // 可以是 128, 或 256
    },
    true, // 是否可导出
    ["encrypt", "decrypt"] // 密钥用途
)
    .then(function(key){
        // key是一个CryptoKey对象，包含了用于AES加密的密钥
        console.log('AES Key generated:', key);

        // 如果设置了可导出（true），你可以导出密钥的原始字节
        return window.crypto.subtle.exportKey("raw", key);
    })
    .then(function(exportedKey){

        const keyBase64 = btoa(String.fromCharCode.apply(null, new Uint8Array(exportedKey)));
        window.aesKey = keyBase64;

        // 现在你可以使用keyArray作为AES加密的密钥
        // 注意：这个密钥需要在需要解密的地方安全地共享
    })
    .catch(function(err){
        console.error(err);
    });