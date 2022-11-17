package com.christianoette.jasyptdemo;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.christianoette.jasyptdemo.config.CustomConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptionTest {

    private final Logger logger = LoggerFactory.getLogger(CustomConfiguration.class);
    private static final String masterPassword = "mySuperSecretMasterPassword";
    private static final String propertyValue = "123";
    private static final String algorithm = "PBEWithMD5AndDES";


    @Test
    public void testEncryptAndDecrypt() {
        // given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(createConfig());
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setConfig(createConfig());

        // when
        var encrypted = encryptor.encrypt(propertyValue);
        logger.info("Encrypted Property: {}", encrypted);

        // then
        var decrypted = decryptor.decrypt(encrypted);
        assertThat(decrypted).isEqualTo(propertyValue);
    }

    private PBEConfig createConfig() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(masterPassword);
        config.setAlgorithm(algorithm);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        return config;
    }
}
