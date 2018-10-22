package com.amido.healthchecker.azure;

import com.amido.healthchecker.util.SecretHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Qualifier("vaultService")
@Profile("dev")
public class DummyVaultService implements VaultService {

    private Environment env;

    private SecretHolder secretHolder;

    @Autowired
    public DummyVaultService(Environment env, SecretHolder secretHolder){
        this.env = env;
        this.secretHolder = secretHolder;
    }

    @Override
    public void loadAllSecrets() {
        this.secretHolder.getSecretNames().forEach(name -> {
                    final String value = env.getProperty(name);
                    if(!StringUtils.isEmpty(value)) {
                        this.secretHolder.setSecretsMap(name, value);
                    } else {
                        throw new IllegalStateException("Couldn't find secret " + name);
                    }
                }
        );
    }
}