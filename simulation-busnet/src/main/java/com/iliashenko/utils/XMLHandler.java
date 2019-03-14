package com.iliashenko.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.iliashenko.BusShedule;
import com.iliashenko.Network;
import com.iliashenko.simulation.Configuration;

/**
 * Util class to unmarshalling xml to java objects.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class XMLHandler {

	private static final Logger XML_LOGGER = LoggerFactory.getLogger(XMLHandler.class);

	private XMLHandler() {
		super();
	}

	private static boolean validate(String xmlFile, String schemaFile) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = schemaFactory.newSchema(new File((schemaFile)));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File((xmlFile))));
			return true;
		} catch (SAXException | IOException e) {
			XML_LOGGER.warn("{}", e);
			return false;
		}
	}

	private static Object unmarshalling(String fileName, String className) {
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
			File xmlFile = new File(fileName);
			JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(obj.getClass());
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			obj = obj.getClass().cast(jaxbUnmarshaller.unmarshal(xmlFile));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | JAXBException e1) {
			XML_LOGGER.warn("{}", e1);
		}
		return obj;
	}

	public static Configuration unmarshallingConfig(String fileName, String schemaFile) {
		if (validate(fileName, schemaFile)) {
			return (Configuration) unmarshalling(fileName, "com.iliashenko.simulation.Configuration");
		}
		return null;
	}

	public static BusShedule unmarshallingShedule(String fileName, String schemaFile) {
		if (validate(fileName, schemaFile)) {
			return (BusShedule) unmarshalling(fileName, "com.iliashenko.BusShedule");
		}
		return null;
	}

	public static Network unmarshallingNetwork(String fileName, String schemaFile) {
		if (validate(fileName, schemaFile)) {
			Network net = (Network) unmarshalling(fileName, "com.iliashenko.Network");
			return net;
		}
		return null;
	}

}
