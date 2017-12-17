/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.util;

import org.jboss.security.CacheableManager;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.security.Principal;
import java.util.logging.Logger;

/**
 *
 * @author Ayemi
 */
@WebListener
public class AuthCacheClearSessionListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(AuthCacheClearSessionListener.class.getName());

    @Inject
    private Principal principal;

    @Resource(name = "java:jboss/jaas/cosmDomain/authenticationMgr")
    private CacheableManager<?, Principal> authenticationManager;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.info("creating session...");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOG.info("Clearing cache...");
        authenticationManager.flushCache(principal);
        //clearCache(principal.getName());
    }

    public void clearCache(String username) {
        try {

            ObjectName jaasMgr = new ObjectName("jboss.as:subsystem=security,security-domain=cosmDomain");
            Object[] params = {username};
            String[] signature = {"java.lang.String"};
            MBeanServer server = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
            server.invoke(jaasMgr, "flushCache", params, signature);
        } catch (Exception ex) {
            LOG.warning(ex.getMessage());
        }
    }
}
