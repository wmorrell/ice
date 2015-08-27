package org.jbei.ice.lib.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbei.ice.lib.dto.entry.CustomField;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.entry.model.Parameter;

import java.util.List;

/**
 * Data accessor object for handling {@link Parameter}s
 *
 * @author Hector Plahar
 */
public class ParameterDAO extends HibernateRepository<Parameter> {

    @Override
    public Parameter get(long id) {
        return super.get(Parameter.class, id);
    }

    // filter by key value pairs
    public List<Entry> filter(List<CustomField> fields) {
        Criteria criteria = currentSession().createCriteria(Parameter.class);
        for (CustomField field : fields) {
            criteria.add(
                    Restrictions.and(
                            Restrictions.eq("key", field.getName()), Restrictions.eq("value", field.getValue())));
        }
        criteria.setProjection(Projections.property("entry"));
        return criteria.list();
    }
}