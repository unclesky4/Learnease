package org.jyu.web.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.jyu.app.dao")
@EntityScan(basePackages = "org.jyu.app.entity")
public class JpaConfig {}