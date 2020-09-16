package com.emard.sigrhp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.emard.sigrhp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.emard.sigrhp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.emard.sigrhp.domain.User.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Authority.class.getName());
            createCache(cm, com.emard.sigrhp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.emard.sigrhp.domain.Structure.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Convention.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Avantage.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Motif.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Bareme.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Exercice.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Fonction.class.getName());
            createCache(cm, com.emard.sigrhp.domain.MoisConcerne.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Nationalite.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Prime.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Regime.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Sexe.class.getName());
            createCache(cm, com.emard.sigrhp.domain.SituationMatrimoniale.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Statut.class.getName());
            createCache(cm, com.emard.sigrhp.domain.StatutDemande.class.getName());
            createCache(cm, com.emard.sigrhp.domain.TypeAbsence.class.getName());
            createCache(cm, com.emard.sigrhp.domain.TypeContrat.class.getName());
            createCache(cm, com.emard.sigrhp.domain.TypePaiement.class.getName());
            createCache(cm, com.emard.sigrhp.domain.TypeRelation.class.getName());
            createCache(cm, com.emard.sigrhp.domain.UserExtra.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Collaborateur.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Categorie.class.getName());
            createCache(cm, com.emard.sigrhp.domain.AvantageCollab.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Absence.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Remboursement.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Remboursement.class.getName() + ".bulletins");
            createCache(cm, com.emard.sigrhp.domain.Bulletin.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Bulletin.class.getName() + ".remboursements");
            createCache(cm, com.emard.sigrhp.domain.DetailPret.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Pret.class.getName());
            createCache(cm, com.emard.sigrhp.domain.DemandeConge.class.getName());
            createCache(cm, com.emard.sigrhp.domain.MembreFamille.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Pieces.class.getName());
            createCache(cm, com.emard.sigrhp.domain.PrimeCollab.class.getName());
            createCache(cm, com.emard.sigrhp.domain.Recap.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
