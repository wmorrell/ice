package org.jbei.ice.services.webservices;

import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import org.jbei.ice.controllers.common.ControllerException;
import org.jbei.ice.lib.config.ConfigurationController;
import org.jbei.ice.lib.logging.Logger;
import org.jbei.ice.lib.shared.dto.ConfigurationKey;

/**
 * Service client for the Registry API. Used to communicate with other ICE Registry Instances
 *
 * @author Hector Plahar
 */
@WebServiceClient(name = "RegistryAPIServiceClient", targetNamespace = "https://api.registry.jbei.org/")
public class RegistryAPIServiceClient {

    private static final QName SERVICE_NAME = new QName("https://api.registry.jbei.org/", "RegistryAPIService");
    private static final String QNAME_LOCAL_PART = "RegistryAPIPort";
    private static final Service service = Service.create(SERVICE_NAME);
    private static final HashSet<String> namespaceURIs = new HashSet<>();
    private static final RegistryAPIServiceClient INSTANCE = new RegistryAPIServiceClient();

    public static RegistryAPIServiceClient getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves available ports and maintains in memory
     */
    private RegistryAPIServiceClient() {
        Logger.info("Creating service client for " + SERVICE_NAME);

        try {
            String values = new ConfigurationController().getPropertyValue(ConfigurationKey.WEB_PARTNERS);
            if (values != null) {
                for (String split : values.split(";"))
                    addPortName(split.trim());
            }

        } catch (ControllerException e) {
            Logger.warn(e.getMessage());
        }
    }

    public void addPortName(String namespaceURI) {
        if (namespaceURI == null || namespaceURI.trim().isEmpty() || namespaceURIs.contains(namespaceURI.trim()))
            return;

        QName name = new QName(namespaceURI, QNAME_LOCAL_PART);
        service.addPort(name, SOAPBinding.SOAP11HTTP_BINDING, "https://" + namespaceURI + "/api/RegistryAPI");
        namespaceURIs.add(namespaceURI.trim());
    }

    public IRegistryAPI getAPIPortForURL(String namespaceURI) {
        addPortName(namespaceURI);
        Iterator<QName> iter = service.getPorts();
        while (iter.hasNext()) {
            QName name = iter.next();
            if (name.getNamespaceURI().equals(namespaceURI)) {
                return service.getPort(name, IRegistryAPI.class);
            }
        }
        return null;
    }

    public void removePortName(String namespaceURI) {
        Iterator<QName> iter = service.getPorts();
        while (iter.hasNext()) {
            QName name = iter.next();
            if (name.getNamespaceURI().equalsIgnoreCase(namespaceURI.trim())) {
                iter.remove();
                namespaceURIs.remove(namespaceURI.trim());
                return;
            }
        }
    }

    public Service getService() {
        return this.service;
    }
}
