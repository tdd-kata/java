package com.markruler.batch.config;

import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CustomerByCityQueryProvider extends AbstractJpaQueryProvider {

    private String cityName;

    @Override
    public Query createQuery() {
        final EntityManager em = getEntityManager();
        return em
                // .createNativeQuery()
                .createQuery("select c from Customer c where c.city = :city")
                .setParameter("city", cityName);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cityName, "City name is required");
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
