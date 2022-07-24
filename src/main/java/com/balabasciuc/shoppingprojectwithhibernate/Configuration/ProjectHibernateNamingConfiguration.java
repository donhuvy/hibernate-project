package com.balabasciuc.shoppingprojectwithhibernate.Configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ProjectHibernateNamingConfiguration extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier("PROJECT_HIBERNATE_" + name.getText(), name.isQuoted());
    }
}
