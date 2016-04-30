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

/**
 * @author ashish
 *
 */
@Repository
@Transactional
public class PingDataDaoImpl implements PingDataDao {

	private static final Logger logger = LoggerFactory.getLogger(PingDataDaoImpl.class);
	 
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

	@Override
	public void addData(PingData p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Ping saved - " + p);		
	}

	@Override
	public List<PingData> findDataById(String ping_id) {
		Session session = this.sessionFactory.getCurrentSession();
        List<PingData> pings = session.createQuery("FROM PingData WHERE ping_id='" + ping_id + "'").list();
        return pings;
	}

	@Override
	public void removePingData(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		PingData p = (PingData) session.load(PingData.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        
        logger.info("Ping deleted successfully, Ping details="+p);		
	}

	@Override
	public List<String> getAllPings() {
		Session session = this.sessionFactory.getCurrentSession();
        List<String> pings = session.createQuery("SELECT p.ping_id from PingData p").list();
        Set<String> hs = new HashSet<>();
        hs.addAll(pings);
        pings.clear();
        pings.addAll(hs);
        return pings;
	}

	@Override
	public List<String> fingPingsForTracert(int pk) {
		Session session = this.sessionFactory.getCurrentSession();
        List<String> pings = session.createQuery("SELECT ping_id FROM PingData WHERE tracert_id='" + pk + "'").list();
        return pings;
	}
}
