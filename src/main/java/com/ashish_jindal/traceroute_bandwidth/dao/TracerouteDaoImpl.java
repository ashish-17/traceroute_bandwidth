/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ashish_jindal.traceroute_bandwidth.model.PingData;
import com.ashish_jindal.traceroute_bandwidth.model.TraceRoute;

/**
 * @author ashish
 *
 */
@Repository
@Transactional
public class TracerouteDaoImpl implements TracerouteDao {

	private static final Logger logger = LoggerFactory.getLogger(PingDataDaoImpl.class);
	 
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

	/* (non-Javadoc)
	 * @see com.ashish_jindal.traceroute_bandwidth.dao.TracerouteDao#addData(com.ashish_jindal.traceroute_bandwidth.model.TraceRoute)
	 */
	@Override
	public void addData(TraceRoute t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(t);
		logger.info("tracert saved - " + t);		
	}

	/* (non-Javadoc)
	 * @see com.ashish_jindal.traceroute_bandwidth.dao.TracerouteDao#getAllTraceroutes()
	 */
	@Override
	public List<String> getAllTraceroutes() {
		Session session = this.sessionFactory.getCurrentSession();
        List<String> tracerts = session.createQuery("SELECT t.tracert_id from TraceRoute t").list();
        Set<String> hs = new HashSet<>();
        hs.addAll(tracerts);
        tracerts.clear();
        tracerts.addAll(hs);
        return tracerts;
	}

	/* (non-Javadoc)
	 * @see com.ashish_jindal.traceroute_bandwidth.dao.TracerouteDao#findById(java.lang.String)
	 */
	@Override
	public TraceRoute findById(String traceroute_id) {
		Session session = this.sessionFactory.getCurrentSession();
        List<TraceRoute> tracerts = session.createQuery("FROM TraceRoute WHERE tracert_id='" + traceroute_id + "'").list();
        return tracerts.get(0);
	}

	/* (non-Javadoc)
	 * @see com.ashish_jindal.traceroute_bandwidth.dao.TracerouteDao#removeData(int)
	 */
	@Override
	public void removeData(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TraceRoute t = (TraceRoute) session.load(TraceRoute.class, new Integer(id));
        if(null != t){
            session.delete(t);
        }
        
        logger.info("Tracert deleted successfully, Tracert details="+t);
	}

}
